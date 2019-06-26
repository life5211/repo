package com.study.thread;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author xhz
 * @version 1.0
 * @date 2019/6/26 10:35
 **/
public class ThreadStu {
    /**
     * 创建无边界大小的线程池
     */
    public static void createCachedThreadPool() {
        Executor Executor = null;
//        new ThreadPoolExecutor();

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            final int currentIndex = i;
            cachedThreadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + ", currentIndex is : " + currentIndex);
                countDownLatch.countDown();
            });
            cachedThreadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + ", currentIndex is= : " + currentIndex);
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("全部线程执行完毕");
    }

    public static void main(String[] args) {
        createCachedThreadPool();
    }
}

class Test {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5));

        MyTask myTask = new MyTask(1);
        for (int i = 0; i < 11; i++) {
            executor.execute(myTask);
            System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
                    executor.getQueue().size() + "，已执行玩别的任务数目：" + executor.getCompletedTaskCount());
        }
        executor.shutdown();
    }
}


class MyTask implements Runnable {
    private long taskNum;

    public MyTask(int num) {
        this.taskNum = num;
    }

    @Override
    public void run() {
        this.taskNum = System.nanoTime() - 72802888652800L;
        System.out.println("正在执行task " + taskNum);
//        try {
//            Thread.currentThread().sleep(2);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("task " + taskNum + "执行完毕");
    }
}
