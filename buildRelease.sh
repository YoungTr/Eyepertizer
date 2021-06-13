#!/bin/bash
echo "------------------start build------------------"
./gradlew clean
./gradlew :app:assembleRelease
echo "------------------build finish------------------"