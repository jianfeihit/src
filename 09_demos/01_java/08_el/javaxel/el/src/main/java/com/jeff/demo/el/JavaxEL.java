package com.jeff.demo.el;

import javax.el.ELManager;
import javax.el.ELResolver;
import javax.el.StandardELContext;

/**
 * 使用标准的Javax.el包中的相关方法
 * @author jianfeihit
 *
 */
public class JavaxEL {
	@SuppressWarnings("rawtypes")
    public static void main(String[] args) {
		ELManager manager = new ELManager();
		StandardELContext context = manager.getELContext();
		ELResolver resolver = context.getELResolver();
		Person pet = new Person();
		pet.setName("GFW");
		String value = (String) resolver.getValue(context, pet, "name");
		Class clazz = resolver.getType(context, pet, "name");
		System.out.println(clazz);
		System.out.println(value);
	}
}
