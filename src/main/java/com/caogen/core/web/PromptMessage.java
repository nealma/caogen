package com.caogen.core.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.caogen.core.cache.AppCache;
import com.caogen.core.exception.AppException;

import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 提示信息封装类 根据prompt.properties中配置的提示信息组装返回页面提示，用于后台处理完成后发送至前台进行提示的场景
 * 提供四种常用提示框创建静态方法，如果需要自定义提示框中的title、text、code等需要自己创建对象：new PromptMesage();
 * 
 */
public class PromptMessage {

	/**
	 * 显示错误码临时配置后续会将配置移到配置管理的系统参数管理中去
	 */
	private static String SHOW_ERROR_CODE = "true";

	/**
	 * 
	 * 显示错误Message临时配置后续会将配置移到配置管理的系统参数管理中去
	 */
	private static String SHOW_ERROR_MESSAGE = "true";

	/**
	 * info提示框的标题
	 */
	private static String TITLE_INFO = "提示";

	/**
	 * success提示框的标题
	 */
	private static String TITLE_SUCCESS = "成功";

	/**
	 * warn提示框的标题
	 */
	private static String TITLE_WARN = "警告";

	/**
	 * error提示框的标题
	 */
	private static String TITLE_ERROR = "错误";

	/**
	 * 提示标题
	 */
	private String title;

	/**
	 * 提示信息
	 */
	private String text;

	/**
	 * 提示信息code
	 */
	private String code;

	/**
	 * 返回的结果记录 如返回一个list
	 */
	private Object result;

	/**
	 * 错误信息
	 */
	private Map<String, String> error;

	public PromptMessage() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化提示消息
	 * 
	 * @param title
	 *            提示标题
	 * @param code
	 *            提示信息code
	 * @param text
	 *            提示信息
	 * @param type
	 *            提示類型 INFO, WARNING, ERROR, SUCCESS
	 */
	public PromptMessage(String title, String code, String text, String type) {
		this.text = text;
		this.title = title;
		this.type = type;
		this.code = code;
	}

	/**
	 * 将消息提示对象输出为Json串，并设置了常用类型的默认值
	 * <ul>
	 * <li>默认日期格式化格式yyyy-MM-dd HH:mm:ss
	 * <li>数字类型null时默认为数字0
	 * <li>字符串类型null默认字符串""
	 * <li>Map类型/某属性值默认未null
	 * </ul>
	 * <p>
	 * 当某些特殊情况下，设置默认值会导致业务数据变化，导致影响业务执行。 example:
	 * <p>
	 * 
	 * @see {@link SerializerFeature}
	 * @see {@link JSON#toJSONStringWithDateFormat(Object, String, SerializerFeature...)}
	 * @return 格式化后的JSON字符串
	 */
	public String toJSonString() {
		SerializerFeature[] serializerFeatureNull = new SerializerFeature[] { SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty };
		return JSON.toJSONStringWithDateFormat(this, "yyyy-MM-dd HH:mm:ss", serializerFeatureNull);
	}

	/**
	 * 创建成功提示消息
	 * 
	 * @param code
	 *            提示代码
	 * @param args
	 *            提示信息参数 用于替换信息中的变量
	 * @return 信息提示对象
	 */
	public static PromptMessage createSuccessPrompt(String code, Object... args) {
		PromptMessage promptMessage = new PromptMessage();
		promptMessage.setTitle(TITLE_SUCCESS);
		promptMessage.setCode(code);
		promptMessage.setType(PromptType.SUCCESS.getType());
		promptMessage.setText(AppCache.promptInfoConfig.get(code));
		if (null != promptMessage.getText() && !"".equalsIgnoreCase(promptMessage.getText()) && args != null && args.length != 0)
			promptMessage.setText(MessageFormat.format(promptMessage.getText(), args));
		return promptMessage;
	}

