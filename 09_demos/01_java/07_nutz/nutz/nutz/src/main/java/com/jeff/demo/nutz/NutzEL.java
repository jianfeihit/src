package com.jeff.demo.nutz;

import org.nutz.el.El;
import org.nutz.lang.Lang;
import org.nutz.lang.util.Context;

/**
 * Hello world!
 * 
 */
public class NutzEL {
	public static void main(String[] args) {
		Context context = Lang.context();
		Pet pet = new Pet();
		pet.setName("GFW");
		context.set("pet",pet);
		System.out.println(El.eval(context,"pet.name"));
	}
}
