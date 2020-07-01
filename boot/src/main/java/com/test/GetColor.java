package com.test;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetColor extends BaseDao {

	int add(List<P> list) {
		try {
			connect();
			sql = "insert into color values ";
			StringBuffer sb = new StringBuffer();
			if (list.size() < 1) {
				return 0;
			} else {
				for (P p : list) {
					sb.append(" (null,'" + p.name + "'," + p.x + "," + p.y + "," + p.r + "," + p.g + "," + p.b + ","
							+ p.rgb + "),");
				}
				sql += sb.substring(0, sb.length() - 1) + ";";
			}
			ps = conn.prepareStatement(sql);
			return ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return 0;
	}

	void each(int x, int y) {
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<P> list = new ArrayList<P>();
		Color color;
		for (int i = x; i < x + 16; i++) {
			for (int j = y; j < y + 16; j++) {
				color = robot.getPixelColor(i, j);
				list.add(new P("isMine", (i - x), (j - y), color.getRed(), color.getGreen(), color.getBlue(),
						color.getRGB()));
			}
		}
		System.out.println(add(list));
	}

	public static void main(String[] args) {
		GetColor getColor = new GetColor();
		getColor.each(323, 139);

	}

	class P {
		String name;
		int x, y, r, g, b, rgb;

		public P(String name, int x, int y, int r, int g, int b, int rgb) {
			super();
			this.name = name;
			this.x = x;
			this.y = y;
			this.r = r;
			this.g = g;
			this.b = b;
			this.rgb = rgb;
		}

	}
}
