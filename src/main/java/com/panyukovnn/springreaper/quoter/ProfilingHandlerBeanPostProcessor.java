package com.panyukovnn.springreaper.quoter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {

    /**
     * имя бина, даже после создания прокси объекта, не меняется
     * соответственно можно его запоминать на этапе beforeInitialization и реализовывать логику на этапе afterInitialization
     */
    private Map<String, Class> map = new HashMap<>();

    /**
     * Данный класс необходим чтобы можно было выключить работу аннотации, уменьшая нагрузку
     */
    private ProfilingManager manager = new ProfilingManager();

    public ProfilingHandlerBeanPostProcessor() throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException {
        // Здесь можно регистрировать MBean'ы - (классы реализующие интерфейс, который имеет такое же название как и класс,
        // только с постфиксом MBean.
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();

        platformMBeanServer.registerMBean(manager, new ObjectName("profiling", "name", "manager"));
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Profiling.class)) {
            map.put(beanName, beanClass);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = map.get(beanName);

        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if (!manager.isEnabled()) {
                        return method.invoke(bean, args);
                    }

                    // Здесь будет логика, которая будет оборачивать каждый метод класса, помеченного аннотацией @Profiling
                    System.out.println("Профилирую");

                    long before = System.nanoTime();
                    Object returnVal = method.invoke(bean, args);
                    long after = System.nanoTime();

                    System.out.println((after - before) / 1_000_000d);
                    System.out.println("Закончил профилировать");

                    return returnVal;
                }
            });
        }

        return bean;
    }
}
