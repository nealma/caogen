package com.caogen.core.web;

/**
 *  输出消息结果
 *
 * Created by neal on 9/21/16.
 */
public class MsgOut {

    private Integer code;
    private String msg;
    private String type;
    private String title;
    private String error;
    private Object data;

    public MsgOut(){

    }

    public MsgOut(Integer code, String msg, String type, String title, Object data) {
        this.code = code;
        this.msg = msg;
        this.type = type;
        this.title = title;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    /**
     *
     * 枚举提示类型(info、waring、error、success)
     */
    private enum MsgType {

        SUCCESS(0, "操作成功啦", "SUCCESS", "成功"), INFO(1, "来信息啦", "INFO", "信息"), WARNING(2, "警告来了", "WARNING", "警告"), ERROR(3, "操作失败啦", "ERROR", "错误");

        private int code;
        private String type;
        private String title;
        private String msg;


        MsgType(int code, String msg, String type, String title) {
            this.code = code;
            this.msg = msg;
            this.type = type;
            this.title = title;
        }

        public int getCode() {
            return code;
        }

        public String getTitle() {
            return title;
        }

        public String getType() {
            return type;
        }
    }
    public static MsgOut success(){
        return getInstance(MsgType.SUCCESS.code, MsgType.SUCCESS.msg);
    }
    public static MsgOut success(String msg){
        return getInstance(MsgType.SUCCESS.code, msg);
    }

    public static MsgOut success(Object data){
        return getInstance(MsgType.SUCCESS.code, MsgType.SUCCESS.msg, data);
    }

    public static MsgOut success(String msg, Object data){
        return getInstance(MsgType.SUCCESS.code, msg, data);
    }

    public static MsgOut info(String msg){
        return getInstance(MsgType.INFO.code, msg);
    }

    public static MsgOut info(String msg, Object data){
        return getInstance(MsgType.INFO.code, msg, data);
    }

    public static MsgOut warning(String msg){
        return getInstance(MsgType.WARNING.code, msg);
    }

    public static MsgOut warning(String msg, Object data){
        return getInstance(MsgType.WARNING.code, msg, data);
    }

    public static MsgOut error(String msg){
        return getInstance(MsgType.ERROR.code, msg);
    }

    public static MsgOut error(String msg, Object data){
        return getInstance(MsgType.ERROR.code, msg, data);
    }
    public static MsgOut error(){
        return getInstance(MsgType.ERROR.code, MsgType.ERROR.msg);
    }
    public static MsgOut error(Object data){
        return getInstance(MsgType.ERROR.code, MsgType.ERROR.msg, data);
    }
    public static MsgOut getInstance(int code, String msg){
        return getInstance(code, msg, null);
    }
    private static MsgOut getInstance(int code, String msg, Object data){
        switch (code){
            case 0:
                return new MsgOut(MsgType.SUCCESS.code, msg, MsgType.SUCCESS.type, MsgType.SUCCESS.title, data);
            case 1:
                return new MsgOut(MsgType.INFO.code, msg, MsgType.INFO.type, MsgType.INFO.title, data);
            case 2:
                return new MsgOut(MsgType.WARNING.code, msg, MsgType.WARNING.type, MsgType.WARNING.title, data);
            case 3:
                return new MsgOut(MsgType.ERROR.code, msg, MsgType.ERROR.type, MsgType.ERROR.title, data);
            default:
                return new MsgOut(code, msg, MsgType.INFO.type, MsgType.INFO.title, data);
        }
    }
}
