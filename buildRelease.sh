#!/bin/bash
echo "------------------start build------------------"
./gradlew clean
./gradlew :app:assembleRelease
echo "------------------build finish------------------"
#cp -f app/build/outputs/apk/release/eyepertizer_1.1.0_release.apk release/