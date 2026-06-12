.PHONY: build build-dotnet build-android clean clean-dotnet clean-android clean-generated

DOTNET ?= /usr/local/share/dotnet/dotnet

# Extra .NET paths cleaned by `make clean` but not built by `make build-dotnet`.
DOTNET_CLEAN_EXTRA = \
	31-Playwright-Testing/01.RazorPagesPlaywrightDemo/01.RazorPagesPlaywrightDemo.csproj \
	31-Playwright-Testing/01.RazorPagesPlaywrightDemo/tests/01.RazorPagesPlaywrightDemo.Tests/01.RazorPagesPlaywrightDemo.Tests.csproj \
	40-Advanced-EFCore/40-Advanced-EFCore.sln

DOTNET_TARGETS = \
	01.HelloDotnet/01.HelloDotnet.sln \
	02.HTML-CSS \
	03.CSharp-Razorpages \
	04.ConsistentWebDesign \
	05.NavigationRouting \
	06.ReusableComponentsValidation \
	07.AjaxDynamicContent \
	08.ClientSideInteractivity \
	09.DataPersistenceEFCore \
	12.MauiCrossPlatform \
	22-MinimalAPIs-REST \
	22-Unit-Testing \
	24-Identity-JWT-Auth/24-Identity-JWT-Auth.slnx \
	32-AI-Integration \
	33-SPA-Integration \
	34-React-SPA-Integration \
	50-CICD-GitHubActions/50-CICD-GitHubActions.sln

ANDROID_PROJECTS = \
	10.AndroidKotlinJetpackCompose \
	11.AndroidGreetingApps

build: build-dotnet build-android

build-dotnet:
	@for target in $(DOTNET_TARGETS); do \
		echo "Building $$target with dotnet build..."; \
		"$(DOTNET)" build "$$target" || exit $$?; \
	done

build-android:
	@for project in $(ANDROID_PROJECTS); do \
		echo "Building $$project with build-projects.sh..."; \
		bash "./$$project/build-projects.sh" || exit $$?; \
	done

clean: clean-dotnet clean-android clean-generated

clean-dotnet:
	@for target in $(DOTNET_TARGETS) $(DOTNET_CLEAN_EXTRA); do \
		echo "Cleaning $$target with dotnet clean..."; \
		"$(DOTNET)" clean "$$target" || exit $$?; \
	done

clean-android:
	@for project in $(ANDROID_PROJECTS); do \
		echo "Cleaning $$project with cleanup-builds.sh..."; \
		bash "./$$project/cleanup-builds.sh" || exit $$?; \
	done

# Removes generated artifacts only (source, docs, and media assets such as GIFs are kept).
clean-generated:
	@echo "Removing generated folders (node_modules, bin, obj, dist, .angular, .playwright, .gradle, build, artifacts, TestResults)..."
	@find . -type d \( \
		-name node_modules -o \
		-name bin -o \
		-name obj -o \
		-name dist -o \
		-name .angular -o \
		-name .vite -o \
		-name .cache -o \
		-name .playwright -o \
		-name .gradle -o \
		-name build -o \
		-name artifacts -o \
		-name TestResults \
	\) -prune -print0 | xargs -0 rm -rf
