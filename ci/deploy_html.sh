#!/usr/bin/env bash

# Include bash-tools
source $(dirname $0)/shtools.sh

# Deploy HTML project
doGradleCmd "./gradlew html:dist"
zip -r reaktio-$TRAVIS_TAG.zip ./html/build/dist/
