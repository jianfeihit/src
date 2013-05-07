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

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.panguso.app.core.exception.BusinessException;

/**
 * 
 * @author jianfeihit
 * @date 2013-4-14
 */
public final class XMLUtil {

	/**
	 * 
	 */
	private static final String CLASS_NAME = "className";

	private XMLUtil() {

	}

	/**
	 * 将Bean转换为XML
	 * 
	 * @param object bean
	 * @return
	 * @throws BusinessException 转换错长
	 * @author jianfeihit
	 * @date 2013-4-14
	 */
	public static String toXML(Object object) throws BusinessException {
		try {
			Document document = DocumentHelper.createDocument();
			String name = StringUtils.uncapitalize(object.getClass().getSimpleName());
			Element element = document.addElement(name);
			buildElement(element, object);
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			StringWriter writer = new StringWriter();
			XMLWriter out = new XMLWriter(writer, format);
			out.write(document);
			return writer.toString();
		} catch (Exception e) {
			new BusinessException("E401013", new Object[] {"XML", object, e.getMessage() });
			return null;
		}
	}

	public static Object toObject(String xml) throws BusinessException {
		try {
			SAXReader saxReader = new SAXReader();
			StringReader stringReader = new StringReader(xml);
			Document document = saxReader.read(stringReader);
			Element rootElement = document.getRootElement();
			return parseElement(rootElement);
		} catch (Exception e) {
			new BusinessException("E401013", new Object[] {"XML", e.getMessage() });
			return null;
		}
	}

	@SuppressWarnings({"rawtypes", "unchecked" })
	private static Object parseElement(Element element) throws Exception {
		String className = element.attributeValue(CLASS_NAME);
		Object object = Class.forName(className).newInstance();
		for (Field field : object.getClass().getDeclaredFields()) {
			// 无writer的忽略
			if (!PropertyUtils.isWriteable(object, field.getName())) {
				continue;
			}

			String fieldName = field.getName();
			XMLSchema xmlSchema = field.getAnnotation(XMLSchema.class);

			// 基本类型来自于属性
			if (field.getClass().isPrimitive() || field.getClass().equals(String.class)) {
				String value = element.attributeValue(fieldName);
				if (StringUtils.isNotEmpty(value)) {
					PropertyUtils.setProperty(object, fieldName, value);
				}
			} else if (field.getClass().equals(List.class)) {
				List elements = null;
				if (xmlSchema == null || !xmlSchema.silent()) {
					Element tempEle = element.element(fieldName);
					elements = tempEle.elements();
				} else {
					elements = element.elements(fieldName);
				}
				if (CollectionUtils.isNotEmpty(elements)) {
					List values = new ArrayList();
					for (Object eleObj : elements) {
						Element ele = (Element) eleObj;
						Object eleValue = parseElement(ele);
						values.add(eleValue);
					}
					PropertyUtils.setProperty(object, fieldName, values);
				}
			} else if (field.getClass().equals(Map.class)) {

			} else {

			}
		}

		int attrCount = element.attributeCount();
		for (int t = 0; t < attrCount; t++) {
			Attribute attr = element.attribute(t);
			String attriName = attr.getName();
			if (!CLASS_NAME.equals(attr.getName())) {
				PropertyUtils.setProperty(object, attriName, attr.getData());
			}
		}

		List eleList = element.elements();
		for (Object eleObj : eleList) {
			Element ele = (Element) eleObj;
			String eleName = ele.getName();
			Object eleValue = parseElement(ele);
			PropertyUtils.setProperty(object, eleName, eleValue);
		}
		return object;
	}

	@SuppressWarnings("rawtypes")
	private static void buildElement(Element element, Object object) throws Exception {
		element.addAttribute(CLASS_NAME, object.getClass().getName());
		for (Field field : object.getClass().getDeclaredFields()) {
			// 无getter方法的属性,如serialVersionUID,需要过滤掉
			if (!PropertyUtils.isReadable(object, field.getName())) {
				continue;
			}
			// 属性值为空的过滤掉
			Object obj = PropertyUtils.getProperty(object, field.getName());
			if (obj == null) {
				continue;
			}

			// 获取序列化方法
			XMLSchema xmlSchema = field.getAnnotation(XMLSchema.class);
			// 如果是基本类型，则直接添加属性
			if (field.getType().isPrimitive()) {
				if (xmlSchema != null && xmlSchema.text()) {
					element.addText(obj.toString());
				} else {
					element.addAttribute(field.getName(), obj.toString());
				}
				continue;
			}

			if (obj instanceof List) {
				Element childrenEle = element;
				if (xmlSchema == null || !xmlSchema.silent()) {
					childrenEle = element.addElement(field.getName());
				}
				List children = (List) obj;
				for (Object child : children) {
					String name = StringUtils.uncapitalize(child.getClass().getSimpleName());
					if (xmlSchema != null && StringUtils.isNotEmpty(xmlSchema.nameAlias())) {
						name = xmlSchema.nameAlias();
					}
					Element childEle = childrenEle.addElement(name);
					buildElement(childEle, child);
				}
			} else if (obj instanceof Map) {
				Element childrenEle = element;
				if (xmlSchema == null || !xmlSchema.silent()) {
					childrenEle = element.addElement(field.getName());
				}
				Map children = (Map) obj;
				for (Object childKey : children.keySet()) {
					Object value = children.get(childKey);
					String name = StringUtils.uncapitalize(children.getClass().getSimpleName());
					if (xmlSchema != null && StringUtils.isNotEmpty(xmlSchema.nameAlias())) {
						name = xmlSchema.nameAlias();
					}
					Element childEle = childrenEle.addElement(name);
					if (value instanceof String) {
						if (xmlSchema != null && StringUtils.isNotEmpty(xmlSchema.keyAlias())
						        && StringUtils.isNotEmpty(xmlSchema.valueAlias())) {
							childEle.addAttribute(xmlSchema.keyAlias(), childKey.toString());
							childEle.addAttribute(xmlSchema.valueAlias(), value.toString());
						} else {
							childEle.addAttribute("key", childKey.toString());
							childEle.addAttribute("value", value.toString());
						}
					} else {
						buildElement(childEle, value);
					}
				}
			} else if (obj instanceof String) {
				if (xmlSchema != null && xmlSchema.text()) {
					element.addText(obj.toString());
				} else {
					element.addAttribute(field.getName(), obj.toString());
				}
			} else {
				String name = StringUtils.uncapitalize(obj.getClass().getSimpleName());
				Element childEle = element.addElement(name);
				buildElement(childEle, obj);
			}
		}
	}
}
