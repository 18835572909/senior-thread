package com.rhb.juc.tools;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author renhuibo  2019-09-26 21:25:26
 * @Description 
 * 	& 所有使用Callablbe都只是为了强化Callable的使用。
 * 
 * 保证所有线程执行完，再执行后续流程。同join()
 * 
 * 专业解释：
 * 		await()方法： 在count>0之前，一直wait(0)
 * 
 */
public class SimpleCountDownLatch {

	class Callable1 implements Callable<String>{
		private CountDownLatch countDownLatch;
		
		public Callable1(CountDownLatch countDownLatch) {
			this.countDownLatch = countDownLatch;
		}
		
		@Override
		public String call() throws Exception {
			// TODO Auto-generated method stub
			countDownLatch.countDown();
			System.out.println("Callable1-run");
			return "callable1-over";
		}
	}
	
	class Callable2 implements Callable<String>{
		private CountDownLatch countDownLatch;
		
		public Callable2(CountDownLatch countDownLatch) {
			this.countDownLatch = countDownLatch;
		}
		
		@Override
		public String call() throws Exception {
			// TODO Auto-generated method stub
			countDownLatch.countDown();
			System.out.println("Callable2-run");
			return "callable2-over";
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(2);
		
		Executor exe = Executors.newFixedThreadPool(2);
		exe.execute(new FutureTask<String>(new SimpleCountDownLatch().new Callable1(countDownLatch)));
		exe.execute(new FutureTask<String>(new SimpleCountDownLatch().new Callable2(countDownLatch)));
		
		countDownLatch.await();
		System.out.println("over");
		
	}
	
}
