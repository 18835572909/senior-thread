package com.rhb.common;

import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CyclicBarrierService implements Runnable{

	private ConcurrentHashMap<String, Double> map = new ConcurrentHashMap<>();
	
	private CyclicBarrier barrier = new CyclicBarrier(4, this);
	
	private Executor executor = Executors.newFixedThreadPool(4);
	
	@Override
	public void run() {
		double sum = 0;
		for(Map.Entry<String, Double> entry : map.entrySet()) {
			sum += entry.getValue();
		}
		map.put("SUM", sum);
		System.out.println(Thread.currentThread().getName()+"-SUM:"+sum);
	}
	
	public void count() {
		for (int i = 0; i < 4; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					map.put(Thread.currentThread().getName(), 10.5d);
					try {
						System.out.println(Thread.currentThread().getName()+"->");
						barrier.await();
						System.out.println(Thread.currentThread().getName()+"<-");
					} catch (InterruptedException | BrokenBarrierException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
	
	public static void main(String[] args) {
		CyclicBarrierService service = new CyclicBarrierService();
		service.count();
	}
	
}
