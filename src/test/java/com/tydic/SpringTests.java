package com.tydic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 如果要用spring框架的相关特性，继续该测试基类 该类加载了spring资源
 * */
public abstract class SpringTests extends BaseTests {

	private static final Log log = LogFactory.getLog(SpringTests.class);

	private static ApplicationContext context;

	static {
		log.debug("初始化Spring信息.............");
		context = new ClassPathXmlApplicationContext(
				"classpath:config/spring-*.xml");
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/** 返回spring上下文对象 */
	protected ApplicationContext getContext() {
		return context;
	}
	
}
