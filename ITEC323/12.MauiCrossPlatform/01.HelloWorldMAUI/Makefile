.PHONY: help build build-web build-native build-maccatalyst build-android build-ios build-windows run-web run-maccatalyst run-android run-ios clean restore

SOLUTION := HelloWorldMAUI.Starter.slnx
MAUI_PROJECT := HelloWorldMAUI.csproj
WEB_PROJECT := HelloWorldMAUI.Web/HelloWorldMAUI.Web.csproj

help:
	@printf "Available targets:\n"
	@printf "  make restore           Restore all projects in the starter solution\n"
	@printf "  make build             Build the shared library, web host, and a native host for this OS\n"
	@printf "  make build-web         Build the Blazor web host\n"
	@printf "  make build-native      Build the default native host for this OS\n"
	@printf "  make build-maccatalyst Build the macOS native host\n"
	@printf "  make build-android     Build the Android native host\n"
	@printf "  make build-ios         Build the iOS native host\n"
	@printf "  make build-windows     Build the Windows native host (Windows only)\n"
	@printf "  make run-web           Run the Blazor web host\n"
	@printf "  make run-maccatalyst   Run the macOS native host\n"
	@printf "  make run-android       Run the Android native host\n"
	@printf "  make run-ios           Run the iOS native host on the booted simulator\n"
	@printf "  make clean             Clean the MAUI and web projects\n"

restore:
	dotnet restore $(SOLUTION)

build: build-web build-native

build-web:
	dotnet build $(WEB_PROJECT) -v minimal

build-native:
	@if [ "$$(uname -s)" = "Darwin" ]; then \
		$(MAKE) build-maccatalyst; \
	elif [ "$$(uname -s)" = "Linux" ]; then \
		echo "No default native host target is configured for Linux."; \
		exit 1; \
	else \
		$(MAKE) build-windows; \
	fi

build-maccatalyst:
	dotnet build $(MAUI_PROJECT) -f net10.0-maccatalyst -v minimal

build-android:
	dotnet build $(MAUI_PROJECT) -f net10.0-android -v minimal

build-ios:
	dotnet build $(MAUI_PROJECT) -f net10.0-ios -v minimal

build-windows:
	dotnet build $(MAUI_PROJECT) -f net10.0-windows10.0.19041.0 -v minimal

run-web:
	dotnet run --project $(WEB_PROJECT)

run-maccatalyst:
	dotnet build $(MAUI_PROJECT) -t:Run -f net10.0-maccatalyst

run-android:
	dotnet build $(MAUI_PROJECT) -t:Run -f net10.0-android

run-ios:
	dotnet build $(MAUI_PROJECT) -t:Run -f net10.0-ios -p:_DeviceName=:v2:udid=$$(xcrun simctl list devices booted | awk -F '[()]' '/Booted/{print $$2; exit}')

clean:
	dotnet clean $(MAUI_PROJECT)
	dotnet clean $(WEB_PROJECT)
