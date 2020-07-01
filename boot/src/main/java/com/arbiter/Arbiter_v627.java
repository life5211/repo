package com.arbiter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author xhz
 * @version 1.0.4
 * @date 2019/6/27
 */
public class Arbiter_v627 extends JFrame {

    private final int PRIMARY_ROW = 8;
    private final int PRIMARY_COL = 8;
    private final int PRIMARY_BOMB = 10;
    private final int MEDIUM_ROW = 16;
    private final int MEDIUM_COL = 16;
    private final int MEDIUM_BOMB = 40;
    private final int SENIOR_ROW = 16;
    private final int SENIOR_COL = 30;
    private final int SENIOR_BOMB = 99;

    private int row = PRIMARY_ROW;
    private int col = PRIMARY_COL;
    // 雷数
    private int bombNo = PRIMARY_BOMB;
    // 雷区方格数
    private int blockNo = row * col;
    // 未标记雷数
    private int unmarkMines = bombNo;
    // 剩余方格数
    private int leftBlockNo = blockNo - bombNo;
    // 单个雷块尺寸
    private int size = 800 / row;
    // 高度
    private int height = row * size + 70;
    // 宽度
    private int width = col * size + 20;
    // 总面板
    private JPanel contentPane;
    // 功能区面板
    private JPanel panelBtn;

    private JPanel panelMines;

    private JButton btnNewGame;
    private JButton btnPrimary;
    private JButton btnMedium;
    private JButton btnSenior;
    private JTextField txtTime;
    private JTextField txtUnmarkMines;
    private JTextField txtLeftBlock;
    private JButton[][] btnMines;
    private boolean[][] bMine;
    private int[][] nAroundMines;

//    private static ExecutorService executorService = null;

    private boolean gaming = false;
    private boolean startMine = true;

    public static void main(String[] args) {
//        executorService = Executors.newCachedThreadPool();
//        executorService.execute();
        EventQueue.invokeLater(() -> {
            try {
                Arbiter_v627 frame = new Arbiter_v627();
                frame.setVisible(true);
            } catch (Exception e) {
            }
        });
    }

    public Arbiter_v627() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Minesweeper Arbiter_v627 By JAVA");
        paintFrame();
    }

    private void paintFrame() {
        setBounds(10, 10, width, height);
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
        width = col * size + 20;// 宽度
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

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                btnMines[i][j] = new JButton();
                panelMines.add(btnMines[i][j]);
                btnMines[i][j].setFont(font);
                btnMines[i][j].setMargin(insets);
                btnMines[i][j].addMouseListener(new MyMouseListener(i, j));
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
        Random random = new Random();
        while (calcAllMinesNo() < bombNo) {
            bMine[random.nextInt(row)][random.nextInt(col)] = true;
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
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (bMine[i][j]) {
                    n++;
                }
            }
        }
        return n;
    }

    private class MyMouseListener extends MouseAdapter {
        int i, j;
        boolean doubleClick;

        MyMouseListener(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            doubleClick = e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2
                    || e.getModifiersEx() == (InputEvent.BUTTON3_DOWN_MASK + InputEvent.BUTTON1_DOWN_MASK);
            if (doubleClick) {
                btnDoubleClick(i, j);
            } else if (e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 1) {
                btnRightClick(i, j);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
                btnLeftClick(i, j);
            }

            Arbiter_v627.this.setFocusable(true);

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

    private void btnLeftClick(int i, int j) {
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
                    JOptionPane.showMessageDialog(Arbiter_v627.this, "本次耗时 : " + txtTime.getText());
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
            mineStartProduce(i, j);
            btnLeftClick(i, j);
        }
    }

    private void aroundClick(int i, int j) {
        for (Point p : getAroundPoint(i, j)) {
            btnLeftClick(p.x, p.y);
        }
    }

    private int aroundMineNo(int i, int j) {
        int n = 0;
        for (Point p : getAroundPoint(i, j)) {
            n += bMine[p.x][p.y] ? 1 : 0;
        }
        return n;
    }

    private int aroundFlagNo(int i, int j) {
        int n = 0;
        for (Point p : getAroundPoint(i, j)) {
            n += "F".equals(btnMines[p.x][p.y].getText()) ? 1 : 0;
        }
        return n;
    }

    private void showAllMines() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (bMine[i][j]) {
                    btnMines[i][j].setText("雷");
                    btnMines[i][j].setForeground(Color.RED);
                }
            }
        }
    }

    private List<Point> getAroundPoint(int i, int j) {
        List<Point> list = new ArrayList<>();
        for (int k = -1; k < 2; k++) {
            for (int l = -1; l < 2; l++) {
                if (exist(i + k, j + l) && (k != 0 || l != 0)) {
                    list.add(new Point(i + k, j + l));
                }
            }
        }
        return list;
    }

    private boolean exist(int i, int j) {
        return i > -1 && i < row && j > -1 && j < col;
    }

    private class Point {
        int x, y;

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
            return x * 1000 + y;
        }
    }

    private class MyNewGameActionListener implements ActionListener {

        int row, col, bombNo;

        MyNewGameActionListener(int row, int col, int bombNo) {
            this.row = row;
            this.col = col;
            this.bombNo = bombNo;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Arbiter_v627.this.row = row;
            Arbiter_v627.this.col = col;
            Arbiter_v627.this.bombNo = bombNo;
            reStartGame();
        }
    }
}
