package com.jeff.demo.el;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.simpleEL.eval.DefaultExpressEvalService;
import com.alibaba.simpleEL.preprocess.ExpressUtils;
import com.alibaba.simpleEL.preprocess.ExpressUtils.Token;

/**
 * 使用alibaba的开源项目SimpleEL
 * @author jianfeihit
 *
 */
public class SimpleEL {
	public static void demo(){
		DefaultExpressEvalService service = new DefaultExpressEvalService();
		service.setAllowMultiStatement(true);
		 
		service.regsiterVariant(Person.class, "p");
		service.regsiterVariant(List.class, "list");
		 
		List<Person> persons = new ArrayList<Person>();
		persons.add(new Person(18, "吴能"));
		persons.add(new Person(27, "刘芒"));
		persons.add(new Person(40, "黄警"));
		persons.add(new Person(50, "夏留"));
		 
		List<Person> list = new ArrayList<Person>();
		for (Person p : persons) {
		    Map<String, Object> ctx = new HashMap<String, Object>();
		    ctx.put("p", p);
			ctx.put("list", list);
		    service.eval(ctx, "if (@p.getAge() > 30) { @list.add(@p); } return null;");
		}
		System.out.println(list.size());
	}
	
	public static void main(String[] args) {
//		demo();
		List<Token> list = ExpressUtils.parse("if (@p.getAge() > 30) { @list.add(@p); } return null;");
		for(Token token : list){
			System.out.println(token.getType()+"="+token.getText());
		}
	}
}
