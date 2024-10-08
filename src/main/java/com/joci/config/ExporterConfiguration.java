package com.joci.config;

import java.util.Optional;

public class ExporterConfiguration {

    private static final String ENV_PREFIX = "ZEEBE_JOCI_";

    private String enabledValueTypes = "";
    private String enabledRecordTypes = "";

    public String getEnabledValueTypes() {
        return getEnv("ENABLED_VALUE_TYPES").orElse(enabledValueTypes);
    }

    public String getEnabledRecordTypes() {
        return getEnv("ENABLED_RECORD_TYPES").orElse(enabledRecordTypes);
    }

    private Optional<String> getEnv(String name) {
        return Optional.ofNullable(System.getenv(ENV_PREFIX + name));
    }

    @Override
    public String toString() {
        return "[" +
                ", enabledValueTypes='" + getEnabledValueTypes() + '\'' +
                ", enabledRecordTypes='" + getEnabledRecordTypes() + '\'' +
                ']';
    }
}