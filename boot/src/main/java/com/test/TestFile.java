package com.test;

import java.util.ArrayList;
import java.util.List;

public class TestFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<>();
		list.add(0);
		for (int in = 1; in < 1 + 15; in++) {
			for (int i = 0; i < list.size(); i++) {
				Integer n = list.get(i);
				if (n > 1)
					list.add(0);
				list.set(i, ++n);

			}
			String s = "";
			for (Integer i : list) {
				s += i + ",";
			}
			System.out.println(in + "-" + list.size() + "-" + fun(in) + "\t" + s);
		}
		System.out.println(fun(11));
		;
	}

	public static int fun(int i) {
		return i < 3 ? 1 : fun(i - 1) + fun(i - 2);
	}

}
