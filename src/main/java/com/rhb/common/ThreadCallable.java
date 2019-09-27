package com.rhb.common;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName:  ThreadCallable   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: renhuibo
 * @date:   2019年9月26日 下午3:54:22
 * 
 * 
 * volatitle:
 * 	使用场景： 多个变量或者某个变量，当前值与修改后没有约束
 * 	限制条件： 1. 自身不能依赖自身。2.其他变量不能依赖自身。
 */
public class ThreadCallable implements Callable<Integer> {
	
	private int ticket = 100;
	
	private ReentrantLock lock = new ReentrantLock();
	
	public Integer call() throws Exception {
		try {
			lock.lock();
			for (int i = 0; i < 100; i++) {
	            if (this.ticket > 0) {
	            	System.out.println("ticket=" + this.ticket--);
	            }
		    }
		}finally {
			lock.unlock();
		}
		
		return ticket;
	}
	
}
