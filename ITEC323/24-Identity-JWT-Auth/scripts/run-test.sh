#!/bin/bash
cd /Users/vuhung/00.Work/02.ACU/github/ITEC323/24-Identity-JWT-Auth/01.JwtAuthAPI
dotnet bin/Debug/net10.0/01.JwtAuthAPI.dll --urls "http://localhost:5000" > api.log 2>&1 &
API_PID=$!
echo "Started API with PID $API_PID"
sleep 5
cd ../scripts
echo "Running playwright"
node playwright-test.js
kill -9 $API_PID
echo "Done"
