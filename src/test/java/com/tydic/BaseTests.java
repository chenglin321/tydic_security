package com.tydic;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import com.tydic.util.ConfigUtil;

/**
 * 测试基类，该类只做了日志组件的加载
 * */
public abstract class BaseTests extends TestCase {
	
	protected Log log = LogFactory.getLog(getClass());
	
	protected static String baseConfPath = "file:./config/xxx/";
	
	/** 初始化日志组件 */
	static {
		System.out.println("信息:初始化Log4j信息.............");
		InputStream in = null;
		try {
			in = BaseTests.class.getResourceAsStream("/log4j.properties");
			Properties props = new Properties();
			props.load(in);
			PropertyConfigurator.configure(props);
			
			ConfigUtil.init(baseConfPath+"sysconf.properties", 10*1000);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		System.out.println("********* "+this.getClass()+" *********");
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
