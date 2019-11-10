package com.hnf.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author user17
 * @version 1.0
 * @date 2019-9-28 11:58
 */
public class Test {
    public static void main(String[] args) {
        Writer writer = null;

        try {
            writer = new BufferedWriter(new PrintWriter(new File("t.txt")));
            writer.write("1");
            writer.write("2-----------\n");
            writer.write("3-----------\r");
            writer.write(4 + System.lineSeparator());
            writer.write("5-----------");
            writer.write("6-----------");
            writer.write(new Date() +"");

            List<String> list = new ArrayList<>();
            String line = "1";
            list.add(line);
            line = "2";
            list.add(line);
            line = "3";
            list.add("" + line);
            writer.write(list.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
