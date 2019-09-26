package com.rhb.juc.tools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author renhuibo  2019-09-26 22:10:13
 * @Description
 * 
 * new的使用初始化barrier的个数，只有调用await()个数和初始化个数相等后，线程才会执行后续操作，否者所有线程都将处于等待。
 * 
 * 上句话描述的不是那么清楚：
 * 		只用await()数达到了初始化时定义的个数，相应线程才会执行线程中await()后的操作。否则相关线程将全部阻塞在await()
 * 
 */
public class SimpleCyclicBarrier {

	static CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
	
	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println(Thread.currentThread().getName()+"-start");
					cyclicBarrier.await();
					System.out.println(Thread.currentThread().getName()+"-end");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println(Thread.currentThread().getName()+"-start");
					cyclicBarrier.await();
					System.out.println(Thread.currentThread().getName()+"-end");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		cyclicBarrier.await();
		System.out.println("over");
	}
	
}
