#!/usr/bin/env bash

# Include bash-tools
source $(dirname $0)/shtools.sh

# Build HTML project
doGradleCmd "./gradlew html:build"
