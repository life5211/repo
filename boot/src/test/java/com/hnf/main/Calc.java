package com.hnf.main;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calc {
    @Test
    public void start() {
        run("(5.0-(4.0+1.5*1.5+0.75-5.0)^2.2^3)");
        run("-2-1-1");
        run("(-2+3*5)*-(5-(1))");
        run("-(1.5*1.5-0.25)+1");
        run("-2*5*5");
    }

    public double run(String calc) {
        String str = calc.replaceAll("[^\\d+/*^().-]", "");
        System.out.print(str + "=\t");
        System.out.print(getResult(str) + "\t");
        System.out.println(calc(str));
        return (calc(str));
    }


    public Double getResult(String str) {
        if (str.matches("^(\\+|-)?\\d+(\\.\\d+)?$")) {
            return Double.valueOf(str);
        }
        if (str.contains("(")) {  //去掉括号
            int left = str.lastIndexOf('(');
            int right = str.indexOf(')', left);
            return getResult(str.substring(0, left) + getResult(str.substring(left + 1, right)) + str.substring(right + 1));
        }
        if (str.contains("^")) { //计算pow
            return getResult(calculate(str, 3));
        }
        if (str.contains("*") || str.contains("/")) { //计算乘除法
            return getResult(calculate(str, 2));
        }
        if (str.contains("+") || str.contains("-")) { //计算加减法
            return getResult(calculate(str, 1));
        }
        return null;
    }

    public String calculate(String str, int sign) {
        String regex = (sign == 1 ? "-?" : "") + "\\d+(\\.\\d+)?[" + (sign > 2 ? "\\^" : sign > 1 ? "*/" : "+-") + "]-?\\d+(\\.\\d+)?";
        Matcher matcher = Pattern.compile(regex).matcher(str);
        double result = 0;
        if (matcher.find()) {
            String group = matcher.group();
            String[] nums = group.split("\\b[*/^+-]", 2);
            double x = Double.parseDouble(nums[0]);
            double y = Double.parseDouble(nums[1]);
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

    private Double calc(String str) {
        str = str.replaceAll("\\+-", "-").replaceAll("--", "+");
        if (str.isEmpty()) {
            return 0d;
        }
        if (str.matches("-?\\d+(\\.\\d+)?")) {
            return Double.valueOf(str);
        }
        if (str.contains(")")) {
            int left = str.lastIndexOf("(");
            int right = str.indexOf(")", left);
            return calc(str.substring(0, left) + calc(str.substring(left + 1, right)) + str.substring(right + 1));
        }
        if (str.contains("-") && !str.contains("^-") && !str.contains("*-") && !str.contains("/-")) {
            int index = str.lastIndexOf("-");
            return calc(str.substring(0, index)) - calc(str.substring(index + 1));
        }
        if (str.contains("+")) {
            int index = str.lastIndexOf("+");
            return calc(str.substring(0, index)) + calc(str.substring(index + 1));
        }
        if (str.contains("*")) {
            int index = str.lastIndexOf("*");
            return calc(str.substring(0, index)) * calc(str.substring(index + 1));
        }
        if (str.contains("/")) {
            int index = str.lastIndexOf("/");
            return calc(str.substring(0, index)) / calc(str.substring(index + 1));
        }
        if (str.contains("^")) {
            int index = str.lastIndexOf("^");
            return Math.pow(calc(str.substring(0, index)), calc(str.substring(index + 1)));
        }
        return null;//出错
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

    @Test
    public void tr() {
        String s1 = "1234abcc--2345abbc-3456tttc";
        Pattern compile = Pattern.compile("(\\d{3})([a-z]{3})");
        Matcher matcher = compile.matcher(s1);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.println(matcher.group(i));
//                System.out.println(matcher.group(1));
//                System.out.println(matcher.group(2));
//                System.out.println(s1.substring(start, end));
            }
        }
    }

    @Test
    public void c() {
        Pattern compile = Pattern.compile("^51000000000$", Pattern.CASE_INSENSITIVE);
        System.out.println(compile);

    }

    @Test
    public void p() {
        System.out.printf("%f", 5f);
        System.out.printf("字母a的大写是：%c %n", 'A');
        System.out.printf("3>7的结果是：%b %n", 3 > 7);
        System.err.printf("100的一半是：%d %n", 100 / 2);
        System.out.printf("100的16进制数是：%x %n", 100);
        System.out.printf("100的8进制数是：%o %n", 100);
        System.out.printf("50元的书打8.5折扣是：%f 元%n", 50 * 0.85);
        System.out.printf("上面价格的16进制数是：%a %n", 50 * 0.85);
        System.out.printf("上面价格的指数表示：%e %n", 50 * 0.85);
        System.out.printf("上面价格的指数和浮点数结果的长度较短的是：%g %n", 50 * 0.85);
        System.out.printf("上面的折扣是%d%% %n", 85);
        System.out.printf("字母A的散列码是：%h %n", 'A');
    }
}
