package com.rhb.juc.tools;

import java.util.concurrent.Exchanger;

/**
 * @ClassName:  ExchangerService   
 * @Description: 高级线程工具类之：Exchange   
 * @author: renhuibo
 * @date:   2019年9月27日 下午3:58:31
 * 
 * 
 * 当线程调用Exchange.exchange()方法时，当前线程阻塞，知道其他线程同样调用exchange()方法，完成工作线程中变量的交换
 * 
 * 业务场景：
 * 		工厂生产物品，消费者消费物品。基本条件： 多线程生产，多线程消费，保证数据正确，保证消费之前必须生产完成！
 * 
 * 思路：
 * 		1. Exchange可以完成生成后消费的置换。
 * 		2. 由于Exchange
 */
public class ExchangerService {

	//生成者：  数量小于消费者
	class Producer implements Runnable{
		
		private Exchanger<Double> exchanger;
		
		public Producer(Exchanger<Double> exchanger) {
			this.exchanger = exchanger;
		}
			
		@Override
		public void run() {
			
		}
	}
	
	//消费者
	class Consumer implements Runnable{
		
		private Exchanger<Double> exchanger;
		
		public Consumer(Exchanger<Double> exchanger) {
			this.exchanger = exchanger;
		}
		
		@Override
		public void run() {
			
		}
	}
	
	
	
}
