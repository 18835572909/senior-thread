package com.rhb.juc.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName:  SimpleCompletionService   
 * @Description:  
 * @author: renhuibo
 * @date:   2019年9月29日 上午11:48:05
 * 
 * 
 * JDKAPI: 
 * 		CompletionService: 将生产新的异步任务与使用已完成任务的结果分离开来的服务。(Future是对任务本身进行分离，CompletionService是将执行流程进行分离)
 * 
 * 业务场景：
 * 		多个计算任务同时提交，需要将最快计算出来的结果显示。 （new CompletionService().take().get()）
 * 
 * 通俗解释：
 * 			与ThreadPoolExecutor相比，将最后获取FuturnTask的结果，从用户使用具体的Task.get(),转为
 * 		所有Task【completionService.take()】交给CompletionService处理，由CompletionService已处
 * 		理时间的快慢顺序返回。
 * 
 */
public class SimpleCompletionService {
	
	class FirstFuture implements Callable<Double>{
		@Override
		public Double call() throws Exception {
			TimeUnit.SECONDS.sleep(50);
			System.out.println("FirstFuture: "+Thread.currentThread().getName());
			return 56.5d;
		}
	}
	
	class TwoFuture implements Callable<Double>{
		@Override
		public Double call() throws Exception {
			TimeUnit.SECONDS.sleep(3);
			System.out.println("TwoFuture: "+Thread.currentThread().getName());
			return 56.5d;
		}
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		SimpleCompletionService scs = new SimpleCompletionService();
		scs.completionServiceRun(scs);
	}
	
	public void threadPoolRun(SimpleCompletionService scs) throws InterruptedException, ExecutionException {
		ExecutorService es = Executors.newFixedThreadPool(2);
		Future<Double> submit = es.submit(scs.new FirstFuture());
		Future<Double> submit2 = es.submit(scs.new TwoFuture());
		if(!submit.isDone()) {
			submit.cancel(true);
		}
		System.out.println("submit:"+submit.get());
		System.out.println("submit2:"+submit2.get());
		
		es.shutdown();
	}
	
	public void completionServiceRun(SimpleCompletionService scs) throws InterruptedException, ExecutionException {
		CompletionService<Double> cs = new ExecutorCompletionService<>(Executors.newFixedThreadPool(2));
		cs.submit(scs.new FirstFuture());
		cs.submit(scs.new TwoFuture());
		
		for(int i=0 ; i<2;i++) {
			Double result = cs.take().get();
			System.out.println(result);
		}
	}
}
