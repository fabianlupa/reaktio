#!/usr/bin/env bash

# Include bash-tools
source $(dirname $0)/shtools.sh

# Deploy Android project
doGradleCmd "./gradlew android:assembleRelease"
mv android/build/outputs/apk/android-release-unsigned.apk android/build/outputs/apk/reaktio-$TRAVIS_TAG.apk
# TODO: Sign APK
