#!/bin/bash
echo "------------------start build------------------"
./gradlew clean
./gradlew :app:assembleDebug
echo "------------------build finish------------------"
cp -f build/outputs/apk/debug/eyepertizer_1.1.0_debug.apk debug/