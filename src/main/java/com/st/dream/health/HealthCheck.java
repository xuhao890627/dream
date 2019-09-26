package com.st.dream.health;

import com.st.dream.exception.ConnectorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface HealthCheck {

    Logger healthcheckLogger = LoggerFactory.getLogger("healthcheckLogger");

    String runHealthCheck() throws ConnectorException;
}
