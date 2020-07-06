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
            **androidx** provides helper classes to ensure backward compatibility. It's an improvement over Android support library/