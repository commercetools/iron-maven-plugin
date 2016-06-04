package com.commercetools.build.iron;

import com.google.gson.annotations.SerializedName;

public class CodesDraft {
    private String name;
    private String image;
    private String command;
    private String config;
    @SerializedName("max_concurrency")
    private String maxConcurrency;
    private String runtime;
    private String stack;
    private Integer retries;
    @SerializedName("retries_delay")
    private Integer retriesDelay;
    @SerializedName("default_priority")
    private String defaultPriority;
    @SerializedName("env_vars")
    private String envVars;

    public CodesDraft() {
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(final String config) {
        this.config = config;
    }

    public String getDefaultPriority() {
        return defaultPriority;
    }

    public void setDefaultPriority(final String defaultPriority) {
        this.defaultPriority = defaultPriority;
    }

    public String getEnvVars() {
        return envVars;
    }

    public void setEnvVars(final String envVars) {
        this.envVars = envVars;
    }

    public String getImage() {
        return image;
    }

    public void setImage(final String image) {
        this.image = image;
    }

    public String getMaxConcurrency() {
        return maxConcurrency;
    }

    public void setMaxConcurrency(final String maxConcurrency) {
        this.maxConcurrency = maxConcurrency;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getRetries() {
        return retries;
    }

    public void setRetries(final Integer retries) {
        this.retries = retries;
    }

    public Integer getRetriesDelay() {
        return retriesDelay;
    }

    public void setRetriesDelay(final Integer retriesDelay) {
        this.retriesDelay = retriesDelay;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(final String stack) {
        this.stack = stack;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(final String runtime) {
        this.runtime = runtime;
    }
}
