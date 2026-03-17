.PHONY: build build-dotnet build-android clean clean-dotnet clean-android

DOTNET ?= /usr/local/share/dotnet/dotnet

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
	12.MauiCrossPlatform

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

clean: clean-dotnet clean-android

clean-dotnet:
	@for target in $(DOTNET_TARGETS); do \
		echo "Cleaning $$target with dotnet clean..."; \
		"$(DOTNET)" clean "$$target" || exit $$?; \
	done

clean-android:
	@for project in $(ANDROID_PROJECTS); do \
		echo "Cleaning $$project with cleanup-builds.sh..."; \
		bash "./$$project/cleanup-builds.sh" || exit $$?; \
	done
