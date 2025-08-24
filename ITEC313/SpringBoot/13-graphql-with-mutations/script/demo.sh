#!/bin/bash

echo "=== Spring Boot GraphQL Server Demo ==="
echo

# Check if the server is running
echo "1. Checking if the server is running..."
if curl -s http://localhost:8081/graphql > /dev/null; then
    echo "✅ Server is running on http://localhost:8081"
else
    echo "❌ Server is not running. Please start it with: mvn spring-boot:run"
    echo "   Then run this script again."
    exit 1
fi

echo
echo "2. Testing GraphQL Queries..."

# Test 1: Query book-1 (The Lucky Country)
echo
echo "📚 Querying 'The Lucky Country' (book-1):"
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{
    "query": "query { bookById(id: \"book-1\") { id name pageCount author { id firstName lastName } } }"
  }' \
  http://localhost:8081/graphql

echo
echo

# Test 2: Query book-2 (The Magic Pudding)
echo "📚 Querying 'The Magic Pudding' (book-2):"
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{
    "query": "query { bookById(id: \"book-2\") { id name pageCount author { id firstName lastName } } }"
  }' \
  http://localhost:8081/graphql

echo
echo

# Test 3: Query non-existent book
echo "❌ Querying non-existent book:"
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{
    "query": "query { bookById(id: \"non-existent\") { id name pageCount author { id firstName lastName } } }"
  }' \
  http://localhost:8081/graphql

echo
echo

# Test 4: Query with variables
echo "🔧 Querying with variables:"
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{
    "query": "query GetBook($id: ID!) { bookById(id: $id) { id name pageCount author { firstName lastName } } }",
    "variables": { "id": "book-1" }
  }' \
  http://localhost:8081/graphql

echo
echo

echo "3. Testing GraphQL Pagination..."

# Test 11: Query books with pagination
echo
echo "📖 Querying books with pagination (first 3):"
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{
    "query": "query { books(first: 3) { edges { cursor node { id name pageCount author { id firstName lastName } } } pageInfo { hasNextPage hasPreviousPage startCursor endCursor } totalCount } }"
  }' \
  http://localhost:8081/graphql

echo
echo

# Test 12: Query books with cursor pagination
echo "📖 Querying books with cursor pagination:"
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{
    "query": "query { books(first: 2, after: \"Ym9vay0x\") { edges { cursor node { id name pageCount author { id firstName lastName } } } pageInfo { hasNextPage hasPreviousPage startCursor endCursor } totalCount } }"
  }' \
  http://localhost:8081/graphql

echo
echo

echo "4. Testing GraphQL Mutations..."

# Test 5: Create a new author
echo
echo "👤 Creating a new author:"
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{
    "query": "mutation { createAuthor(input: { firstName: \"Jane\", lastName: \"Austen\" }) { id firstName lastName } }"
  }' \
  http://localhost:8081/graphql

echo
echo

# Test 6: Create a new book
echo "📖 Creating a new book:"
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{
    "query": "mutation { createBook(input: { name: \"Pride and Prejudice\", pageCount: 432, authorId: \"author-1\" }) { id name pageCount author { id firstName lastName } } }"
  }' \
  http://localhost:8081/graphql

echo
echo

# Test 7: Update a book
echo "✏️ Updating book-1:"
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{
    "query": "mutation { updateBook(id: \"book-1\", input: { name: \"The Lucky Country - Updated Edition\", pageCount: 320 }) { id name pageCount author { id firstName lastName } } }"
  }' \
  http://localhost:8081/graphql

echo
echo

# Test 8: Delete a book
echo "🗑️ Deleting book-2:"
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{
    "query": "mutation { deleteBook(id: \"book-2\") }"
  }' \
  http://localhost:8081/graphql

echo
echo

# Test 9: Try to delete non-existent book
echo "❌ Trying to delete non-existent book:"
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{
    "query": "mutation { deleteBook(id: \"non-existent\") }"
  }' \
  http://localhost:8081/graphql

echo
echo

# Test 10: Verify book-1 was updated
echo "✅ Verifying book-1 was updated:"
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{
    "query": "query { bookById(id: \"book-1\") { id name pageCount author { id firstName lastName } } }"
  }' \
  http://localhost:8081/graphql

echo
echo

echo "=== Demo completed ==="
echo
echo "💡 You can also access the interactive GraphiQL interface at:"
echo "   http://localhost:8081/graphiql"
echo
echo "📖 Available books:"
echo "   - book-1: The Lucky Country by Donald Horne"
echo "   - book-2: The Magic Pudding by Norman Lindsay (deleted in demo)"
echo
echo "🔄 Features demonstrated:"
echo "   - bookById: Query individual books"
echo "   - books: Paginated book queries with cursor-based pagination"
echo "   - createAuthor: Add new authors"
echo "   - createBook: Add new books"
echo "   - updateBook: Modify existing books"
echo "   - deleteBook: Remove books"
