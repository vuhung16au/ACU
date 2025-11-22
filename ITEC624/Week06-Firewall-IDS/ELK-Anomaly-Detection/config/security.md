# Enabling Security for Production

This lab has security disabled by default for educational purposes.
To enable security for production use:

## Steps

1. **Edit `.env`**:
   ```bash
   ELASTIC_SECURITY=true
   KIBANA_AUTH_REQUIRED=true
   ```

2. **Update docker-compose.yml**:
   Remove or set to `true`:
   ```yaml
   elasticsearch:
     environment:
       - xpack.security.enabled=true
       - xpack.security.http.ssl.enabled=true
   ```

3. **Set passwords**:
   ```bash
   docker-compose exec elasticsearch bin/elasticsearch-setup-passwords auto
   ```
   
   Save the generated passwords securely!

4. **Update Kibana configuration**:
   ```yaml
   kibana:
     environment:
       - ELASTICSEARCH_USERNAME=elastic
       - ELASTICSEARCH_PASSWORD=<your-password>
   ```

5. **Restart services**:
   ```bash
   make restart
   ```

6. **Login to Kibana**:
   - URL: http://localhost:5601
   - Username: `elastic`
   - Password: (from step 3)

## Production Best Practices

- Use TLS/SSL certificates
- Enable audit logging
- Implement role-based access control (RBAC)
- Regular security updates
- Network segmentation
- Backup encryption keys

## References

- [Elasticsearch Security](https://www.elastic.co/guide/en/elasticsearch/reference/current/security-basic-setup.html)
- [Kibana Security](https://www.elastic.co/guide/en/kibana/current/using-kibana-with-security.html)

