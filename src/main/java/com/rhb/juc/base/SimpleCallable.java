package com.rhb.juc.base;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author renhuibo  2019-09-26 21:30:28
 * @Description
 * 
 * 可以将线程中异常抛出，并且返回线程的执行结果
 * 
 * 从代码字面上开，可以理解Callable更下一层（仅供记忆）
 */
public class SimpleCallable {
	
	class Callable1 implements Callable<String>{
		@Override
		public String call() throws Exception {
			System.out.println("call1");
			return "callable1";
		}
	}
	
    class Callable2 implements Callable<String>{
		@Override
		public String call() throws Exception {
			System.out.println("call2");
			return "callable2";
		}
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		FutureTask<String> task1 = new FutureTask<>(new SimpleCallable().new Callable1());
		FutureTask<String> task2 = new FutureTask<>(new SimpleCallable().new Callable2());
		
		Executor executor = Executors.newFixedThreadPool(2);
		executor.execute(task1);
		executor.execute(task2);
		
		System.out.println("task1:"+task1.get()+",task2:"+task2.get());
	}
	
	public void run() {
		FutureTask<String> task1 = new FutureTask<>(new SimpleCallable().new Callable1());
		FutureTask<String> task2 = new FutureTask<>(new SimpleCallable().new Callable2());
		
		new Thread(task1).start();
		new Thread(task2).start();
	}
}
