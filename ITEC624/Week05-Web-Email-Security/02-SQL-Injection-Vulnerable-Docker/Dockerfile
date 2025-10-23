FROM node:20-alpine

WORKDIR /app

# Install system dependencies for sqlite3
RUN apk add --no-cache python3 make g++

COPY package.json package-lock.json* ./
RUN npm ci --omit=dev || npm install --omit=dev

COPY . .

EXPOSE 3000
CMD ["npm", "start"]
