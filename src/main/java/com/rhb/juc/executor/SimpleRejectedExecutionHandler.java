package com.rhb.juc.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName:  SimpleRejectedExecutionHandler   
 * @Description: 线程池4种拒绝策略   
 * @author: renhuibo
 * @date:   2019年9月29日 下午3:59:17
 * 
 * 4中场景：
 * a. threadCount < coreThreadPool : 创建线程处理任务。
 * b. threadCount > coreThreadPool && 缓存队列（workQueue）未满  : 任务放入缓存队列。
 * c. threadCount > coreThreadPool && 缓存队列（workQueue）已满   && threadCount < maximumThreadPool : 创建线程处理任务。
 * e. threadCount > coreThreadPool && 缓存队列（workQueue）已满   && threadCount = maximumThreadPool : 交由Handler处理。  
 * 
 * JDK原生策略：
 * 	ThreadPoolExecutor.AbortPolicy ： 抛出异常RejectedExecutionException
 * 	ThreadPoolExecutor.CallerRunsPolicy ： 在handler中运行拦截到的任务
 * 	ThreadPoolExecutor.DiscardOldestPolicy ： 杀掉存活时间最长的线程，运行当前线程
 * 	ThreadPoolExecutor.DiscardPolicy ： 不做任何操作
 */
public class SimpleRejectedExecutionHandler implements RejectedExecutionHandler{
	
	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		System.out.println("我自己的拒绝策略！");
	}

	/**
	 * @author: renhuibo
	 * @date:   2019年9月29日 下午4:46:23   
	 * @Description: (描述类的作用)
	 *		自定义策略
	 */
	public ThreadPoolExecutor newThreadPoolExecutor(int strategy) {
		int corePoolSize = 10; 			//核心线程池大小
		int maximumPoolSize = 15;		//线程池中最大线程个数
		long keepAliveTime = 5;			//线程池满后，缓存队列存放线程时间
		TimeUnit unit = TimeUnit.SECONDS;
		BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(3);			//缓存队列
		RejectedExecutionHandler handler = null;									//拒绝策略
		switch (strategy) {
		case 0:
			handler = new SimpleRejectedExecutionHandler();
			break;
		case 1:
			handler = new ThreadPoolExecutor.AbortPolicy();
			break;
		case 2:
			handler = new ThreadPoolExecutor.CallerRunsPolicy();
			break;
		case 3:
			handler = new ThreadPoolExecutor.DiscardOldestPolicy();
			break;
		case 4:
			handler = new ThreadPoolExecutor.DiscardPolicy();
			break;
		default:
			break;
		}
		return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
	}
	
	class SimpleThread implements Runnable{
		@Override
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				System.out.println(Thread.currentThread().getName()+":  Running!");
			}
		}
	}
	
	/**
	 * @author: renhuibo
	 * @date:   2019年9月29日 下午4:30:24   
	 * @Description: (描述类的作用)
	 *		场景a
	 */
	public void scene1(SimpleRejectedExecutionHandler hander,SimpleThread thread) {
		ThreadPoolExecutor threadPoolExecutor = hander.newThreadPoolExecutor(0);
		for(int i=0 ; i<10 ;i++) {
			threadPoolExecutor.submit(thread);
		}
		threadPoolExecutor.shutdown();
	}
	
	/**
	 * @author: renhuibo
	 * @date:   2019年9月29日 下午4:30:24   
	 * @Description: (描述类的作用)
	 *		场景b
	 */
	public void scene2(SimpleRejectedExecutionHandler hander,SimpleThread thread) {
		ThreadPoolExecutor threadPoolExecutor = hander.newThreadPoolExecutor(0);
		for(int i=0 ; i<13 ;i++) {
			threadPoolExecutor.submit(thread);
		}
		BlockingQueue<Runnable> queue = threadPoolExecutor.getQueue();
		System.out.println("当前缓存队列大小："+queue.size());
		threadPoolExecutor.shutdown();
	}
	
	
	/**
	 * @author: renhuibo
	 * @date:   2019年9月29日 下午4:30:24   
	 * @Description: (描述类的作用)
	 *		场景c
	 */
	public void scene3(SimpleRejectedExecutionHandler hander,SimpleThread thread) {
		ThreadPoolExecutor threadPoolExecutor = hander.newThreadPoolExecutor(0);
		for(int i=0 ; i<14 ;i++) {
			threadPoolExecutor.submit(thread);
		}
		BlockingQueue<Runnable> queue = threadPoolExecutor.getQueue();
		System.out.println("当前缓存队列大小："+queue.size());
		threadPoolExecutor.shutdown();
	}
	
	
	/**
	 * @author: renhuibo
	 * @date:   2019年9月29日 下午4:30:24   
	 * @Description: (描述类的作用)
	 *		场景d
	 */
	public void scene4(SimpleRejectedExecutionHandler hander,SimpleThread thread) {
		ThreadPoolExecutor threadPoolExecutor = hander.newThreadPoolExecutor(4);
		for(int i=0 ; i<20 ;i++) {
			threadPoolExecutor.submit(thread);
		}
		BlockingQueue<Runnable> queue = threadPoolExecutor.getQueue();
		System.out.println("当前缓存队列大小："+queue.size());
		threadPoolExecutor.shutdown();
	}
	
	public static void main(String[] args) {
		SimpleRejectedExecutionHandler executionHandler = new SimpleRejectedExecutionHandler();
		SimpleThread thread = executionHandler.new SimpleThread();
		executionHandler.scene4(executionHandler, thread);
	}
	
}
