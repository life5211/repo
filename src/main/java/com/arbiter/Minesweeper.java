package com.arbiter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Minesweeper extends JFrame {

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
    private int width = col * size + 20;// 宽度

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
    private boolean autoPlay = true;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Minesweeper frame = new Minesweeper();
                    frame.setVisible(true);
                } catch (Exception e) {
                }
            }
        });
    }

    public Minesweeper() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Minesweeper Arbiter By JAVA");
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

        btnAutoPlay = new JButton("扫雷外挂");
        panelBtn.add(btnAutoPlay);
        btnAutoPlay.addActionListener(new MyAutoPlayActionListener());

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

            Minesweeper.this.setFocusable(true);

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
                    JOptionPane.showMessageDialog(Minesweeper.this, "本次耗时 : " + txtTime.getText());
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
        for (Point p : getAroundPoint(i, j))
            btnLeftClick(p.x, p.y);
    }

    private int aroundMineNo(int i, int j) {
        int n = 0;
        for (Point p : getAroundPoint(i, j))
            n += bMine[p.x][p.y] ? 1 : 0;
        return n;
    }

    private int aroundFlagNo(int i, int j) {
        int n = 0;
        for (Point p : getAroundPoint(i, j))
            n += "F".equals(btnMines[p.x][p.y].getText()) ? 1 : 0;
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

    private class MyAutoPlayActionListener implements ActionListener {

        int[][] autoMine = new int[row][col];
        List<MineData> mineDataList;

        public void actionPerformed(ActionEvent e) {
            if (autoPlay) {
                new Thread() {
                    public void run() {
                        autoPlay = false;
                        if (startMine) {
                            btnLeftClick(random(row), random(col));
                        }
                        while (gaming) {
                            try {
                                Thread.sleep(random(100));
                            } catch (Exception ignored) {
                            }
                            startAutoMineArray();
                            startMineDataList();
                            doMineDataList();
                            clickBlock();
                        }
                        autoPlay = true;
                        btnAutoPlay.setText("再战");
                        System.gc();
                    }
                }.start();
            }
        }

        void startAutoMineArray() {
            for (int i = 0; i < row; i++)
                for (int j = 0; j < col; j++)
                    if (!btnMines[i][j].isEnabled())
                        autoMine[i][j] = nAroundMines[i][j];
        }

        void startMineDataList() {
            mineDataList = new ArrayList<>();
            for (int i = 0; i < row; i++)
                for (int j = 0; j < col; j++) {
                    List<Point> aroundPoint = getAroundPoint(i, j);
                    if (autoMine[i][j] > 0 && getAroundUnClickNo(aroundPoint) > 0) {
                        mineDataList.add(new MineData(autoMine[i][j] - aroundFlagNo(i, j), getLeftBlockPointList(aroundPoint)));
                    }
                }
        }

        void doMineDataList() {
            for (MineData max : mineDataList) {
                for (MineData min : mineDataList) {
                    if (max != min && max.listPoint.containsAll(min.listPoint)) {
                        max.listPoint.removeAll(min.listPoint);
                        max.aroundMinesNo -= min.aroundMinesNo;
                        if (max.listPoint.size() == 0) {
                            mineDataList.remove(max);
                            doMineDataList();
                            return;
                        }
                    }
                }
            }

//            boolean bDoList = true;
//            while (bDoList) {
//                bDoList = false;
//                for (int i = 0; i < mineDataList.size(); i++) {
//                    MineData max = mineDataList.get(i);
//                    if (max.listPoint.size() > 0) {
//                        for (int j = 0; j < mineDataList.size(); j++) {
//                            MineData min = mineDataList.get(j);
//                            if (i != j && min.listPoint.size() > 0) {
//                                if (max.listPoint.containsAll(min.listPoint)) {
//                                    max.listPoint.removeAll(min.listPoint);
//                                    max.aroundMinesNo -= min.aroundMinesNo;
//                                    bDoList = true;
//                                    if (max.listPoint.size() == 0) {
//                                        mineDataList.remove(i--);
//                                    }
//                                }
//                            } else if (min.listPoint.size() == 0) {
//                                mineDataList.remove(j--);
//                            }
//                        }
//                    } else {
//                        mineDataList.remove(i--);
//                    }
//                }
//            }
        }

        void clickBlock() {
            for (MineData data : mineDataList) {
                if (data.aroundMinesNo == 0) {
                    clickByList(data.listPoint);
                } else if (data.aroundMinesNo == data.listPoint.size()) {
                    markFlagByList(data.listPoint);
                }
            }
        }

        List<Point> getLeftBlockPointList(List<Point> list) {
            for (int i = 0; i < list.size(); i++) {
                Point p = list.get(i);
                if (!btnMines[p.x][p.y].isEnabled() || !"".equals(btnMines[p.x][p.y].getText())) {
                    list.remove(i--);
                }
            }
            return list;
        }

        void markFlagByList(List<Point> list) {
            for (Point point : list) {
                perMarkFlag(point.x, point.y);
            }
        }

        void clickByList(List<Point> list) {
            for (Point point : list) {
                btnLeftClick(point.x, point.y);
            }
        }

        private int getAroundUnClickNo(List<Point> list) {
            int n = 0;
            for (Point p : list) {
                n += btnMines[p.x][p.y].isEnabled() ? 1 : 0;
            }
            return n;
        }

        private void perMarkFlag(int i, int j) {
            if (exist(i, j) && btnMines[i][j].isEnabled()) {
                autoMine[i][j] = -1;
                btnMines[i][j].setText("F");
            }
        }
    }


    private class Point {
        int x, y;
//        int mineNO, flagNo, unkonwnNo;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        public int hashCode() {
            return x * 1000 + y;
        }
    }

    private class MineData {
        int aroundMinesNo;
        List<Point> listPoint;

        MineData(int aroundMinesNo, List<Point> listPoint) {
            this.aroundMinesNo = aroundMinesNo;
            this.listPoint = listPoint;
        }
    }

    private class MyNewGameActionListener implements ActionListener {

        int row, col, bombNo;

        MyNewGameActionListener(int row, int col, int bombNo) {
            this.row = row;
            this.col = col;
            this.bombNo = bombNo;
        }

        public void actionPerformed(ActionEvent e) {
            Minesweeper.this.row = row;
            Minesweeper.this.col = col;
            Minesweeper.this.bombNo = bombNo;
            reStartGame();
        }
    }
}
