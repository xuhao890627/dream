package com.st.dream.health;

import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class HealthCheckService {

    private static Logger logger = LoggerFactory.getLogger("healthcheckLogger");
    private List<DependencyHealthCheck> mandatory;
    private List<DependencyHealthCheck> optional;
    private Long timeout;
    private final ExecutorService threadPool;

    public HealthCheckService(Long timeout, DependencyHealthCheck... mandatoryDependencies) {
        this.mandatory = Arrays.asList(mandatoryDependencies);
        this.optional = new ArrayList();
        this.timeout = timeout;
        this.threadPool = Executors.newFixedThreadPool(this.mandatory.size() + this.optional.size());
    }

    public boolean ping() {
        Map<String, HealthStatus> healthCheck = this.deepHealthCheck(this.mandatory);
        return this.areDependenciesHealthy(healthCheck);
    }

    public Map<String, HealthStatus> healthCheck() {
        return this.deepHealthCheck(this.mandatory, this.optional);
    }

    private Map<String, HealthStatus> deepHealthCheck(List<DependencyHealthCheck>... dependencies) {
        Map<String, HealthStatus> response = new HashMap();
        long start = System.currentTimeMillis();
        ((Stream)Stream.of(dependencies).flatMap(Collection::stream).map((dependencyHealthCheck) -> {
            return new Pair(dependencyHealthCheck, this.threadPool.submit(dependencyHealthCheck));
        }).parallel()).forEach((dependencyHealthCheckFuturePair) -> {
            HealthStatus healthStatus;
//            try {
//                healthStatus = (HealthStatus)((Future)dependencyHealthCheckFuturePair.getValue()).get(this.timeout, TimeUnit.MILLISECONDS);
//            } catch (InterruptedException var5) {
//                this.logHealthCheckError(((DependencyHealthCheck)dependencyHealthCheckFuturePair.getKey()).getName(), "Interrupt exception while trying to get health status", var5);
//                healthStatus = new HealthStatus("ERROR: Interrupt");
//            } catch (ExecutionException var6) {
//                this.logHealthCheckError(((DependencyHealthCheck)dependencyHealthCheckFuturePair.getKey()).getName(), "Execution exception while trying to get health status", var6);
//                healthStatus = new HealthStatus("ERROR: General");
//            } catch (TimeoutException var7) {
//                this.logHealthCheckError(((DependencyHealthCheck)dependencyHealthCheckFuturePair.getKey()).getName(), "Timeout while trying to get health status", var7);
//                healthStatus = new HealthStatus("TIMEOUT (over " + this.timeout + "ms)");
//            }

//            response.put(((DependencyHealthCheck)dependencyHealthCheckFuturePair.getKey()).getName(), healthStatus);
        });
        this.addOverallHealthStatusToReport(response, System.currentTimeMillis() - start);
        return response;
    }

    public void addOptionalDependency(DependencyHealthCheck... optionalDependencies) {
        Collections.addAll(this.optional, optionalDependencies);
    }

    private void addOverallHealthStatusToReport(Map<String, HealthStatus> healthReport, long timePassedMs) {
        boolean allHealthy = this.areDependenciesHealthy(healthReport);
        String pingMsg = allHealthy ? null : "One or more dependencies failed health check";
        healthReport.put("ping", new HealthStatus(pingMsg, timePassedMs));
    }

    public boolean areDependenciesHealthy(Map<String, HealthStatus> healthReport) {
        Iterator var2 = healthReport.values().iterator();

        HealthStatus hs;
        do {
            if (!var2.hasNext()) {
                return true;
            }

            hs = (HealthStatus)var2.next();
        } while(hs.getHealthy());

        return false;
    }

    protected void logHealthCheckError(String dependencyName, String problem, Exception ex) {
        String msg = String.format("%s Health Check: %s", dependencyName, problem);
        if (ex == null) {
            logger.error(msg);
        } else {
            logger.error(msg + "\nError details: ", ex);
        }

    }

    public void shutdown() {
        this.threadPool.shutdown();
    }
}
