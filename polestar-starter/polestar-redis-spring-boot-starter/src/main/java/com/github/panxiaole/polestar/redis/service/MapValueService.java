package com.github.panxiaole.polestar.redis.service;


import com.github.panxiaole.polestar.redis.annotation.MapValue;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * mapValue赋值方法
 *
 * @author panxiaole
 * @date 2019-06-08
 */
@Component
public class MapValueService implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void mapValue(Collection collection) {
		if (CollectionUtils.isEmpty(collection)) {
			return;
		}

		//1 获取集合中的Class对象
		Class<?> clazz = collection.iterator().next().getClass();

		//2 获取Class对象及其父类的所有字段
		List<Field> fields = new ArrayList<>();
		Class tempClass = clazz;
		while (tempClass != null) {
			// 当父类为null的时候说明到达了最上层的父类(Object类).
			fields.addAll(Arrays.asList(tempClass.getDeclaredFields()));
			tempClass = tempClass.getSuperclass(); // 得到父类,然后赋给自己
		}

		//3 遍历Class的字段,获取需要设置值的字段
		for (Field field : fields) {
			try {
				//3.1获取有SetValue注解的字段
				MapValue annotation = field.getAnnotation(MapValue.class);

				if (annotation == null) {
					continue;
				}
				//3.2 设置私有字段可以被反射直接访问
				field.setAccessible(true);

				//3.3 获取要调用的bean
				Object bean = applicationContext.getBean(annotation.bean());

				//3.4 获取入参字段并设置可访问属性
				Field sourceField = null;
				for (Field f : fields) {
					if (annotation.source().equals(f.getName())) {
						sourceField = f;
						break;
					}
				}
				if(sourceField == null) {
					continue;
				}
				sourceField.setAccessible(true);

				//3.5 获取要调用的方法
				Method method = annotation.bean().getMethod(annotation.method(), sourceField.getType());

				//3.6 定义需要赋值的目标字段
				Field targetField = null;

				//4 遍历集合,为集合中需要赋值的对象赋值
				for (Object obj : collection) {

					//4.1 获取原字段参数值
					Object sourceValue = sourceField.get(obj);
					if (sourceValue == null) {
						continue;
					}

					//4.2 调用方法获取目标字段的值
					Object targetValue = method.invoke(bean, sourceValue);
					if (targetValue != null) {

						targetField = targetField == null ? targetValue.getClass().getDeclaredField(annotation.target()) : targetField;
						targetField.setAccessible(true);
						targetValue = targetField.get(targetValue);
					}

					//5 设置值
					field.set(obj, targetValue);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
