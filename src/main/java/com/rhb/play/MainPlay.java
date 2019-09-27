package com.rhb.play;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import com.rhb.common.ThreadCallable;

public class MainPlay {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		FutureTask<Integer> futureTask1 = new FutureTask<Integer>(new ThreadCallable());
		FutureTask<Integer> futureTask2 = new FutureTask<Integer>(new ThreadCallable());
		FutureTask<Integer> futureTask3 = new FutureTask<Integer>(new ThreadCallable());
		
		new Thread(futureTask1).start();
		new Thread(futureTask2).start();
		new Thread(futureTask3).start();
		
		System.out.println("结果："+futureTask1.get()+"->"+futureTask2.get());
	}
	
}	
