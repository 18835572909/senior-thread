package com.rhb.juc.tools;

import java.util.concurrent.Semaphore;

/**
 * @ClassName:  SimpleSemaphore   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: renhuibo
 * @date:   2019年9月27日 上午9:50:59
 * 
 * 
 * Semaphore : 线程信号量
 * 
 */
public class SimpleSemaphore implements Runnable{

	//定义信号量
	private final Semaphore semaphore = new Semaphore(1);

	@Override
	public void run() {
		try {
			System.out.println("当前信号量："+getAvailablePermits());
			System.out.println("有线程等待："+hasWaitThreads());
			semaphore.acquire();
			System.out.println("Semaphore-Run-start:"+Thread.currentThread().getName());
			Thread.sleep(1000);
			System.out.println("Semaphore-Run-end:"+Thread.currentThread().getName());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			semaphore.release();
		}
	}
	
	//信号量当前许可数
	public int getAvailablePermits() {
		return semaphore.availablePermits();
	}
	
	//检测是否有线程等待
	public boolean hasWaitThreads(){
		if(semaphore.hasQueuedThreads()) {
			System.out.println("当前等待线程个数："+semaphore.getQueueLength());
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		SimpleSemaphore semaphore = new SimpleSemaphore();
		for (int i = 0; i < 10; i++) {
			new Thread(semaphore).start();
		}
	}
	
}
