package com.rhb.play;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import org.junit.Test;

import com.rhb.common.ThreadRunnable;

public class TestPlay {
	
	@Test
	public void runnable() throws InterruptedException, ExecutionException {
		FutureTask task1 = new FutureTask(new ThreadRunnable(), "执行Over1");
		FutureTask task2 = new FutureTask(new ThreadRunnable(), "执行Over2");
		FutureTask task3 = new FutureTask(new ThreadRunnable(), "执行Over3");
		new Thread(task1).start();
		new Thread(task2).start();
		new Thread(task3).start();
		System.out.println(task1.get()+"-"+task2.get()+"-"+task3.get());
	}
	
}
