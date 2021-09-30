package com.panyukovnn.springreaper.quoter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;

/**
 * Реализация третей фазы конструктора, после создания прокси
 */
public class PostProxyInvokerContextListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        String[] beanDefinitionNames = context.getBeanDefinitionNames();

        for (String name : beanDefinitionNames) {
            // это необходимо, чтобы достать оригинальное название класса, если мы просто попробуем получить
            // название класса, то получим имя прокси
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(name);

            String beanClassName = beanDefinition.getBeanClassName();

            try {
                Class<?> originalClass = Class.forName(beanClassName);

                Method[] methods = originalClass.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(PostProxy.class)) {
                        Object bean = context.getBean(name);
                        Method currentMethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                        currentMethod.invoke(bean);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
