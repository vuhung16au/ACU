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
echo "2. Testing Authentication..."

# Login to get JWT token
echo "🔐 Logging in with default credentials..."
LOGIN_RESPONSE=$(curl -s -X POST \
  -H "Content-Type: application/json" \
  -d '{
    "username": "313@acu.com",
    "password": "123456"
  }' \
  http://localhost:8081/auth/login)

# Extract token from response
TOKEN=$(echo $LOGIN_RESPONSE | grep -o '"token":"[^"]*"' | cut -d'"' -f4)

if [ -z "$TOKEN" ]; then
    echo "❌ Login failed. Please check if the server is running and the default user exists."
    echo "Response: $LOGIN_RESPONSE"
    exit 1
fi

echo "✅ Login successful! JWT token obtained."
echo "Token: ${TOKEN:0:50}..."

echo
echo "3. Testing GraphQL Queries..."

# Test 1: Query book-1 (The Lucky Country) - Full fields
echo
echo "📚 Querying 'The Lucky Country' (book-1) - Full fields:"
curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "query": "query { bookById(id: \"book-1\") { id name pageCount author { id firstName lastName } } }"
  }' \
  http://localhost:8081/graphql

echo
echo

# Test 1.1: Field Selection - Only name and genre
echo "📚 Field Selection - Only name and genre:"
curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "query": "query { bookById(id: \"book-1\") { name genre } }"
  }' \
  http://localhost:8081/graphql

echo
echo

# Test 1.2: Field Selection - name, genre, and author (nested selection)
echo "📚 Field Selection - name, genre, and author (nested selection):"
curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "query": "query { bookById(id: \"book-1\") { name genre author { firstName lastName } } }"
  }' \
  http://localhost:8081/graphql

echo
echo

# Test 1.3: Field Selection - Minimal fields (only name)
echo "📚 Field Selection - Minimal fields (only name):"
curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "query": "query { bookById(id: \"book-1\") { name } }"
  }' \
  http://localhost:8081/graphql

echo
echo

# Test 2: Query book-2 (The Magic Pudding)
echo "📚 Querying 'The Magic Pudding' (book-2):"
curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
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
  -H "Authorization: Bearer $TOKEN" \
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
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "query": "query GetBook($id: ID!) { bookById(id: $id) { id name pageCount author { firstName lastName } } }",
    "variables": { "id": "book-1" }
  }' \
  http://localhost:8081/graphql

echo
echo

echo "4. Testing GraphQL Pagination and Filtering..."

# Test 11: Query books with pagination
echo
echo "📖 Querying books with pagination (first 3) - Full fields:"
curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "query": "query { books(first: 3) { edges { cursor node { id name pageCount genre author { id firstName lastName } } } pageInfo { hasNextPage hasPreviousPage startCursor endCursor } totalCount } }"
  }' \
  http://localhost:8081/graphql

echo
echo

# Test 11.1: Field Selection with pagination - Only specific fields
echo "📖 Field Selection with pagination - Only name, genre, and author:"
curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "query": "query { books(first: 3) { edges { cursor node { name genre author { firstName lastName } } } pageInfo { hasNextPage } totalCount } }"
  }' \
  http://localhost:8081/graphql

echo
echo

# Test 12: Query books with cursor pagination
echo "📖 Querying books with cursor pagination:"
curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "query": "query { books(first: 2, after: \"Ym9vay0x\") { edges { cursor node { id name pageCount genre author { id firstName lastName } } } pageInfo { hasNextPage hasPreviousPage startCursor endCursor } totalCount } }"
  }' \
  http://localhost:8081/graphql

echo
echo

# Test 13: Query books with search filter
echo "🔍 Querying books with search filter (search: 'Lucky'):"
curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "query": "query { books(first: 5, search: \"Lucky\") { edges { cursor node { id name pageCount genre author { id firstName lastName } } } pageInfo { hasNextPage hasPreviousPage startCursor endCursor } totalCount } }"
  }' \
  http://localhost:8081/graphql

echo
echo

# Test 14: Query books with genre filter
echo "📚 Querying books with genre filter (genre: 'Non-Fiction'):"
curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "query": "query { books(first: 5, genre: \"Non-Fiction\") { edges { cursor node { id name pageCount genre author { id firstName lastName } } } pageInfo { hasNextPage hasPreviousPage startCursor endCursor } totalCount } }"
  }' \
  http://localhost:8081/graphql

echo
echo

# Test 15: Query books with search and genre filter
echo "🔍📚 Querying books with search and genre filter (search: 'Lucky', genre: 'Non-Fiction'):"
curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "query": "query { books(first: 5, search: \"Lucky\", genre: \"Non-Fiction\") { edges { cursor node { id name pageCount genre author { id firstName lastName } } } pageInfo { hasNextPage hasPreviousPage startCursor endCursor } totalCount } }"
  }' \
  http://localhost:8081/graphql

echo
echo

echo "6. Testing GraphQL Sorting..."

