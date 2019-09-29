package com.rhb.juc.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName:  SimpleExecutor   
 * @Description: Executor框架   ： 重点还是相关Executor的特性，以及源码的解析
 * @author: renhuibo
 * @date:   2019年9月27日 下午3:32:16
 * 
 * 
 * 								Executor-I
 * 									|
 *		    				ExecutorService-I
 *				|										|
 * 	AbstractExecutorService-C    						|
 * 				|								ScheduledExecutorService-I
 * 		ThreadPoolExecutor-C							|
 * 				|										|
 * 					   ScheduledThreadPoolExecutor-C
 * 
 * 
 * ThreadPoolExecutor:
 * 		FixedThreadPoolExecutor		: 固定大小
 * 		SingleThreadPoolExecutor	: 单个线程
 * 		CacheThreadPoolExecutor		: 适用于多个小的Task

	以上三种不同池的设置，源码解析：
 		Fixed-> new ThreadPoolExecutor(nThreads, nThreads,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());
                                      
       	Single->new ThreadPoolExecutor(1, 1,
                                    0L, TimeUnit.MILLISECONDS,
                                    new LinkedBlockingQueue<Runnable>())
                                    
        Cache-> new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                      60L, TimeUnit.SECONDS,
                                      new SynchronousQueue<Runnable>())
   
   	ThreadPool两个核心概念：
   		corePoolSize: 核心线程数
   		maximumPoolSize: 最大线程数
   		keepAliveTime: 当线程数大于核心线程数时，这是多余的空闲线程在终止新任务之前等待新任务的最长时间。
   	
   	> corePoolSize = maximumPoolSize： 固定大小的线程池
   	> 将 maximumPoolSize 设置为基本的无界值（如 Integer.MAX_VALUE），则允许池适应任意数量的并发任务
   	    为什么说CacheThreadPool适合用于多个小任务： 首先是maximumPoolSize设置Integer.MAX_VALUE，其次设置的keepAliveTime=60s。
   	
 * ScheduledThreadPoolExecutor: 周期性执行Task
 * SingleScheduledThreadPoolExecutor: 单个线程周期执行Task
 * 
 * 异步计算：
 * Future-I ：
 * FutureTask-C ：
 * 		get(): 阻塞方法，等待线程执行结束才执行。（源码中一共有7种状态，在get方法中调用awaitDone()）
 * 		isDone() : 是否已经运行（源码： state != NEW）。
 * 		isCancelled() : 是否取消（源码： state > CANCELLED , 换句话说，任务取消，只有两种途径：1.正在中断INTERRUPTING。2.已被中断INTERRUPTED）
 * 		&个人总结： 由于get()方法是阻塞式的，所以一个get()方法足以应对所有场景
 */
public class SimpleExecutor {

	private static CountDownLatch countDownLatch = new CountDownLatch(2);

	class SimpleRunnable implements Runnable{
		@Override
		public void run() {
			String threadName = Thread.currentThread().getName();
			System.out.println(threadName+": 运行！");
			countDownLatch.countDown();
		}
	}

	class SimpleCallable implements Callable<String>{
		@Override
		public String call() throws Exception {
			String threadName = Thread.currentThread().getName();
			TimeUnit.SECONDS.sleep(5);
			System.out.println(threadName+": 运行！");
			countDownLatch.countDown();
			return threadName+": 执行结果！";
		}
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		SimpleExecutor se = new SimpleExecutor();
		
		ExecutorService exe = Executors.newFixedThreadPool(2);
		
		exe.execute(se.new SimpleRunnable());
		
		FutureTask<String> ft = new FutureTask<>(se.new SimpleCallable());
		exe.execute(ft);
		//阻塞式方法
		System.out.println(ft.get());
		
		SimpleExecutor.countDownLatch.await();
		exe.shutdown();
		
		System.out.println("OVER!");
	}
	
	/**
	 * @author: renhuibo
	 * @date:   2019年9月29日 上午10:15:28   
	 * @Description: (描述类的作用)
	 *		
	 */
	public void ThreadPoolExecutorMake() {
		
	}
	
}
