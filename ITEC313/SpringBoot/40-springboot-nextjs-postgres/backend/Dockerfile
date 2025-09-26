FROM gradle:8.10.2-jdk17
WORKDIR /app
COPY . /app
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
EXPOSE 8080
CMD ["gradle", "--no-daemon", "bootRun"]


