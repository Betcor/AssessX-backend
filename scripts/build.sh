#!/bin/bash

JAVA_PATH=$(dirname $(dirname $(readlink -f $(which java))))

echo "Java: $JAVA_PATH"

if ! grep -q "JAVA_HOME" ~/.zshrc; then
    echo "export JAVA_HOME=$JAVA_PATH" >> ~/.zshrc
    echo "JAVA_HOME added в ~/.zshrc"
else
    echo "JAVA_HOME finded ~/.zshrc"
fi

export JAVA_HOME=$JAVA_PATH

echo "Building..."
./mvnw clean package -DskipTests