package com.hnf.main;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calc {
    @Test
    public void start() {
        System.out.println(run("-2+3*5"));
        String r = "\\d+(\\.\\d+^0$)" +
                "(((25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d?\\d))";
    }

    public double run(String calc) {
        return Double.valueOf(cycle(calc.replaceAll("[^\\d+\\-/*^\\(\\)]", "")));
    }

    public String cycle(String str) {
        //不存在运算符了，即递归结束，这里的正则为匹配所有的正负整数及小数
        if (str.matches("-?[0-9]+([.][0-9]+)?")) {
            return str;
        }
        String doStr;
        //去掉括号
        if (str.contains("(")) {
            int left = str.lastIndexOf('(');
            int right = str.indexOf(')', left);
            String sub = str.substring(left + 1, right);
            return str.substring(0, left) + cycle(sub) + str.substring(right + 1);
        }

        //计算pow
        if (str.contains("^")) {
            return cycle(calculate(str, '^'));
        }
        //计算乘除法
        if (str.contains("*") || str.contains("/")) {
            return cycle(calculate(str, '*', '/'));

        }
        //计算加减法
        if (str.contains("+") || str.contains("-")) {
            return cycle(calculate(str, '+', '-'));
        }
        return null;
    }

    public String calculate(String str, char... o) {
        String regex = "";
        for (char c : o) {
            regex += c;
        }
        regex = "-?[0-9]+([.][0-9]+)?[" + regex + "]\\-?[0-9]+([.][0-9]+)?";
        Matcher matcher = Pattern.compile(regex).matcher(str);
        double result = 0;
        if (matcher.find()) {
            String group = matcher.group();
            String[] nums = group.split("\\b[*/^+-]", 2);
            double x = Double.parseDouble(nums[0]);
            double y = Double.parseDouble(nums[1]);
            //操作符分支判断
            switch (group.charAt(nums[0].length())) {
                case '+':
                    result = x + y;
                    break;
                case '-':
                    result = x - y;
                    break;
                case '*':
                    result = x * y;
                    break;
                case '/':
                    result = x / y;
                    break;
                case '^':
                    result = Math.pow(x, y);
                    break;
                default:
                    break;
            }
        }
        return str.substring(0, matcher.start()) + result + str.substring(matcher.end());
    }

    public double calc(String[] nums, char o) throws NumberFormatException {
        double x = 0;
        double y = 0;
        x = Double.parseDouble(nums[0]);
        y = Double.parseDouble(nums[1]);
        switch (o) {
            case '+':
                return x + y;
            case '-':
                return x - y;
            case '*':
                return x * y;
            case '/':
                return x / y;
            case '^':
                return Math.pow(x, y);
            default:
                break;
        }
        throw new RuntimeException("illegal operator!");
    }

    @Test
    public void t() {
        char[] chars = {'a', 'b'};
        System.out.println(chars.toString());
        Pattern p = Pattern.compile("\\d{3,5}");
        String s = "123-*-*34345-234-00";
        Matcher m = p.matcher(s);
        while (m.find()) {
            System.out.println(m.start() + "-" + m.end());

        }
    }
}
