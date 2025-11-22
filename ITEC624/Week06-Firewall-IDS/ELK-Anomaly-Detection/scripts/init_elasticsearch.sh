#!/bin/bash
# Initialize Elasticsearch settings for single-node deployment

# Wait for Elasticsearch to be ready
until curl -s "http://localhost:9200/_cluster/health" > /dev/null; do
  echo "Waiting for Elasticsearch..."
  sleep 2
done

echo "Setting default number of replicas to 0 for single-node cluster..."

# Set default template for all new indices
curl -X PUT "http://localhost:9200/_template/single_node_template" \
  -H "Content-Type: application/json" \
  -d '{
    "index_patterns": ["*"],
    "settings": {
      "number_of_replicas": 0
    }
  }'

# Set replicas to 0 for any existing indices  
curl -X PUT "http://localhost:9200/_settings" \
  -H "Content-Type: application/json" \
  -d '{
    "index": {
      "number_of_replicas": 0
    }
  }'

echo "✓ Elasticsearch configured for single-node deployment"