package com.study.temp.java8;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class MStream {
    public static void main(String[] args) {
        new MStream().run();
    }

    void run() {
        {
            Stream<List<Integer>> stream = Stream.of(Arrays.asList(1, 2), Arrays.asList(3, 4, 5));
            stream.flatMap(list -> list.stream()).forEach(i -> System.out.print(i));
        }
        {
            // 找出最长的单词
            Stream<String> stream = Stream.of("I", "love", "you", "too");
            Optional<String> longest = stream.reduce((s1, s2) -> s1.length() >= s2.length() ? s1 : s2);
            //Optional<String> longest = stream.max((s1, s2) -> s1.length()-s2.length());
            System.out.println(longest.get());
        }
        {
// 求单词长度之和
            Stream<String> stream = Stream.of("I", "love", "you", "too");
            Integer lengthSum = stream.reduce(0, // 初始值　// (1)
                    (sum, str) -> sum + str.length(), // 累加器 // (2)
                    (a, b) -> a + b); // 部分和拼接器，并行执行时才会用到 // (3)
// int lengthSum = stream.mapToInt(str -> str.length()).sum();
            System.out.println(lengthSum);
        }
        {

        }
        {

        }
        {

        }
        {

        }
        {

        }
        {

        }
        {

        }
        {

        }
        {

        }
        {

        }

    }
}
