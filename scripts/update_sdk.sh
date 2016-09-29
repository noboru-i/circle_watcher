BTOOLS=/usr/local/android-sdk-linux/build-tools/24.0.2

if [ ! -e BTOOLS ]; then
echo "Cache invalidated updating sdk"
echo y | android update sdk --no-ui --all --filter "tools,extra-android-support"
echo y | android update sdk --no-ui --all --filter "android-24,build-tools-24.0.2,extra-android-m2repository,extra-google-m2repository"
fi
echo "Cache restore complete, sdk updated"