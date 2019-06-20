package com.hnf.main;

import com.sun.istack.internal.Nullable;
import org.junit.Test;

import javax.validation.constraints.Null;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calc {
    @Test
    public void start() {
        run("-2+3*5");
        String r = "\\d+(\\.\\d+^0$)" +
                "(((25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d?\\d))";
    }

    public double run(String calc) {
        String regex = "[^\\d+\\-/*^\\(\\)]";
        calc = calc.replaceAll(regex, "");
        return Double.valueOf(get(calc));
    }

    public String get(String str) {
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
            return str.substring(0, left) + get(sub) + str.substring(right + 1);
        }
        //计算乘除法
        if (str.contains("*") || str.contains("/")) {

//            Pattern pattern = Pattern.compile("[0-9]+([.][0-9]+)?[*/]-?[0-9]+([.][0-9]+)?");
            Matcher matcher = Pattern.compile("[0-9]+([.][0-9]+)?[*/]-?[0-9]+([.][0-9]+)?").matcher(str);
            if (matcher.find()) {
                String group = matcher.group();
//                String[] s = group.split("[*/]");
                return str.substring(0, matcher.start()) + calc(group.split("[*/]")) + str.substring(matcher.end());
            }
            return "0";

        }


        return null;
    }


    public double calc(String[] strings) {
        double x = Double.parseDouble(strings[0]);
        char o = strings[1].charAt(0);
        double y = Double.parseDouble(strings[2]);
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
        Pattern p = Pattern.compile("\\d{3,5}");
        String s = "123-*-*34345-234-00";
        Matcher m = p.matcher(s);
        while (m.find()) {
            System.out.println(m.start() + "-" + m.end());

        }
    }
}