	/**
	 * 创建錯誤提示消息
	 * 
	 * @see {@link AppException}
	 * @param code
	 *            提示代码
	 * @param error
	 *            异常对象
	 * @param args
	 *            提示信息参数 用于替换信息中的变量
	 * @return 信息提示对象
	 */
	public static PromptMessage createErrorPrompt(String code, AppException error, Object... args) {
		PromptMessage promptMessage = new PromptMessage();
		promptMessage.setTitle(TITLE_ERROR);
		promptMessage.setCode(code);
		promptMessage.setType(PromptType.ERROR.getType());
		promptMessage.setText(AppCache.promptInfoConfig.get(code));
		Map<String, String> errorMap = new ConcurrentHashMap();
		errorMap.put("code", error.getCode());
		errorMap.put("message", error.getMessage());
		promptMessage.setError(errorMap);
		if (null != promptMessage.getText() && !"".equalsIgnoreCase(promptMessage.getText()) && args != null && args.length != 0)
			promptMessage.setText(MessageFormat.format(promptMessage.getText(), args));
		return promptMessage;
	}

	/**
	 * 创建錯誤提示消息
	 * 
	 * @param code
	 *            提示代码
	 * @param args
	 *            提示信息参数 用于替换信息中的变量
	 * @return 信息提示对象
	 */
	public static PromptMessage createErrorPrompt(String code, Object... args) {
		PromptMessage promptMessage = new PromptMessage();
		promptMessage.setTitle(TITLE_ERROR);
		promptMessage.setCode(code);
		promptMessage.setType(PromptType.ERROR.getType());
		promptMessage.setText(AppCache.promptInfoConfig.get(code));
		if (null != promptMessage.getText() && !"".equalsIgnoreCase(promptMessage.getText()) && args != null && args.length != 0)
			promptMessage.setText(MessageFormat.format(promptMessage.getText(), args));
		return promptMessage;
	}

	/**
	 * 创建普通提示消息
	 * 
	 * @param code
	 *            提示代码
	 * @param args
	 *            提示信息参数 用于替换信息中的变量
	 * @return 信息提示对象
	 */
	public static PromptMessage createInfoPrompt(String code, Object... args) {
		PromptMessage promptMessage = new PromptMessage();
		promptMessage.setTitle(TITLE_INFO);
		promptMessage.setCode(code);
		promptMessage.setType(PromptType.INFO.getType());
		promptMessage.setText(AppCache.promptInfoConfig.get(code));
		if (null != promptMessage.getText() && !"".equalsIgnoreCase(promptMessage.getText()) && args != null && args.length != 0)
			promptMessage.setText(MessageFormat.format(promptMessage.getText(), args));
		return promptMessage;
	}

	/**
	 * 创建警告提示消息
	 * 
	 * @param code
	 *            提示代码
	 * @param args
	 *            提示信息参数 用于替换信息中的变量
	 * @return 信息提示对象
	 */
	public static PromptMessage createWarnPrompt(String code, Object... args) {
		PromptMessage promptMessage = new PromptMessage();
		promptMessage.setTitle(TITLE_WARN);
		promptMessage.setCode(code);
		promptMessage.setType(PromptType.WARNING.getType());
		promptMessage.setText(AppCache.promptInfoConfig.get(code));
		if (null != promptMessage.getText() && !"".equalsIgnoreCase(promptMessage.getText()) && args != null && args.length != 0)
			promptMessage.setText(MessageFormat.format(promptMessage.getText(), args));
		return promptMessage;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 提示類型 INFO, WARNING, ERROR, SUCCESS
	 */
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Map<String, String> getError() {
		return error;
	}

	public void setError(Map<String, String> error) {
		this.error = error;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	/**
	 * 
	 * 枚举提示类型(info、waring、error、success)
	 */
	private enum PromptType {
		INFO("INFO"), WARNING("WARNING"), ERROR("ERROR"), SUCCESS("SUCCESS");
		private String type;

		PromptType(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}
	}
}
