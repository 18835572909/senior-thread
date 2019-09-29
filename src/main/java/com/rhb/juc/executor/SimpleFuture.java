package com.rhb.juc.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName:  SimpleFuture   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: renhuibo
 * @date:   2019年9月29日 上午11:06:37
 * 
 * Future-I和FutureTask ：
 * 		重点： 将原子性的任务“独立化”，将任务拆分为：任务提交、任务执行、任务取消等操作
 */
public class SimpleFuture {
	
	class FirstFuture implements Callable<Double>{
		@Override
		public Double call() throws Exception {
			TimeUnit.SECONDS.sleep(50);
			System.out.println("FirstFuture: "+Thread.currentThread().getName());
			return 56.5d;
		}
	}
	
	class TwoFuture implements Callable<Double>{
		@Override
		public Double call() throws Exception {
			TimeUnit.SECONDS.sleep(3);
			System.out.println("TwoFuture: "+Thread.currentThread().getName());
			return 56.5d;
		}
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		SimpleFuture sf = new SimpleFuture();
		
		ExecutorService es = Executors.newFixedThreadPool(2);
		Future<Double> submit = es.submit(sf.new FirstFuture());
//		Future<Double> submit2 = es.submit(sf.new TwoFuture());
		if(!submit.isDone()) {
			submit.cancel(true);
		}
		System.out.println("submit:"+submit.get());
//		System.out.println("submit2:"+submit2.get());
		
		es.shutdown();
	}
	
}	
