# Build stage: compile and publish the Razor Pages app
FROM mcr.microsoft.com/dotnet/sdk:10.0 AS build
WORKDIR /src

COPY 07.HelloWorldDocker.csproj ./
RUN dotnet restore

COPY . ./
RUN dotnet publish -c Release -o /app/publish /p:UseAppHost=false

# Runtime stage: run the published app with the ASP.NET runtime image
FROM mcr.microsoft.com/dotnet/aspnet:10.0 AS runtime
WORKDIR /app

COPY --from=build /app/publish .

ENV ASPNETCORE_URLS=http://+:8080
EXPOSE 8080

ENTRYPOINT ["dotnet", "07.HelloWorldDocker.dll"]
