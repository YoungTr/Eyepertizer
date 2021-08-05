#!/bin/bash
echo "------------------start build------------------"
./gradlew :app:assembleDebug
echo "------------------build finish------------------"
echo "------------------start install------------------"
adb -s 344621240204 install -r ./app/build/outputs/apk/debug/eyepertizer_1.1.1_debug.apk
echo "------------------start app------------------"
adb -s 344621240204 shell am start -n com.eyepertizer.androidx/com.eyepertizer.androidx.ui.splash.view.SplashActivity
