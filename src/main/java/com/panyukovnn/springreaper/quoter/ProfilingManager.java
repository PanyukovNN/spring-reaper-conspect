package com.panyukovnn.springreaper.quoter;

public class ProfilingManager implements ProfilingManagerMBean {
    private boolean enabled = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
