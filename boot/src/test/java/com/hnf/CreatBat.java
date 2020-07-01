package com.hnf;

import org.junit.Test;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CreatBat {
    @Test
    public void method() {
        List<String> strings = Arrays.asList("---", null);
        System.out.println(strings.size());
        System.out.println(strings.indexOf(null));
    }

    @Test
    public void creatBat() {
        creat("D:\\jar", "HoneyCombVirtualIdentity3");
    }

    public void creat(String path, String name) {
//        name += new SimpleDateFormat("yyyyMMddHHmm").format(new Date()).substring(2);
        File file = new File(path + "/" + name + ".bat");
        PrintStream ps = null;
        try {
//            if (!file.exists()) {
//                file.createNewFile();
//            }
            ps = new PrintStream(file);
            ps.println("chcp 65001");
            ps.println("title " + name);
            ps.println("color 0a");
            ps.println("java -jar " + name + ".jar");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
        }

    }

//    public static <T, K, D, A, M extends Map<K, D>> Collector<T, ?, M>
//    groupingBy(Function<? super T, ? extends K> classifier,
//                                  Supplier<M> mapFactory,
//                                  Collector<? super T, A, D> downstream) {
//        Supplier<A> downstreamSupplier = downstream.supplier();
//        BiConsumer<A, ? super T> downstreamAccumulator = downstream.accumulator();
//        BiConsumer<Map<K, A>, T> accumulator = (m, t) -> {
//            K key = Objects.requireNonNull(classifier.apply(t), "element cannot be mapped to a null key");
//            A container = m.computeIfAbsent(key, k -> downstreamSupplier.getResult());
//            downstreamAccumulator.accept(container, t);
//        };
//        BinaryOperator<Map<K, A>> merger = Collectors.<K, A, Map<K, A>>mapMerger(downstream.combiner());
//        @SuppressWarnings("unchecked")
//        Supplier<Map<K, A>> mangledFactory = (Supplier<Map<K, A>>) mapFactory;
//
//        if (downstream.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)) {
//            return new CollectorImpl<>(mangledFactory, accumulator, merger, CH_ID);
//        }
//        else {
//            @SuppressWarnings("unchecked")
//            Function<A, A> downstreamFinisher = (Function<A, A>) downstream.finisher();
//            Function<Map<K, A>, M> finisher = intermediate -> {
//                intermediate.replaceAll((k, v) -> downstreamFinisher.apply(v));
//                @SuppressWarnings("unchecked")
//                M castResult = (M) intermediate;
//                return castResult;
//            };
//            return new CollectorImpl<>(mangledFactory, accumulator, merger, finisher, CH_NOID);
//        }
//    }

    /**
     * 应用服务器产生认证原文(第二步 服务端产生认证原文)
     *
     * @return 认证原文
     */
    @Test
    public void generateRandomNumByApplication() {
        String num = "1234567890abcdefghijklmnopqrstopqrstuvwxyz";
        int size = 1000;
        char[] charArray = num.toCharArray();
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < size; i++) {
//            System.out.println(System.currentTimeMillis() + "\t" + System.nanoTime());
            sb.append(charArray[random.nextInt(2)]);
        }
        System.out.println(sb);
    }
        @Test
    public void generat() {
//            System.out.println("12345".substring(0, 6));

            String mapStr = "function(){\n"
                    + "	var time = this.UPDATETIME;\n"
                    + "	if(time === null){\n"
                    + "		time = 0;\n"
                    + "	}\n"
                    + "	var date = new Date(time);	\n"
                    + "	checkDate(date);\n"
                    + "}\n"
                    + "\n"
                    + "function checkDate(date){\n"
                    + "	var year = date.getFullYear();\n"
                    + "	var month = date.getMonth();\n"
                    + "	var day = date.getDate();\n"
                    + "	var newDate = new Date(year,month,day);\n"
                    + "	emit(newDate,1);\n"
                    + "}";
            String reduceStr = "function(key,values){	"
                    + "     return Array.sum(values);   "
                    + "};";
            System.out.println(mapStr);

            System.out.println(reduceStr);
        }


}
