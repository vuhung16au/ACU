FROM node:22-alpine
WORKDIR /app
COPY package.json package-lock.json* pnpm-lock.yaml* yarn.lock* ./
RUN npm install --no-fund --no-audit || true
COPY . .
EXPOSE 3000
CMD ["npm", "run", "dev"]


