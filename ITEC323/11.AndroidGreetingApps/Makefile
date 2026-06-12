.PHONY: help clean build

# Keep the folder-level commands simple for students.
PROJECT_ROOTS := \
	01.AndroidGreetingCards \
	02.AndroidGreetingCards-Gemini \
	03.AndroidStudio-ProjectTypes

help:
	@echo "Available targets:"
	@echo "  make clean   - Run Gradle clean for all Android projects in this folder"
	@echo "  make build   - Run Gradle build for all Android projects in this folder"

clean:
	@set -e; \
	java_17_home=$$(/usr/libexec/java_home -v 17 2>/dev/null || true); \
	if [ -n "$$java_17_home" ]; then \
		export JAVA_HOME="$$java_17_home"; \
		export PATH="$$JAVA_HOME/bin:$$PATH"; \
		echo "Using JAVA_HOME: $$JAVA_HOME"; \
	fi; \
	for root in $(PROJECT_ROOTS); do \
		find "$$root" -name gradlew -type f | while read -r gradlew; do \
			project_dir=$$(dirname "$$gradlew"); \
			echo "Cleaning $$project_dir"; \
			(cd "$$project_dir" && ./gradlew clean --quiet); \
		done; \
	done; \
	echo "Clean complete for all Android projects."

build:
	@set -e; \
	java_17_home=$$(/usr/libexec/java_home -v 17 2>/dev/null || true); \
	if [ -n "$$java_17_home" ]; then \
		export JAVA_HOME="$$java_17_home"; \
		export PATH="$$JAVA_HOME/bin:$$PATH"; \
		echo "Using JAVA_HOME: $$JAVA_HOME"; \
	fi; \
	for root in $(PROJECT_ROOTS); do \
		find "$$root" -name gradlew -type f | while read -r gradlew; do \
			project_dir=$$(dirname "$$gradlew"); \
			echo "Building $$project_dir"; \
			(cd "$$project_dir" && ./gradlew build --quiet); \
		done; \
	done; \
	echo "Build complete for all Android projects."