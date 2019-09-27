package com.rhb.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @ClassName:  SceneCommon   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: renhuibo
 * @date:   2019年9月26日 下午3:05:58
 * 
 * 1. 常见线程安全集合。
 * 
 */
public class SceneCommon {

	/**
	 * @Description: 所有集合操作都在synchronize-同步代码块中 
	 * @author: renhuibo
	 * @date:   2019年9月26日 下午3:12:23
	 */
	public <T> Collection<T> getCollection1(){
		List<T> list = new ArrayList<T>();
		return Collections.synchronizedList(list);
	}
	
	/**
	 * @Description: 所有操作都使用可重入锁ReentrantLock加锁   
	 * @author: renhuibo
	 * @date:   2019年9月26日 下午3:16:44
	 */
	public <T> Collection<T> getCollection2(){
		return new CopyOnWriteArrayList<T>();
	}
	
	/**
	 * @Description: 所有操作都是synchronize-同步方法 ，初始容量10
	 * @author: renhuibo
	 * @date:   2019年9月26日 下午3:17:26
	 */
	public <T> Collection<T> getCollection3(){
		return new Vector<T>();
	}
	
	
}
