package com.test;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class Test {
	Robot robot;
	Toolkit toolkit;
	Dimension dimension;
	Rectangle rectangle;
	BufferedImage image;
	int left, top;
	int[][] mine;
	int pixelColor;
	int width, height;
	private final int rgb255 = -1;
	private final int rgb128 = -8355712;
	private final int rgb192 = -4144960;
	private final int b128 = -16777088;
	private final int b255 = -16776961;
	private final int g128 = -16744448;
	private final int gb128 = -16744320;
	private final int r128 = -8388608;
	private final int r255 = -65536;

	private final int black = -16777216;
	Color color;
	private final int PRIMARY_ROW = 8;// 初级行数
	private final int PRIMARY_COL = 8;// 初级列数
	private final int PRIMARY_BOMB = 10;// 初级雷数

	private final int MEDIUM_ROW = 16;// 中级行数
	private final int MEDIUM_COL = 16;// 中级列数
	private final int MEDIUM_BOMB = 40;// 中级雷数

	private final int SENIOR_ROW = 16;// 高级行数
	private final int SENIOR_COL = 30;// 高级列数
	private final int SENIOR_BOMB = 99;// 高级雷数

	private int row = SENIOR_ROW;// 行数
	private int col = SENIOR_COL;// 列数
	private int bombNo = SENIOR_BOMB;// 雷数
	private int blockNo = row * col;// 雷区方格数
	private int unmarkMines = bombNo;// 未标记雷数
	private int leftBlockNo = blockNo - bombNo;// 剩余方格数
	private int size = 16;// 单个雷块尺寸

	public void strat() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		toolkit = Toolkit.getDefaultToolkit();
		dimension = toolkit.getScreenSize();
		width = dimension.width;
		height = dimension.height;
		rectangle = new Rectangle(0, 0, width, height);
		mine = new int[row][col];
		for (int i = 0; i < mine.length; i++) {
			for (int j = 0; j < mine[i].length; j++) {
				mine[i][j] = -9;
			}
		}
	}

	void getPoint() {
		for (int i = 20; i < 24; i++) {
			for (int j = 20; j < 24; j++) {
				color = robot.getPixelColor(i, j);
				if (color.getRGB() != black) {
					System.out.println("(" + i + "," + j + ")");
					left = i + 13;
					top = j + 101;
//					getStart(i, j);
					return;
				}
			}
		}

	}

	void screen() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				screenP(i, j);
			}
		}

	}

	void screenP(int i, int j) {
		switch (robot.getPixelColor(left + j * 16, top + i * 16).getRGB()) {
		case rgb255:
			switch (robot.getPixelColor(6 + left + j * 16, 6 + top + i * 16).getRGB()) {
			case r255:
				mine[i][j] = -1; // Flag /已知的地雷
				break;

			default:
				break;
			}
			break;
		case rgb128:
			if (rgb255 == robot.getPixelColor(6 + left + j * 16, 6 + top + i * 16).getRGB()) {
				mine[i][j] = -8; // 爆炸的地雷
			} else
				switch (robot.getPixelColor(9 + left + j * 16, 7 + top + i * 16).getRGB()) {
				case b255:
					mine[i][j] = 1; // =1
					break;
				case g128:
					mine[i][j] = 2; // =2
					break;
				case r255:
					mine[i][j] = 3; // =3
					break;
				case b128:
					mine[i][j] = 4; // =4
					break;
				case r128:
					mine[i][j] = 5; // =5
					break;
				case gb128:
					mine[i][j] = 6; // =6
					break;
				case black:
					mine[i][j] = 7; // =7
					break;
				case rgb128:
					mine[i][j] = 8; // =8
					break;
				case rgb192:
					mine[i][j] = 0; // =0
					break;

				default:
					break;
				}
			break;

		default:
			break;
		}
	}

	void print() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				System.out.print((mine[i][j] < 0 ? "" : " ") + mine[i][j] + ",");
			}
			System.out.println();
		}
	}

// (Left+13,Top+101)
	void getStart(int x, int y) {
		for (int i = x + 12; i < x + 14; i++) {
			for (int j = y + 98; j < y + 103; j++) {
				color = robot.getPixelColor(i, j);
				if (color.getRGB() == rgb255) {
					System.out.print("(" + i + "," + j + ")");
//					getEndP(i, j);
					return;
				}
			}
		}
	}

	void getEndP(int x, int y) {
		System.out.println(robot.getPixelColor(323, 139));
		System.out.println(robot.getPixelColor(322, 139));
		System.out.println(robot.getPixelColor(323, 138));

	}

	public Color getColor(int x, int y) {
		image = robot.createScreenCapture(rectangle);
		pixelColor = image.getRGB(x, y);
		return new Color(16777216 + pixelColor);
	}

	public Color getScreenPixel(int x, int y) throws AWTException { // 函数返回值为颜色的RGB值。
		Robot rb = new Robot();
		Toolkit tk = Toolkit.getDefaultToolkit(); // 获取缺省工具包
		Dimension di = tk.getScreenSize(); // 屏幕尺寸规格
		Rectangle rec = new Rectangle(0, 0, di.width, di.height);
		BufferedImage bi = rb.createScreenCapture(rec);
		int pixelColor = bi.getRGB(x, y);
		Color color = new Color(16777216 + pixelColor);
		return color; // pixelColor的值为负，经过实践得出：加上颜色最大值就是实际颜色值。
	}

	public static void main(String[] args) throws AWTException {
		Test test = new Test();
		test.strat();
		test.getPoint();
		test.screen();
		test.print();

//		for (int i = 0; i < 800; i++) {
//			System.out.println(i + "" + test.robot.getPixelColor(i, 545));
//		}

	}

	class Point {
		int x, y;
		Color color;

		public Point() {
		}

		public Point(int x, int y, Color color) {
			super();
			this.x = x;
			this.y = y;
			this.color = color;
		}

		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + ", color=" + color + "]";
		}

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public Color getColor() {
			return color;
		}

		public void setColor(Color color) {
			this.color = color;
		}

	}

	class Window {
		int top, left, width, height;

		public Window(int top, int left, int width, int height) {
			this.top = top;
			this.left = left;
			this.width = width;
			this.height = height;
		}

		public Window() {
		}

		public Window(int top, int left) {
			super();
			this.top = top;
			this.left = left;
		}

		@Override
		public String toString() {
			return "Window [top=" + top + ", left=" + left + ", width=" + width + ", height=" + height + "]";
		}

		public int getTop() {
			return top;
		}

		public void setTop(int top) {
			this.top = top;
		}

		public int getLeft() {
			return left;
		}

		public void setLeft(int left) {
			this.left = left;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}
	}
}
