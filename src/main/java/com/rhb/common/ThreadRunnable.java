package com.rhb.common;

public class ThreadRunnable implements Runnable {
	
	private int count = 100;
	
	public void run() {
		while(count>0) {
			System.out.println("count:"+count--);
		}
	}

}
