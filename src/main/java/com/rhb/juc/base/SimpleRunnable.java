package com.rhb.juc.base;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author renhuibo  2019-09-26 21:40:42
 * @Description
 * 
 * 线程基础： 集成Thread类，或者实现Runnable接口
 */
public class SimpleRunnable {

	class Runnable1 implements Runnable{
		public void run() {
			System.out.println("Runnable1");
		}
	}
	

	class Runnable2 implements Runnable{
		public void run() {
			System.out.println("Runnable2");
		}
	}
	
	public static void main(String[] args) {
		Executor exe = Executors.newFixedThreadPool(2);
		exe.execute(new SimpleRunnable().new Runnable1());
		exe.execute(new SimpleRunnable().new Runnable2());
	}
}
