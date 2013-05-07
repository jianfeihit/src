/**
 *  Copyright (c)  2011-2020 Panguso, Inc.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of Panguso, 
 *  Inc. ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into with Panguso.
 */
package com.panguso.op.resource.cp.core.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author jianfeihit
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface XMLSchema {
	/**
	 * 本属性是否需要添加到xml中
	 * @return
	 * @author jianfeihit
	 * @date 2013-4-14
	 */
	boolean silent() default false;
	
	/**
	 * 名字的别名,若为空则为类的simpleName
	 * @return
	 * @author jianfeihit
	 * @date 2013-4-14
	 */
	String nameAlias() default "";
	
	/**
	 * 当序列化Map时，key的别名
	 * @return
	 * @author jianfeihit
	 * @date 2013-4-14
	 */
	String keyAlias() default "";
	
	/**
	 * 当序列化Map时，value的别名
	 * @return
	 * @author jianfeihit
	 * @date 2013-4-14
	 */
	String valueAlias() default "";
	
	/**
	 * 是否是文本
	 */
	boolean text() default false;
}
