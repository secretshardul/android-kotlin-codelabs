# Google CodeLabs: Android Kotlin Fundamental course

## How to use
Explanation given in comment format. Follow repos in order.
1. (HelloWorld)[/HelloWorld]: Dice roll app
    1. Android app anatomy
        1. Lifecycle methods
        2. Layout inflation, View classes and objects
        3. File structure: activities, layouts, R file, Android manifest
        4. Listeners and callback methods
        5. Layouts: Linear layout, orientation, layout gravity
        
    2. `lateinit`, adding images, compatibility
        1. ```lateinit var```: Minimize the calls to `findViewById()`. Allows global view references and late initialization while removing need of null checks.
        2. ```tools:src```: Add placeholder image in layout file which is only visible in IDE. Its not added into app on compilation. Its helpful for development.
        3. Android SDK versions in `build.gradle(:app)`:
            - **compileSdkVersion**: Version used to compile app. It allows app to use new features.
            - **targetSdkVersion**: Most recent API you have tested your app against.
            - **minSdkVersion**: Minimum level to support.

        4. Backward compatibility
            **androidx** provides helper classes to ensure backward compatibility. It's an improvement over Android support library.
            
            Eg. to display vector images in older android versions instead of converting them to bitmap, use `vectorDrawables.useSupportLibrary`. In `<ImageView>` replace `android:src` with `app:srcCompat`.
        
        5. `app` namespace: Used in XML files for custom functionality not provided by core android framework. Eg. for vector drawables `app:srcCompat` was used.

2. [MyBasicActivity](/MyBasicActivity): Using and importing activity templates.
    > https://codelabs.developers.google.com/codelabs/kotlin-android-training-available-resources

3. [Sunflower](/Sunflower): Change app icon.
    > https://codelabs.developers.google.com/codelabs/kotlin-android-training-available-resources

