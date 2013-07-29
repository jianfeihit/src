package com.obss.radar.searcher.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.obss.radar.searcher.service.ProgramException;

/**
 * 
 * @author iceman
 * @date 2012-10-18
 */
public class BeanMapConverUtil {
	
    public static <T> Map<String, String> beanToMap(T bean) {   
  
        Class<? extends Object> type = bean.getClass();   
        Map<String, String> returnMap = new HashMap<String, String>();   
  
        try {   
            BeanInfo beanInfo = Introspector.getBeanInfo(type);   
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();   
            for (PropertyDescriptor descriptor : propertyDescriptors) {   
                String propertyName = descriptor.getName();   
                if (!propertyName.equals("class")) {   
                    Method readMethod = descriptor.getReadMethod();   
                    Object result = readMethod.invoke(bean, new Object[0]);   
                    returnMap.put(propertyName, result != null ? result.toString() : "");                     
                }   
            }   
        } catch (Exception e) {   
            throw new ProgramException(e);   
        }   
        return returnMap;   
    }   

   
	public static <T> Map<String, Object> beanToMapObj(T bean) {   
		  
        Class<? extends Object> type = bean.getClass();   
        Map<String, Object> returnMap = new HashMap<String, Object>();   
  
        try {   
            BeanInfo beanInfo = Introspector.getBeanInfo(type);   
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();   
            for (PropertyDescriptor descriptor : propertyDescriptors) {   
                String propertyName = descriptor.getName();   
                if (!propertyName.equals("class")) {   
                    Method readMethod = descriptor.getReadMethod();   
                    Object result = readMethod.invoke(bean, new Object[0]);   
                    returnMap.put(propertyName, result);                     
                }   
            }   
        } catch (Exception e) {   
            throw new ProgramException(e);   
        }   
        return returnMap;   
    }  

	public static <T> Map<String, Object> beanToMapObjExceptNull(T bean) {   
		  
        Class<? extends Object> type = bean.getClass();   
        Map<String, Object> returnMap = new HashMap<String, Object>();   
  
        try {   
            BeanInfo beanInfo = Introspector.getBeanInfo(type);   
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();   
            for (PropertyDescriptor descriptor : propertyDescriptors) {   
                String propertyName = descriptor.getName();   
                if (!propertyName.equals("class")) {   
                    Method readMethod = descriptor.getReadMethod();   
                    Object result = readMethod.invoke(bean, new Object[0]); 
                    if (result != null) {
                    	returnMap.put(propertyName, result);
                    }
                }   
            }   
        } catch (Exception e) {   
            throw new ProgramException(e);   
        }   
        return returnMap;   
    }  
	  	  
    public static <T> T mapToBean(Map<String, Object> paramMap, Class<T> clazz) {    
        T beanObj = null;   
        try {
            beanObj = clazz.newInstance();   
            String propertyName = null;   
            Object propertyValue = null;   
            for (Map.Entry<String, Object> entity : paramMap.entrySet()) {   
                propertyName = entity.getKey();   
                propertyValue = entity.getValue();   
                setProperties(beanObj, propertyName, propertyValue);   
            }   
        } catch (Exception e) {   
            throw new ProgramException(e);   
        } 
        return beanObj;   
    }   
  
    private static <T> void setProperties(T entity, String propertyName,   
            Object value) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {   
        PropertyDescriptor pd = new PropertyDescriptor(propertyName, entity.getClass());   
        Method methodSet = pd.getWriteMethod();   
        methodSet.invoke(entity, value);   
    }   

}
