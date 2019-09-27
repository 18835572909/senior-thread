package com.rhb.common;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class CurrentToolCountDownLatch {
	
	
	
	
	
	
	public class Callable1<V> implements Callable<V>{

		private CountDownLatch countDownLatch;
		
		public Callable1(CountDownLatch countDownLatch) {
			this.countDownLatch = countDownLatch;
		}
		
		@Override
		public V call() throws Exception {
			return null;
		}
	}
	
	public class Callable2<V> implements Callable<V>{

		private CountDownLatch countDownLatch;
		
		public Callable2(CountDownLatch countDownLatch) {
			this.countDownLatch = countDownLatch;
		}
		
		@Override
		public V call() throws Exception {
			try {
				Thread.sleep(3000);
			}finally {
				
			}
			return null;
		}
	}
	
	
}
