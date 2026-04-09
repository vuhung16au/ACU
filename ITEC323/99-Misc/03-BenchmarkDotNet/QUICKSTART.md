# QUICKSTART

## Prerequisites

- .NET SDK 9.x and 10.x installed
- macOS/Linux shell with `make`

Verify SDKs:

```bash
dotnet --list-sdks
```

## 1. Build

```bash
cd 99-Misc/03-BenchmarkDotNet
make build
```

## 2. Generate datasets

```bash
make dataset
```

This creates default files under `artifacts/datasets/`:

- `random_1000.txt`
- `random_3000.txt`
- `random_5000.txt`

## 3. Validate algorithms

```bash
make run
```

This checks that all sorting algorithms return a sorted result on the generated data.

## 4. Run benchmark comparison

```bash
make benchmark
```

Outputs:

- BenchmarkDotNet artifacts in `artifacts/benchmarkdotnet/`
- HTML report in `reports/report-<timestamp>.html`
- Updated key points in `docs/Key-Takeaways.md`

## 5. Clean generated files

```bash
make clean
```
