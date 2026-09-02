#! /bin/bash

echo "$(curl -X POST -H "Content-Type: application/json" -d @./add-user.json http://localhost:8080/account/add-user)"
echo "$(curl -X POST -H "Content-Type: application/json" -d @./generate-token.json http://localhost:8080/account/generate-token)"
echo "$(curl -X POST -H "Content-Type: application/json" -d @./generate-token-wrong.json http://localhost:8080/account/generate-token)"