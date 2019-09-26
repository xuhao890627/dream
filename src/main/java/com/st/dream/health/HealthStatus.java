package com.st.dream.health;

public class HealthStatus {

    private boolean healthy;
    private String message;
    private long ms;

    public HealthStatus(String problem, long timeMs) {
        if (problem == null) {
            this.healthy = true;
            this.message = "";
        } else {
            this.healthy = false;
            this.message = problem;
        }

        this.ms = timeMs;
    }

    public HealthStatus(String problem) {
        this(problem, 0L);
    }

    public boolean getHealthy() {
        return this.healthy;
    }

    public String getMessage() {
        return this.message;
    }

    public long getMs() {
        return this.ms;
    }

    public void setMs(long l) {
        this.ms = l;
    }
}
