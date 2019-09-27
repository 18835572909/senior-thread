package com.rhb.common;

public class ProtectedClass {
	
	protected void run() {
		System.out.println("run...");
	}
	
	public static void main(String[] args) {
		ProtectedClass class1 = new ProtectedClass();
		class1.run();
	}
	
}
