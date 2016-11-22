package com.tydic.filter.ip;

import java.util.ArrayList;
import java.util.List;

import com.tydic.util.ConfigListener;
import com.tydic.util.ConfigUtil;
import com.tydic.util.NullUtil;

/**
 * ip 配置信息监听器
 * @author leo
 *
 */
public class IPConfigListener extends ConfigListener {
	
	private static final String BLACK_IPS_KEY = "ip.black.list";
	private static final String WHITE_IPS_KEY = "ip.white.list";
	
	/**白名单ips*/
	private final static List<String> whiteIPS = new ArrayList<String>();
	/**黑名单 ips*/
	private final static List<String> blackIPS = new ArrayList<String>();
	
	@Override
	public void refresh() {
		log.debug("@@ip config load info...");
		whiteIPS.clear();
		blackIPS.clear();
		String blackIPSStr = ConfigUtil.getProperty(BLACK_IPS_KEY,"");
		for(String ip:blackIPSStr.split(",")){
			if(!NullUtil.isNull(ip)){
				blackIPS.add(ip);
			}
		}
		String whileIPSStr = ConfigUtil.getProperty(WHITE_IPS_KEY,"");
		for(String ip:whileIPSStr.split(",")){
			if(!NullUtil.isNull(ip)){
				whiteIPS.add(ip);
			}
		}
	}

	public static List<String> getWhiteips() {
		return whiteIPS;
	}

	public static List<String> getBlackips() {
		return blackIPS;
	}

}
