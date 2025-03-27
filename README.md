# Audit Microservice

### Required Environment Variables

The following environment variables are required:

```yaml
# Server Configuration
AUDIT_SERVICE_SERVER_PORT=8080

# SAML Configuration
AUDIT_SERVICE_SAML_ENTITY_ID=http://localhost:8080/saml
AUDIT_SERVICE_SAML_METADATA_URI=http://localhost:8080/saml/metadata
AUDIT_SERVICE_SAML_PRIVATE_KEY_LOCATION=classpath:saml/private.key
AUDIT_SERVICE_SAML_CERTIFICATE_LOCATION=classpath:saml/public.crt

# Kafka Configuration
AUDIT_SERVICE_KAFKA_BOOTSTRAP_SERVERS=kafka:9092
AUDIT_SERVICE_KAFKA_GROUP_ID=auditor-group
AUDIT_SERVICE_KAFKA_AUTO_OFFSET_RESET=earliest
AUDIT_SERVICE_KAFKA_KEY_DESERIALIZER=org.apache.kafka.common.serialization.StringDeserializer
AUDIT_SERVICE_KAFKA_VALUE_DESERIALIZER=org.apache.kafka.common.serialization.StringDeserializer

# MongoDB Configuration
AUDIT_SERVICE_MONGODB_HOST=mongodb
AUDIT_SERVICE_MONGODB_PORT=27017
AUDIT_SERVICE_MONGODB_DATABASE=audit

# PostgreSQL Configuration
AUDIT_SERVICE_POSTGRES_URL=jdbc:postgresql://postgres:5432/audit_quartz
AUDIT_SERVICE_POSTGRES_USERNAME=postgres
AUDIT_SERVICE_POSTGRES_PASSWORD=postgres
AUDIT_SERVICE_POSTGRES_DRIVER=org.postgresql.Driver
```

### Security Note

for simplicity, I've included the some passwords in the repository. In a production environment, one should use appropriate secrets management tools, I've also disabled saml authentication as I don't have an idp for validation.