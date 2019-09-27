package com.rhb.test;

import com.rhb.common.ProtectedClass;

public class ProtectedTest extends ProtectedClass{
	
	public void run() {
		super.run();
	}
	
	public static void main(String[] args) {
		ProtectedTest class1 = new ProtectedTest();
		class1.run();
	}
}
