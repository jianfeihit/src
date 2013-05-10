package com.jeff.demo.tag.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class SimpleDemoTag extends TagSupport {
	
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			out.println("Hello,World"); // 页面中显示的内容
		} catch (IOException e) {
			throw new JspException(e);
		}
		return SKIP_PAGE; // 不包含主体内容
	}
}
