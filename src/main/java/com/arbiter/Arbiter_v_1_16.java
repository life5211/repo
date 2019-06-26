package com.arbiter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;

public class Arbiter_v_1_16 extends JFrame {

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
	private JTextField txtTime;
	private JTextField txtUnmarkMines;
	private JTextField txtLeftBlock;
	private JButton[][] btnMines;
	private boolean[][] bMine;
	private int[][] nAroundMines;

	private boolean gaming = false;
	private boolean startMine = true;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Arbiter_v_1_16 frame = new Arbiter_v_1_16();
					frame.setVisible(true);
				} catch (Exception e) {
				}
			}
		});
	}

	public Arbiter_v_1_16() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Minesweeper Arbiter By JAVA");
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
		setText(txtTime, "0.0");

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

		txtUnmarkMines = new JTextField();
		setText(txtUnmarkMines, String.valueOf(unmarkMines));

		txtLeftBlock = new JTextField();
		setText(txtLeftBlock, String.valueOf(leftBlockNo));

		panelMines = new JPanel();
		contentPane.add(panelMines, BorderLayout.CENTER);
		panelMines.setLayout(new GridLayout(row, col, 0, 0));

		btnMinesStart();
	}

	private void reStartGame() {
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

	private void btnMinesStart() {
		btnMines = new JButton[row][col];
		Font font = new Font("宋体", Font.BOLD, size / 2);
		Insets insets = new Insets(0, 0, 0, 0);
		KeyAdapter adapter = new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_F2) {
					reStartGame();
				}
			}

		};
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				btnMines[i][j] = new JButton();
				panelMines.add(btnMines[i][j]);
				btnMines[i][j].setFont(font);
				btnMines[i][j].setMargin(insets);
				btnMines[i][j].addMouseListener(new MyMouseListener(i, j));
				btnMines[i][j].addKeyListener(adapter);
			}
		}
	}

	private void setText(JTextField txt, String string) {
		txt.setEditable(false);
		txt.setText(string);
		txt.setColumns(4);
		panelBtn.add(txt);
	}

	private void mineStartProduce(int x, int y) {
		bMine = new boolean[row][col];
		while (calcAllMinesNo() < bombNo) {
			bMine[random(row)][random(col)] = true;
			bMine[x][y] = false;
		}
		nAroundMines = new int[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (bMine[i][j]) {
					nAroundMines[i][j] = -1;
				} else {
					nAroundMines[i][j] = aroundMineNo(i, j);
				}
			}
		}
	}

	private int calcAllMinesNo() {
		int n = 0;
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++)
				if (bMine[i][j])
					n++;
		return n;
	}

	private class MyMouseListener extends MouseAdapter {
		int i, j;
		boolean doubleClick;

		MyMouseListener(int i, int j) {
			this.i = i;
			this.j = j;
		}

		public void mousePressed(MouseEvent e) {
			doubleClick = e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2
					|| e.getModifiersEx() == (InputEvent.BUTTON3_DOWN_MASK + InputEvent.BUTTON1_DOWN_MASK);
			if (doubleClick) {
				btnDoubleClick(i, j);
			} else if (e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 1) {
				btnRightClick(i, j);
			}
		}

		public void mouseReleased(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
				btnLeftClick(i, j);
			}

			Arbiter_v_1_16.this.setFocusable(true);

		}

	}

	private void btnDoubleClick(int i, int j) {
		if (gaming && !btnMines[i][j].isEnabled() && nAroundMines[i][j] > 0
				&& aroundFlagNo(i, j) == nAroundMines[i][j]) {
			aroundClick(i, j);
		}
	}

	private void btnRightClick(int i, int j) {
		if (gaming && btnMines[i][j].isEnabled()) {
			if (btnMines[i][j].getText().equals("")) {
				btnMines[i][j].setText("F");
				btnMines[i][j].setForeground(Color.GREEN);
				txtUnmarkMines.setText(String.valueOf(--unmarkMines));
			} else {
				btnMines[i][j].setText("");
				txtUnmarkMines.setText(String.valueOf(++unmarkMines));
			}
		}
	}

	private void btnLeftClick(int i, int j) {
		if (gaming && exist(i, j) && btnMines[i][j].isEnabled() && btnMines[i][j].getText().equals("")) {
			btnMines[i][j].setText(nAroundMines[i][j] > 0 ? String.valueOf(nAroundMines[i][j]) : "");
			btnMines[i][j].setEnabled(false);

			if (bMine[i][j]) {
				gaming = false;
				showAllMines();
			} else {
				txtLeftBlock.setText(String.valueOf(--leftBlockNo));
				if (leftBlockNo < 1) {
					gaming = false;
					JOptionPane.showMessageDialog(Arbiter_v_1_16.this, "本次耗时 : " + txtTime.getText());
				}
				if (nAroundMines[i][j] == 0)
					aroundClick(i, j);
			}
		} else if (startMine) {
			startMine = false;
			gaming = true;
			new Thread() {
				long timeStart = System.currentTimeMillis();

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
			mineStartProduce(i, j);
			btnLeftClick(i, j);
		}
	}

	private void aroundClick(int i, int j) {
		btnLeftClick(i - 1, j - 1);
		btnLeftClick(i - 1, j + 1);
		btnLeftClick(i - 1, j);
		btnLeftClick(i, j - 1);
		btnLeftClick(i, j + 1);
		btnLeftClick(i + 1, j - 1);
		btnLeftClick(i + 1, j);
		btnLeftClick(i + 1, j + 1);
	}

	private int isFlag(int i, int j) {
		return exist(i, j) && "F".equals(btnMines[i][j].getText()) ? 1 : 0;
	}

	private int isMine(int i, int j) {
		return exist(i, j) && bMine[i][j] ? 1 : 0;
	}

	private int aroundMineNo(int i, int j) {
		int n = 0;
		n += isMine(i - 1, j - 1);
		n += isMine(i - 1, j + 1);
		n += isMine(i - 1, j);
		n += isMine(i, j - 1);
		n += isMine(i, j + 1);
		n += isMine(i + 1, j - 1);
		n += isMine(i + 1, j);
		n += isMine(i + 1, j + 1);
		return n;
	}

	private int aroundFlagNo(int i, int j) {
		int n = 0;
		n += isFlag(i - 1, j - 1);
		n += isFlag(i - 1, j + 1);
		n += isFlag(i - 1, j);
		n += isFlag(i, j - 1);
		n += isFlag(i, j + 1);
		n += isFlag(i + 1, j - 1);
		n += isFlag(i + 1, j);
		n += isFlag(i + 1, j + 1);
		return n;
	}

	private int random(int max) {
		return (int) (Math.random() * max);
	}

	private void showAllMines() {
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++)
				if (bMine[i][j]) {
					btnMines[i][j].setText("雷");
					btnMines[i][j].setForeground(Color.RED);
				}
	}

	private boolean exist(int i, int j) {
		return i > -1 && i < row && j > -1 && j < col;
	}

	private class MyNewGameActionListener implements ActionListener {

		int row, col, bombNo;

		MyNewGameActionListener(int row, int col, int bombNo) {
			this.row = row;
			this.col = col;
			this.bombNo = bombNo;
		}

		public void actionPerformed(ActionEvent e) {
			Arbiter_v_1_16.this.row = row;
			Arbiter_v_1_16.this.col = col;
			Arbiter_v_1_16.this.bombNo = bombNo;
			reStartGame();
		}
	}
}
