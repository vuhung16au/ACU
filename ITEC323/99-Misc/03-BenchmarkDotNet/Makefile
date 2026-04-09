SHELL := /bin/bash

PROJECT := benchmark/BenchmarkDotNetSortComparer.csproj
FRAMEWORK := net10.0

.PHONY: clean build run benchmark dataset

clean:
	dotnet clean $(PROJECT)
	rm -rf benchmark/bin benchmark/obj
	rm -rf artifacts/benchmarkdotnet artifacts/datasets
	rm -rf reports/*.html

build:
	dotnet build $(PROJECT) -c Release

dataset:
	dotnet run --project $(PROJECT) -c Release -f $(FRAMEWORK) -- dataset

run:
	dotnet run --project $(PROJECT) -c Release -f $(FRAMEWORK) -- run

benchmark:
	dotnet run --project $(PROJECT) -c Release -f $(FRAMEWORK) -- benchmark
