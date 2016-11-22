package com.tydic.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 内存配置信息刷新监听器,需要根据 配置文件更新而动态刷新内存值的需要继承该类
 * @author leo
 *
 */
public abstract class ConfigListener {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private static final List<ConfigListener> objs = new ArrayList<ConfigListener>();
	
	public ConfigListener(){
		objs.add(this);
	}
	
	/**要刷新的监听器列表*/
	public static List<ConfigListener> getObjs() {
		return objs;
	}

	/**
	 * 动态刷新配置文件后需要刷新的内存配置 ,刷新加载的信息通常是静态全局的
	 */
	public abstract void refresh();
}
