package com.test;

import java.util.ArrayList;
import java.util.List;

public class S {
	public static void main(String[] args) {
		System.err.println(fun(5));
	}
	 static int fun(int i) {
		 return fun(i-1);
				 
	 }
}
