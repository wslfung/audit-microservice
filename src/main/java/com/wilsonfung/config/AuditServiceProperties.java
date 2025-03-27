package com.wilsonfung.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "audit.service")
public class AuditServiceProperties {
    private String samlEntityId;
    private String samlMetadataUri;
    private String samlPrivateKeyLocation;
    private String samlCertificateLocation;
    
    private String kafkaBootstrapServers;
    private String kafkaGroupId;
    private String kafkaAutoOffsetReset;
    private String kafkaKeyDeserializer;
    private String kafkaValueDeserializer;
    
    private String mongodbHost;
    private int mongodbPort;
    private String mongodbDatabase;
    
    private String postgresUrl;
    private String postgresUsername;
    private String postgresPassword;
    private String postgresDriver;

    // Getters and Setters
    public String getSamlEntityId() {
        return samlEntityId;
    }

    public void setSamlEntityId(String samlEntityId) {
        this.samlEntityId = samlEntityId;
    }

    public String getSamlMetadataUri() {
        return samlMetadataUri;
    }

    public void setSamlMetadataUri(String samlMetadataUri) {
        this.samlMetadataUri = samlMetadataUri;
    }

    public String getSamlPrivateKeyLocation() {
        return samlPrivateKeyLocation;
    }

    public void setSamlPrivateKeyLocation(String samlPrivateKeyLocation) {
        this.samlPrivateKeyLocation = samlPrivateKeyLocation;
    }

    public String getSamlCertificateLocation() {
        return samlCertificateLocation;
    }

    public void setSamlCertificateLocation(String samlCertificateLocation) {
        this.samlCertificateLocation = samlCertificateLocation;
    }

    public String getKafkaBootstrapServers() {
        return kafkaBootstrapServers;
    }

    public void setKafkaBootstrapServers(String kafkaBootstrapServers) {
        this.kafkaBootstrapServers = kafkaBootstrapServers;
    }

    public String getKafkaGroupId() {
        return kafkaGroupId;
    }

    public void setKafkaGroupId(String kafkaGroupId) {
        this.kafkaGroupId = kafkaGroupId;
    }

    public String getKafkaAutoOffsetReset() {
        return kafkaAutoOffsetReset;
    }

    public void setKafkaAutoOffsetReset(String kafkaAutoOffsetReset) {
        this.kafkaAutoOffsetReset = kafkaAutoOffsetReset;
    }

    public String getKafkaKeyDeserializer() {
        return kafkaKeyDeserializer;
    }

    public void setKafkaKeyDeserializer(String kafkaKeyDeserializer) {
        this.kafkaKeyDeserializer = kafkaKeyDeserializer;
    }

    public String getKafkaValueDeserializer() {
        return kafkaValueDeserializer;
    }

    public void setKafkaValueDeserializer(String kafkaValueDeserializer) {
        this.kafkaValueDeserializer = kafkaValueDeserializer;
    }

    public String getMongodbHost() {
        return mongodbHost;
    }

    public void setMongodbHost(String mongodbHost) {
        this.mongodbHost = mongodbHost;
    }

    public int getMongodbPort() {
        return mongodbPort;
    }

    public void setMongodbPort(int mongodbPort) {
        this.mongodbPort = mongodbPort;
    }

    public String getMongodbDatabase() {
        return mongodbDatabase;
    }

    public void setMongodbDatabase(String mongodbDatabase) {
        this.mongodbDatabase = mongodbDatabase;
    }

    public String getPostgresUrl() {
        return postgresUrl;
    }

    public void setPostgresUrl(String postgresUrl) {
        this.postgresUrl = postgresUrl;
    }

    public String getPostgresUsername() {
        return postgresUsername;
    }

    public void setPostgresUsername(String postgresUsername) {
        this.postgresUsername = postgresUsername;
    }

    public String getPostgresPassword() {
        return postgresPassword;
    }

    public void setPostgresPassword(String postgresPassword) {
        this.postgresPassword = postgresPassword;
    }

    public String getPostgresDriver() {
        return postgresDriver;
    }

    public void setPostgresDriver(String postgresDriver) {
        this.postgresDriver = postgresDriver;
    }
}
