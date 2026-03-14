# .NET and C# Version History

## Overview

Microsoft releases new versions of .NET and C# together every year in November. Each .NET version comes with a corresponding C# language version that includes new features and improvements.

## Understanding the Relationship

- **.NET** is the platform/framework that runs your applications
- **C#** is the programming language you write your code in
- Each .NET version includes a specific C# version

## Recent Version Timeline

| .NET Version | C# Version | Release Date | Support Type |
|-------------|-----------|--------------|--------------|
| .NET 10 | C# 14 | November 2025 | STS (18 months) |
| .NET 9 | C# 13 | November 2024 | STS (18 months) |
| .NET 8 | C# 12 | November 2023 | LTS (3 years) |
| .NET 7 | C# 11 | November 2022 | STS (18 months) |
| .NET 6 | C# 10 | November 2021 | LTS (3 years) |

## Support Types Explained

### LTS (Long Term Support)
- Supported for **3 years**
- Recommended for production applications
- More stable and mature
- Released in **even-numbered** years (.NET 6, 8, 10, etc.)

### STS (Standard Term Support)
- Supported for **18 months**
- Gets latest features first
- Good for learning and experimenting
- Released in **odd-numbered** years (.NET 7, 9, 11, etc.)

## For ITEC323 Students

### Current Recommendation
- **Primary**: .NET 10 - Current course standard
- **Language**: C# 14

### Why This Matters
When you see code examples or tutorials online, check which .NET version they target. The core concepts remain the same, but newer versions may have additional features or slightly different syntax.

## Checking Your Version

To check which .NET version you have installed:

```bash
dotnet --version
```

To see all installed versions:

```bash
dotnet --list-sdks
```

## Key Takeaways

1. **Annual Release**: New .NET and C# versions come out every November
2. **Synchronized**: The version numbers track together (.NET 10 = C# 14, .NET 9 = C# 13)
3. **Choose LTS for stability**: If in doubt, use the latest LTS version
4. **Backward compatible**: Code written for older versions generally works in newer versions

## Learning Resources

- [Official .NET Documentation](https://learn.microsoft.com/en-us/dotnet/)
- [What's New in C# (15)](https://learn.microsoft.com/en-us/dotnet/csharp/whats-new/)
- [.NET Support Policy](https://dotnet.microsoft.com/platform/support/policy)

---

**Note**: This course uses .NET 10.0 with C# 14 as the standard for all current examples.
