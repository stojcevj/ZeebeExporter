package com.joci.config;

import lombok.Getter;

import java.util.Optional;

public class ExporterConfiguration {

    private static final String ENV_PREFIX = "ZEEBE_ASPEKT_";

    private String enabledValueTypes = "";

    private String enabledRecordTypes = "";

    private String JDBCConnection = "";

    private String JDBCUser = "";

    private String JDBCPassword = "";

    @Getter
    private String MigrationLocation = "classpath:db/migration";

    @Getter
    private String MigrationSchema = "dbo";

    public String getEnabledValueTypes() {
        return getEnv("ENABLED_VALUE_TYPES").orElse(enabledValueTypes);
    }

    public String getEnabledRecordTypes() {
        return getEnv("ENABLED_RECORD_TYPES").orElse(enabledRecordTypes);
    }

    public String getJDBCConnection(){
        return getEnv("JDBC_CONNECTION").orElse(JDBCConnection);
    }

    public String getJDBCUser(){
        return getEnv("JDBC_USER").orElse(JDBCUser);
    }

    public String getJDBCPassword(){
        return getEnv("JDBC_PASSWORD").orElse(JDBCPassword);
    }

    private Optional<String> getEnv(String name) {
        return Optional.ofNullable(System.getenv(ENV_PREFIX + name));
    }

    @Override
    public String toString() {
        return "[" +
                ", enabledValueTypes='" + getEnabledValueTypes() + '\'' +
                ", enabledRecordTypes='" + getEnabledRecordTypes() + '\'' +
                ", JDBCConnection='" + getJDBCConnection() + '\'' +
                ", JDBCUser='" + getJDBCUser() + '\'' +
                ", JDBCPassword='" + getJDBCPassword() + '\'' +
                ", MigrationLocation='" + getMigrationLocation() + '\'' +
                ", MigrationSchema='" + getMigrationSchema() + '\'' +
                ']';
    }
}