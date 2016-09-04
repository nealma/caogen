package com.caogen.core.cache;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 应用缓存类，使用静态对象方式对部分常用参数进行内存缓存。
 * 
 */
@Repository
public class AppCache {
	/**
	 * 初始化ErrorCode配置
	 */
	public static final Map<String, String> errorInfoConfig = new ConcurrentHashMap();

	/**
	 * 初始化prompt配置
	 */
	public static final Map<String, String> promptInfoConfig =  new ConcurrentHashMap();


	/**
	 * 加载“错误消息”“提示消息”
	 * 
	 * @see #loadErrorConfig()
	 * @see #loadPromptConfig()
	 */
	@PostConstruct
	public void init() {
		loadErrorConfig();
		loadPromptConfig();
	}
	
	/**
	 * 清理代码缓存
	 */
	public void clear(){
		errorInfoConfig.clear();
		promptInfoConfig.clear();
	}
	
	/**
	 * 重新加载代码缓存
	 */
	public void reLoad(){
		clear();
		init();
	}

	/**
	 * 从数据库加载“错误消息”，初始化到{@link #errorInfoConfig}
	 */
	public void loadErrorConfig(){
	}

	/**
	 * 从数据库加载“提示消息”，初始化到{@link #promptInfoConfig}
	 */
	public void loadPromptConfig(){
		promptInfoConfig.put("0000", "{0}");
		promptInfoConfig.put("0001", "保存{0}成功!");
		promptInfoConfig.put("0002", "修改{0}成功!");
		promptInfoConfig.put("0003", "删除{0}成功!");
		promptInfoConfig.put("9001", "保存{0}失败!");
	}
}
