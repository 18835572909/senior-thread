package com.rhb.juc.tools;

import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author renhuibo  2019-09-26 22:22:13
 * @Description
 * 
 * 业务场景： 每个sheel中存放一个用户的今年的账户信息，计算excel文件中所有账户总额。
 * 
 * 结果： new CyclicBarrier(parties,Runnable)方法
 * 		当所有线程到达屏障，且执行完毕后，再执行Runnable线程
 * 
 * CountDownLatch和CyclicBarrier比较：
 * 		1. CountDownLatch是阻塞单个线程，CyclicBarrier阻塞一组线程。
 * 		2. CountDownLatch是减法算法，CyclicBarrier是加法算法
 */
public class CyclicBarrierService implements Runnable{

	//存放用户信息和用户账户总额
	private ConcurrentHashMap<String, Double> sheels = new ConcurrentHashMap<>();
	
	//启动线程的线程池 : 假定10个sheel（也可以理解为10列，丰富的场景。。。）
	private Executor exe = Executors.newFixedThreadPool(1);
	
	//限定个数的可循环屏障
	private final CyclicBarrier cyclicBarrier = new CyclicBarrier(10,this);
	
	//限定主程序的执行时间
	private CountDownLatch countDownLatch = new CountDownLatch(10);

	public CyclicBarrier getCb() {
		return this.cyclicBarrier;
	}
	
	public CountDownLatch getCdl() {
		return this.countDownLatch;
	}
	
	/**
	 * 将统计后的结果汇总
	 */
	@Override
	public void run() {
		double sum = 0d;
		for(Map.Entry<String, Double> entry:sheels.entrySet()) {
			sum+=entry.getValue();
		}
		System.out.println("已阻塞线程数："+cyclicBarrier.getNumberWaiting());
		System.out.println("result:"+sum+"  "+Thread.currentThread().getName());
	}
	
	/**
	 * @author renhuibo  2019年9月26日
	 * @Description 统计每个用户的账户信息
	 */
	public void countSheel() {
		for (int i = 0; i < 10; i++) {
			//每个线程都是新的对象，不会出现并发问题
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						//计算用户的操作省略，直接将结果存入map
						Thread.sleep(3000);
						sheels.put(Thread.currentThread().getName(), 10000.00d);
						System.out.println("sheel计算结果完成！-"+Thread.currentThread().getName());
						cyclicBarrier.await();
						System.out.println("屏障解除："+Thread.currentThread().getName());
						countDownLatch.countDown();
					} catch (InterruptedException | BrokenBarrierException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
	
	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
		CyclicBarrierService service = new CyclicBarrierService();
		service.countSheel();
		CountDownLatch countDownLatch = service.getCdl();
		countDownLatch.await();
		CyclicBarrier barrier = service.getCb();
		System.out.println(Thread.currentThread().getName()+"-阻塞线程数:"+barrier.getNumberWaiting());
		System.out.println("Process Over!");
	}
	
}
