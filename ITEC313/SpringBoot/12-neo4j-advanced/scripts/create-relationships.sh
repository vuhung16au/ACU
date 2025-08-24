#!/bin/bash

echo "=== Neo4j Relationship Creation Script ==="
echo ""

# Check if the application is running
echo "1. Checking if the application is running..."
if curl -s http://localhost:8080/actuator/health > /dev/null; then
    echo "✅ Application is running"
else
    echo "❌ Application is not running. Please start the Spring Boot application first."
    exit 1
fi

echo ""
echo "2. Getting relationship information..."
curl -s http://localhost:8080/api/relationships/info | jq '.'

echo ""
echo "3. Creating relationships..."
RESPONSE=$(curl -s -X POST http://localhost:8080/api/relationships/create)
echo $RESPONSE | jq '.'

echo ""
echo "4. Getting relationship statistics..."
curl -s http://localhost:8080/api/relationships/stats | jq '.'

echo ""
echo "=== Script completed ==="
echo ""
echo "You can now check Neo4j Browser at http://localhost:7474 to see the relationships!"
echo "Try these Cypher queries:"
echo "  - MATCH (n)-[r]->(m) RETURN n, r, m LIMIT 25"
echo "  - MATCH (p:Person)-[r:WORKS_FOR]->(c:Company) RETURN p, r, c"
echo "  - MATCH (p:Person)-[r:HAS_SKILL]->(s:Skill) RETURN p, r, s"
echo "  - MATCH (c:Company)-[r:USES_TECHNOLOGY]->(t:Technology) RETURN c, r, t"
