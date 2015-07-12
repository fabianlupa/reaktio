#!/usr/bin/env bash

# Include bash-tools
source $(dirname $0)/shtools.sh

# Build IOS project
doGradleCmd "./gradlew ios:build"