# Test 16: Query books ordered by name
echo "📚 Querying books ordered by name:"
curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "query": "query { books(first: 5, orderBy: NAME) { edges { cursor node { id name pageCount genre author { id firstName lastName } } } pageInfo { hasNextPage hasPreviousPage startCursor endCursor } totalCount } }"
  }' \
  http://localhost:8081/graphql

echo
echo

# Test 17: Query books ordered by page count
echo "📚 Querying books ordered by page count:"
curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "query": "query { books(first: 5, orderBy: PAGE_COUNT) { edges { cursor node { id name pageCount genre author { id firstName lastName } } } pageInfo { hasNextPage hasPreviousPage startCursor endCursor } totalCount } }"
  }' \
  http://localhost:8081/graphql

echo
echo

# Test 18: Query books ordered by genre
echo "📚 Querying books ordered by genre:"
curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "query": "query { books(first: 5, orderBy: GENRE) { edges { cursor node { id name pageCount genre author { id firstName lastName } } } pageInfo { hasNextPage hasPreviousPage startCursor endCursor } totalCount } }"
  }' \
  http://localhost:8081/graphql

echo
echo

# Test 19: Query books with search and ordered by name
echo "🔍📚 Querying books with search and ordered by name (search: 'Lucky', orderBy: NAME):"
curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "query": "query { books(first: 5, search: \"Lucky\", orderBy: NAME) { edges { cursor node { id name pageCount genre author { id firstName lastName } } } pageInfo { hasNextPage hasPreviousPage startCursor endCursor } totalCount } }"
  }' \
  http://localhost:8081/graphql

echo
echo

# Test 20: Query books with genre filter and ordered by page count
echo "📚 Querying books with genre filter and ordered by page count (genre: 'Non-Fiction', orderBy: PAGE_COUNT):"
curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "query": "query { books(first: 5, genre: \"Non-Fiction\", orderBy: PAGE_COUNT) { edges { cursor node { id name pageCount genre author { id firstName lastName } } } pageInfo { hasNextPage hasPreviousPage startCursor endCursor } totalCount } }"
  }' \
  http://localhost:8081/graphql

echo
echo

# Test 21: Query books with search, genre filter, and ordered by genre
echo "🔍📚 Querying books with search, genre filter, and ordered by genre (search: 'Lucky', genre: 'Non-Fiction', orderBy: GENRE):"
curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "query": "query { books(first: 5, search: \"Lucky\", genre: \"Non-Fiction\", orderBy: GENRE) { edges { cursor node { id name pageCount genre author { id firstName lastName } } } pageInfo { hasNextPage hasPreviousPage startCursor endCursor } totalCount } }"
  }' \
  http://localhost:8081/graphql

echo
echo

echo "7. Testing GraphQL Mutations..."

# Test 5: Create a new author
echo
echo "👤 Creating a new author:"
curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
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
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "query": "mutation { createBook(input: { name: \"Pride and Prejudice\", pageCount: 432, authorId: \"author-1\", genre: \"Romance\" }) { id name pageCount genre author { id firstName lastName } } }"
  }' \
  http://localhost:8081/graphql

echo
echo

# Test 7: Update a book
echo "✏️ Updating book-1:"
curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "query": "mutation { updateBook(id: \"book-1\", input: { name: \"The Lucky Country - Updated Edition\", pageCount: 320, genre: \"History\" }) { id name pageCount genre author { id firstName lastName } } }"
  }' \
  http://localhost:8081/graphql

echo
echo

# Test 8: Delete a book
echo "🗑️ Deleting book-2:"
curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
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
  -H "Authorization: Bearer $TOKEN" \
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
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "query": "query { bookById(id: \"book-1\") { id name pageCount genre author { id firstName lastName } } }"
  }' \
  http://localhost:8081/graphql

echo
echo

echo "=== Demo completed ==="
echo
echo "💡 You can also access the interactive GraphiQL interface at:"
echo "   http://localhost:8081/graphiql"
echo
echo "🔐 Authentication:"
echo "   - Default user: 313@acu.com"
echo "   - Default password: 123456"
echo "   - Role: ADMIN (can perform all operations)"
echo
echo "📖 Available books:"
echo "   - book-1: The Lucky Country by Donald Horne"
echo "   - book-2: The Magic Pudding by Norman Lindsay (deleted in demo)"
echo
echo "🔄 Features demonstrated:"
echo "   - Authentication: JWT-based login"
echo "   - Authorization: Role-based access control"
echo "   - Field Selection: Request only needed fields (name, genre, author)"
echo "   - bookById: Query individual books with field selection"
echo "   - books: Paginated book queries with cursor-based pagination"
echo "   - books: Search filtering by book name"
echo "   - books: Genre filtering"
echo "   - books: Combined search and genre filtering"
echo "   - books: Sorting by name, page count, and genre"
echo "   - books: Combined filtering and sorting"
echo "   - createAuthor: Add new authors (ADMIN only)"
echo "   - createBook: Add new books (ADMIN only)"
echo "   - updateBook: Modify existing books (ADMIN only)"
echo "   - deleteBook: Remove books (ADMIN only)"
