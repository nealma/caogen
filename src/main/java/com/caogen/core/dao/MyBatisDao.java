package com.caogen.core.dao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识为@MyBatisDao的dao类 
 * 方便{@link org.mybatis.spring.mapper.MapperScannerConfigurer}进行扫描并自动生成相应的spring bean
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyBatisDao {

}
