package com.panyukovnn.springreaper.quoter;

/**
 * MBean можно включать и выключать поля в JVisualVM где есть плагин MBeans
 */
public interface ProfilingManagerMBean {

    void setEnabled(boolean enabled);
}
