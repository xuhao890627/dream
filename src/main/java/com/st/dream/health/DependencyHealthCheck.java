package com.st.dream.health;

import com.st.dream.exception.ConnectorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public class DependencyHealthCheck implements Callable<HealthStatus> {

    private static Logger healthcheckLogger = LoggerFactory.getLogger("healthcheckLogger");
    private String dependencyName;
    private HealthCheck healthCheck;

    public DependencyHealthCheck(String dependencyName, HealthCheck healthCheck) {
        this.dependencyName = dependencyName;
        this.healthCheck = healthCheck;
    }

    public String getName() {
        return this.dependencyName;
    }

    public HealthStatus getHealthCheck() throws ConnectorException {
        long startTime = System.currentTimeMillis();
        String problem = this.healthCheck.runHealthCheck();
        long stopTime = System.currentTimeMillis();
        return new HealthStatus(problem, stopTime - startTime);
    }

    public HealthStatus call() throws Exception {
        return this.getHealthCheck();
    }
}
