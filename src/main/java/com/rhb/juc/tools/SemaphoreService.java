package com.rhb.juc.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @ClassName:  SemaphoreService   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: renhuibo
 * @date:   2019年9月27日 上午11:08:55
 * 
 * Semaphore： 
 * 		当信号量是1的时候，和lock+unlock是一样的。
 * 		当信号量大于1的时候，唯一不同的是： 信号量可以限定线程的个数。
 * 
 * 重点还是： new Semaphore(parties)	
 * 
 * 业务场景：
 * 		银行只有两个窗口，最大限制的处理客户的问题。
 * 
 * 思路： 使用Semaphore来限定线程运行的个数，满足两个窗口的限制。并发处理客户问题
 */
public class SemaphoreService implements Runnable{
	
	//客户数：
	private int clientCount = 100;
	
	//信号量：2，充当两个窗口的屏障
	private final Semaphore semaphore = new Semaphore(2);
	
	//两个线程的线程池： 最多两个人办理业务
	private final ExecutorService executor = Executors.newFixedThreadPool(2);
	
	private CountDownLatch countDownLatch = new CountDownLatch(clientCount);

	public CountDownLatch getCDL() {
		return this.countDownLatch;
	}
	
	public ExecutorService getExePool() {
		return this.executor;
	}
	
	public static String now() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}
	
	@Override
	public void run() {
		try {
			semaphore.acquire(1);
			//2s一个人业务
			Thread.sleep(2000);
			System.out.println(now()+":"+Thread.currentThread().getName()+"，处理完成！");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			semaphore.release(1);
			countDownLatch.countDown();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		SemaphoreService service = new SemaphoreService();
		ExecutorService exe = service.getExePool();
		CountDownLatch countDownLatch = service.getCDL();
		for (int i = 0; i < 100; i++) {
			exe.execute(service);
		}
		countDownLatch.await();
		exe.shutdown();
		System.out.println(now()+":处理完成！");
	}
	
}
