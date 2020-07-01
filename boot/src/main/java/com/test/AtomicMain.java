package com.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AtomicMain {

	public static void main(String[] args) throws InterruptedException {

		ExecutorService service = Executors.newCachedThreadPool();

		Count count = new Count();
		// 100个线程对共享变量进行加1
		for (int i = 0; i < 10000; i++) {
			service.execute(() -> count.increase());
		}

		// 等待上述的线程执行完
		service.shutdown();
		service.awaitTermination(1, TimeUnit.DAYS);

		System.out.println(count.getCount());
	}

}

class Count{

	// 共享变量
	private Integer count = 0;
	public Integer getCount() {
		return count;
	}
	public  void increase() {
		count++;
	}
}
