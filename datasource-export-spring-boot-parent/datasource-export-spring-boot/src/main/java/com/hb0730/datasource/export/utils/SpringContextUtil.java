package com.hb0730.datasource.export.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil implements ApplicationContextAware{

	private static ApplicationContext appContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		appContext = applicationContext;
	}


    public static ApplicationContext getApplicationContext(){
    	return appContext;
    }

	public static <T> T getBean(Class<T> clazz){
		return appContext.getBean(clazz);
	}

	public static Object getBean(String className){
		return appContext.getBean(className);
	}

}
