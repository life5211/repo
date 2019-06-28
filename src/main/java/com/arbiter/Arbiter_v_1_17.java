package com.arbiter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Arbiter_v_1_17 extends JFrame {

	private final int PRIMARY_ROW = 8;// 初级行数
	private final int PRIMARY_COL = 8;// 初级列数
	private final int PRIMARY_BOMB = 10;// 初级雷数

	private final int MEDIUM_ROW = 16;// 中级行数
	private final int MEDIUM_COL = 16;// 中级列数
	private final int MEDIUM_BOMB = 40;// 中级雷数

	private final int SENIOR_ROW = 16;// 高级行数
	private final int SENIOR_COL = 30;// 高级列数
	private final int SENIOR_BOMB = 99;// 高级雷数

	private int row = PRIMARY_ROW;// 行数
	private int col = PRIMARY_COL;// 列数
	private int bombNo = PRIMARY_BOMB;// 雷数
	private int blockNo = row * col;// 雷区方格数
	private int unmarkMines = bombNo;// 未标记雷数
	private int leftBlockNo = blockNo - bombNo;// 剩余方格数
	private int size = 800 / row;// 单个雷块尺寸
	private int height = row * size + 70;// 高度
	private int width = col * size;// 宽度

	private JPanel contentPane;// 总面板
	private JPanel panelBtn;// 功能区面板
	private JPanel panelMines;
	private JButton btnNewGame;// 重新开始游戏按钮
	private JButton btnPrimary;
	private JButton btnMedium;
	private JButton btnSenior;
	private JButton btnAutoPlay;
	private JTextField txtTime;
	private JTextField txtUnmarkMines;
	private JTextField txtLeftBlock;
	private JButton[][] btnMines;
	private boolean[][] bMine;
	private int[][] nAroundMines;

	private boolean gaming = false;
	private boolean startMine = true;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Arbiter_v_1_17 frame = new Arbiter_v_1_17();
					frame.setVisible(true);
				} catch (Exception e) {
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Arbiter_v_1_17() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Minesweeper Arbiter_v_1_17 By JAVA");
		paintFrame();
	}

	private void paintFrame() {
		setBounds(100, 100, width, height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		panelBtn = new JPanel();
		contentPane.add(panelBtn, BorderLayout.NORTH);

		txtTime = new JTextField();
		startText(txtTime, "0.0");

		btnNewGame = new JButton("新游戏");
		panelBtn.add(btnNewGame);
		btnNewGame.addActionListener(new MyNewGameActionListener(row, col, bombNo));

		btnPrimary = new JButton("初级");
		panelBtn.add(btnPrimary);
		btnPrimary.setEnabled(col != PRIMARY_COL);
		btnPrimary.addActionListener(new MyNewGameActionListener(PRIMARY_ROW, PRIMARY_COL, PRIMARY_BOMB));

		btnMedium = new JButton("中级");
		panelBtn.add(btnMedium);
		btnMedium.setEnabled(col != MEDIUM_COL);
		btnMedium.addActionListener(new MyNewGameActionListener(MEDIUM_ROW, MEDIUM_COL, MEDIUM_BOMB));

		btnSenior = new JButton("高级");
		panelBtn.add(btnSenior);
		btnSenior.setEnabled(col != SENIOR_COL);
		btnSenior.addActionListener(new MyNewGameActionListener(SENIOR_ROW, SENIOR_COL, SENIOR_BOMB));

		btnAutoPlay = new JButton("扫雷外挂");
		panelBtn.add(btnAutoPlay);
		btnAutoPlay.addActionListener(new MyAutoPlayActionListener());

		txtUnmarkMines = new JTextField();
		startText(txtUnmarkMines, String.valueOf(unmarkMines));

		txtLeftBlock = new JTextField();
		startText(txtLeftBlock, String.valueOf(leftBlockNo));

		panelMines = new JPanel();
		contentPane.add(panelMines, BorderLayout.CENTER);
		panelMines.setLayout(new GridLayout(row, col, 0, 0));

		newBtnMines();

		addKeyListener(new KeyAdapter() {// 窗体增加键盘监听事件 //未实现
			@Override
			public void keyPressed(KeyEvent e) {
				JOptionPane.showMessageDialog(null, KeyEvent.getKeyText(e.getKeyCode()));
			}
		});
	}

	private void newGame() {
		blockNo = row * col;// 雷区方格数
		unmarkMines = bombNo;// 未标记雷数
		leftBlockNo = blockNo - bombNo;// 剩余方格数
		size = 800 / row;// 单个雷块尺寸
		height = row * size + 70;// 高度
		width = col * size;// 宽度
		gaming = false;
		startMine = true;
		contentPane.removeAll();
		contentPane.repaint();
		paintFrame();
		contentPane.revalidate();
	}

	private void newBtnMines() {
		btnMines = new JButton[row][col];
		Font font = new Font("宋体", Font.BOLD, size / 2);
		Insets insets = new Insets(0, 0, 0, 0);
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				btnMines[i][j] = new JButton();
				btnMines[i][j].setFont(font);
				btnMines[i][j].setMargin(insets);
				panelMines.add(btnMines[i][j]);
				btnMines[i][j].addMouseListener(new MyMouseListener(i, j));
			}
		}
	}

	private void startText(JTextField txt, String string) {
		txt.setEditable(false);
		txt.setText(string);
		txt.setColumns(4);
		panelBtn.add(txt);
	}

	/**
	 * 开始随机生成地雷
	 *
	 * @param x 首次左键单击按钮横坐标
	 * @param y 首次左键单击按钮纵坐标
	 */
	private void mineStart(int x, int y) {
		bMine = new boolean[row][col];
		while (mineNo() < bombNo) {
			bMine[r(row)][r(col)] = true;
			bMine[x][y] = false;
		}
		nAroundMines = new int[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (bMine[i][j]) {
					nAroundMines[i][j] = -1;
				} else {
					nAroundMines[i][j] = aroundNo(i, j);
				}
			}
		}
	}

	/**
	 * 计算已有地雷数量
	 *
	 * @return 已有地雷数量
	 */
	private int mineNo() {
		int n = 0;
		for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (bMine[i][j]) {
                    n++;
                }
            }
        }
		return n;
	}

	/**
	 * 根据坐标计算周围地雷数量
	 *
	 * @return 周围地雷数量
	 */
	private int aroundNo(int i, int j) {
		int n = 0;
		n += nearMine(i - 1, j - 1);
		n += nearMine(i - 1, j + 1);
		n += nearMine(i - 1, j);
		n += nearMine(i, j - 1);
		n += nearMine(i, j + 1);
		n += nearMine(i + 1, j - 1);
		n += nearMine(i + 1, j);
		n += nearMine(i + 1, j + 1);
		return n;
	}

	/**
	 * 伪随机数生成器
	 *
	 * @param max 最大值（不包含）
	 * @return 随机数
	 */
	private int r(int max) {
		return (int) (Math.random() * max);
	}

	/**
	 * 判断某点是否是地雷
	 *
	 * @param i 横坐标
	 * @param j 纵坐标
	 * @return 雷返回1，其他返回0
	 */
	private int nearMine(int i, int j) {
		return exist(i, j) && bMine[i][j] ? 1 : 0;
	}

	private class MyMouseListener extends MouseAdapter {
		int i, j;
		boolean leftClick, rightClick, doubleClick;

		MyMouseListener(int i, int j) {
			this.i = i;
			this.j = j;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			leftClick = e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1;
			rightClick = e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 1;
			doubleClick = e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2 || leftClick && rightClick;
//双击，双击或者左右键同时点击未实现；双击会先响应单击事件
			if (doubleClick && gaming && !btnMines[i][j].isEnabled() && nAroundMines[i][j] > 0
					&& aroundFlagNo(i, j) == nAroundMines[i][j]) {
				aroundClick(i, j);
			} else if (leftClick) {
				perBtnClick(i, j);
			} else if (rightClick && gaming && btnMines[i][j].isEnabled()) {
				if ("".equals(btnMines[i][j].getText())) {
					btnMines[i][j].setText("F");
					btnMines[i][j].setForeground(Color.GREEN);
					txtUnmarkMines.setText(String.valueOf(--unmarkMines));
				} else {
					btnMines[i][j].setText("");
					txtUnmarkMines.setText(String.valueOf(++unmarkMines));
				}
			}
		}

	}

	private void perBtnClick(int i, int j) {
		if (gaming && exist(i, j) && btnMines[i][j].isEnabled() && "".equals(btnMines[i][j].getText())) {
			btnMines[i][j].setText(nAroundMines[i][j] > 0 ? String.valueOf(nAroundMines[i][j]) : "");
			btnMines[i][j].setEnabled(false);

			if (bMine[i][j]) {
				gaming = false;
				showAllMines();
			} else {
				txtLeftBlock.setText(String.valueOf(--leftBlockNo));
				if (leftBlockNo < 1) {
					gaming = false;
					JOptionPane.showMessageDialog(Arbiter_v_1_17.this, "Win/t:" + txtTime.getText());
				}
				if (nAroundMines[i][j] == 0) {
                    aroundClick(i, j);
                }
			}
		} else if (startMine) {
			startMine = false;
			gaming = true;
			new Thread() {
				long timeStart = System.currentTimeMillis();

				@Override
				public void run() {
					while (gaming) {
						try {
							Thread.sleep(50);
						} catch (Exception ignored) {
						}
						txtTime.setText(
								String.format("%.1f", (double) (System.currentTimeMillis() - timeStart) / 1000));
						repaint();// 界面刷新
					}
				}
			}.start();
			mineStart(i, j);
			perBtnClick(i, j);
		}
	}

	private void aroundClick(int i, int j) {
		perBtnClick(i - 1, j - 1);
		perBtnClick(i - 1, j + 1);
		perBtnClick(i - 1, j);
		perBtnClick(i, j - 1);
		perBtnClick(i, j + 1);
		perBtnClick(i + 1, j - 1);
		perBtnClick(i + 1, j);
		perBtnClick(i + 1, j + 1);
	}

	private int aroundFlagNo(int i, int j) {
		int n = 0;
		n += nearFlag(i - 1, j - 1);
		n += nearFlag(i - 1, j + 1);
		n += nearFlag(i - 1, j);
		n += nearFlag(i, j - 1);
		n += nearFlag(i, j + 1);
		n += nearFlag(i + 1, j - 1);
		n += nearFlag(i + 1, j);
		n += nearFlag(i + 1, j + 1);
		return n;
	}

	private int nearFlag(int i, int j) {
		return exist(i, j) && "F".equals(btnMines[i][j].getText()) ? 1 : 0;
	}

	private void showAllMines() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (bMine[i][j]) {
//                    btnMines[i][j].setText("雷");
					btnMines[i][j].setForeground(Color.RED);
				}
			}
		}
	}

	private class MyAutoPlayActionListener implements ActionListener {

		int[][] autoMine = new int[row][col];
		Cycle checkOpenedCycle, markFlagCycle, clickCycle;
		List<Point>[][] dataMines;

		void leftBlock(List<Point> list, int i, int j) {
			if (exist(i, j) && btnMines[i][j].isEnabled() && autoMine[i][j] != -1) {
				list.add(new Point(i, j));
			}
		}

		void markData(int i, int j) {
			dataMines[i][j] = new ArrayList<>();
			dataMines[i][j].add(new Point(autoMine[i][j] - aroundFlagNo(i, j)));
			leftBlock(dataMines[i][j], i - 1, j - 1);
			leftBlock(dataMines[i][j], i - 1, j);
			leftBlock(dataMines[i][j], i - 1, j + 1);
			leftBlock(dataMines[i][j], i, j - 1);
			leftBlock(dataMines[i][j], i, j + 1);
			leftBlock(dataMines[i][j], i + 1, j - 1);
			leftBlock(dataMines[i][j], i + 1, j);
			leftBlock(dataMines[i][j], i + 1, j + 1);
		}

		void checkData(int x, int y) {
			for (int i = -2; i < 3; i++) {
				for (int j = -2; j < 3; j++) {
					if (exist(i, j) && dataMines[i][j] != null) {
						List<Point> max = dataMines[x][y];
						List<Point> min = dataMines[i][j];
						if (max.size() > min.size()) {
							int index;
							boolean contain = true;
							for (int k = 1; k < min.size(); k++) {
								if ((index = max.indexOf(min.get(k))) > 0) {
									max.remove(index);
								} else {
									contain = false;
									break;
								}
							}
							if (contain) {
								if (max.get(0).num <= min.get(0).num) {
									max.remove(0);
									markFlagByList(max);
								} else {
									max.remove(0);
									clickByList(max);
								}

							}
						}
					}
				}
			}

		}

		void markFlagByList(List<Point> list) {
			for (Point point : list) {
				perMarkFlag(point.x, point.y);
			}
		}

		void clickByList(List<Point> list) {
			for (Point point : list) {
				perBtnClick(point.x, point.y);
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			checkOpenedCycle = new Cycle() {
				@Override
				void function(int i, int j) {
					if (!btnMines[i][j].isEnabled()) {
						autoMine[i][j] = nAroundMines[i][j];
					}
				}
			};
			markFlagCycle = new Cycle() {
				@Override
				void function(int i, int j) {
					if (autoMine[i][j] > 0) {
						int leftBlockNum = aroundUnClickNo(i, j);
						if (autoMine[i][j] == leftBlockNum) {
							aroundMarkFlag(i, j);
						} else {
//                                markData(i, j);
						}
					}
				}
			};
			clickCycle = new Cycle() {
				@Override
				void function(int i, int j) {
					if (autoMine[i][j] > 0) {
						int leftBlockNum = aroundUnClickNo(i, j);
						int fNum = aroundFlagNo(i, j);
						if (leftBlockNum > autoMine[i][j]) {
                            if (autoMine[i][j] == fNum) {
                                aroundClick(i, j);
                            } else {
//                                checkData(i, j);
                            }
                        }
					}
				}
			};
			new Thread() {
				@Override
				public void run() {

					if (startMine) {
						perBtnClick(r(row), r(col));
					}
					while (gaming) {
						try {
							Thread.sleep(10);
						} catch (Exception ignored) {
						}
//                        dataMines = new ArrayList[row][col];
						dataMines = new List[row][col];
						checkOpenedCycle.cycle();
						markFlagCycle.cycle();
						clickCycle.cycle();
					}
					btnAutoPlay.setText("再战");
				}
			}.start();

		}

		private int aroundUnClickNo(int i, int j) {
			int n = 0;
			n += nearUnClickNo(i - 1, j - 1);
			n += nearUnClickNo(i - 1, j);
			n += nearUnClickNo(i - 1, j + 1);
			n += nearUnClickNo(i, j - 1);
			n += nearUnClickNo(i, j + 1);
			n += nearUnClickNo(i + 1, j - 1);
			n += nearUnClickNo(i + 1, j);
			n += nearUnClickNo(i + 1, j + 1);
			return n;
		}

		private void aroundMarkFlag(int i, int j) {
			perMarkFlag(i - 1, j - 1);
			perMarkFlag(i - 1, j);
			perMarkFlag(i - 1, j + 1);
			perMarkFlag(i, j - 1);
			perMarkFlag(i, j + 1);
			perMarkFlag(i + 1, j - 1);
			perMarkFlag(i + 1, j);
			perMarkFlag(i + 1, j + 1);

		}

		private void perMarkFlag(int i, int j) {
			if (exist(i, j) && btnMines[i][j].isEnabled()) {
				autoMine[i][j] = -1;
				btnMines[i][j].setText("F");
			}
		}

		private int nearUnClickNo(int i, int j) {
			return exist(i, j) && btnMines[i][j].isEnabled() ? 1 : 0;
		}

	}

	private boolean exist(int i, int j) {
		return i > -1 && i < row && j > -1 && j < col;
	}

	private class Point {
		int num, x, y;

		Point(int num) {
			this.num = num;
		}

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
                return true;
            }
			if (o == null || getClass() != o.getClass()) {
                return false;
            }
			Point point = (Point) o;
			return x == point.x && y == point.y;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}
	}

	/**
	 * 循环事件
	 */
	private abstract class Cycle {
		abstract void function(int i, int j);

		void cycle() {
			for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    function(i, j);
                }
            }
		}
	}

	/**
	 * 新游戏事件监听器
	 */
	private class MyNewGameActionListener implements ActionListener {

		int row, col, bombNo;

		MyNewGameActionListener(int row, int col, int bombNo) {
			this.row = row;
			this.col = col;
			this.bombNo = bombNo;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Arbiter_v_1_17.this.row = row;
			Arbiter_v_1_17.this.col = col;
			Arbiter_v_1_17.this.bombNo = bombNo;
			newGame();
		}

	}
}
