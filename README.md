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

      1. `lateinit var`: Minimize the calls to `findViewById()`. Allows global view references and late initialization while removing need of null checks.
      2. `tools:src`: Add placeholder image in layout file which is only visible in IDE. Its not added into app on compilation. Its helpful for development.
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

4. [AboutMe](/AboutMe)

   1. Linear layout, ViewGroup, styles

      > https://codelabs.developers.google.com/codelabs/kotlin-android-training-linear-layout

      1. **ViewGroup**: A view that can hold other views, eg. `LinearLayout`, `ScrollView`.
      2. **Styles**: Font, text size, padding and other elements in layout files can be grouped as styles.

         ```xml
            <TextView
                android:id="@+id/name_text"
                android:fontFamily="@font/roboto"
                android:textSize="@dimen/text_size"
                android:textColor="@android:color/black"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="@string/name"
                android:textAlignment="center" />
         ```

         To create style right click `<TextView`, then **refactor>style**.

         ```xml
            <TextView
                android:id="@+id/name_text"
                style="@style/NameStyle"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="@string/name"
                android:textAlignment="center" />
         ```

   2. `visibility` attribute for hiding views
      - **visible**
      - **invisible**: Hides view, but it still takes up space.
      - **gone**: Hides view, and view does not take up any space.
        > https://codelabs.developers.google.com/codelabs/kotlin-android-training-interactivity

   3. **Data binding**: It is a more efficient alternative to `findViewById()`. It eliminates the need to traverse the view hierarchy multiple times. In data binding a common `Binding` holds reference to each view. This single object is used to access views.
      > https://codelabs.developers.google.com/codelabs/kotlin-android-training-data-binding-basics

      ![](images/2020-07-08-13-30-16.png)

    Data binding uses:
        1. Eliminate `findViewById()`
        2. Data binding to display data: Data is used to directly make a **data class** available to a view. Here `MyName` data class is used to provide name and nickname to `activity_main.xml`. Special `<data>`, `<variable>` and `@{}` syntax is used to pass a data class to the layout.
    
5. [ColorMyViews](/ColorMyViews): `ContraintLayout`

    > https://codelabs.developers.google.com/codelabs/kotlin-android-training-constraint-layout

   1. **Constraint layout widget**: Can be used to set margins, bias and constraint type.

      ![](images/2020-07-07-13-08-55.png)

      - Margins: Drop down menu.
      - Bias: Sliders at left and bottom side.
      - Constraint type:

        1. ![](images/2020-07-07-13-10-48.png) **Wrap content**: Expand only as much needed to fill contents.

        2. ![](images/2020-07-07-13-15-41.png) **Fixed**: Fixed distance based on specified value.

        3. ![](images/2020-07-07-13-13-53.png) **Match constraints**: View expands as much as possible to meet the constraints on each side.

   2. **Chains**: A chain is a group of views linked together by bidirectional constraints. Chains can be horizontal or vertical. The leftmost view in horizontal chain and topmost view in vertical chain is called the **head**.

       ![](images/2020-07-07-17-13-05.png)
    
       Chains have 3 styles which can be set using `layout_constraintHorizontal_chainStyle` or `layout_constraintVertical_chainStyle` parameter in the head view.
    
       1. **Spread**(default): Views are evenly spread in the available space, after margins are accounted for.
          ![](images/2020-07-07-17-16-29.png)
    
       2. **Spread inside**: First and the last views are attached to the parent on each end of the chain. The rest are evenly spread.
          ![](images/2020-07-07-17-16-49.png)
    
       3. **Packed**: Views are packed together, after margins are accounted for.
          ![](images/2020-07-07-17-17-03.png)

   3. **Design time attributes**: They're only displayed during layout design and are ignored at runtime. They're helpful for designing. They begin with `tools` namespace.
   
   4. **Baseline constraints**: Aligns baseline of one view's text with baseline of another view's text. This means text is always aligned, even when font sizes are different. This is done using `app:layout_constraintBaseline_toBaselineOf` attribute. To do this from design menu, right click the view and click on **show baseline**.
   
6. (AndroidTrivia-Starter)[/AndroidTrivia-Starter]: Fragments
    1. **Fragment**: A fragment is a sub-activity which can be reused in other activities. It has its own lifecycle events, layout file and kotlin class. Fragments can be added or removed when an activity is removed. Fragments are comparable to react components.
        > https://codelabs.developers.google.com/codelabs/kotlin-android-training-create-and-add-fragmen
    
        To inflate fragment, add binding to fragment's `onCreateView()`:
        
        ```kotlin
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            // Inflate the layout for this fragment
            val binding = DataBindingUtil.inflate<FragmentTitleBinding>(inflater, R.layout.fragment_title, container, false)
            return binding.root
        }
        ```
    2. **Navigation**: Android navigator is a standard library to navigate between fragments.
        1. Import navigation library from Gradle(see committed code).
        2. Right click res > new > Android resource file > navigation. Navigation folder is generated which contains navigation xml file. It provides a GUI to setup navigation between different fragments.
        3. **Navigation host fragment**: It acts as host to navigate between fragments, swapping them in and out as necessary. Add `NavHostFragment` to `activity_main.xml`. `app:defaultNavHost` true sets it as default host, allowing it to intercept back button.
            
            ```xml
           <fragment
              android:id="@+id/myNavHostFragment"
              android:name="androidx.navigation.fragment.NavHostFragment"
              android:layout_width="match_parent"
              android:layout_height="match_parent"
              app:navGraph="@navigation/navigation"
              app:defaultNavHost="true"/>
           ```
        4. **Back stack**: Each time a user navigates to a new destination, the destination is added to the back stack. When back button is pressed, the topmost destination is popped and the user is taken there. Pop behavior can be modified in 2 ways:
            1. `popUpTo`: Pops up the stack up to the specified destination. This is exclusive of the specified destination.
            2. `popUpToInclusive`: If true, the specified destination is also popped. If this is set to app's starting location, then user is taken out of app if back button is pressed.
                ```xml
                <fragment
                   android:id="@+id/gameFragment"
                   android:name="com.example.android.navigation.GameFragment"
                   android:label="GameFragment"
                   tools:layout="@layout/fragment_game" >
                   <action
                       android:id="@+id/action_gameFragment_to_gameOverFragment"
                       app:destination="@id/gameOverFragment"
                       app:popUpTo="@id/titleFragment"
                       app:popUpToInclusive="true" />
                </fragment>
                ```
            
             `popUpTo` can be used to edit back button behavior
                1. Take user to title fragment and not game fragment when back button is pressed from game won / game over fragment.
                2. When user selects play again but then presses back button, he should be taken to title screen and not game over fragment.
        
        5. **Up button navigation**:
            - Up button is the `<-` button present in top app bar/action bar. Navigation wise it's similar to bottom back button but never takes the user out of the app.
            - It's implemented using `NavigationUI` library. Call `NavigationUI.setupActionBarWithNavController` and pass `navController` as a parameter. `navController` object is obtained from navigation host fragment. Finally listen to the up button and navigate behind by overriding `onSupportNavigationUp()` lifecycle function.
            
                ```kotlin
              override fun onSupportNavigateUp(): Boolean {
                      return navController.navigateUp()
                  }  
              ```
              
        6. **Options menu**: It is the 3 dot menu on right side of the app bar. It opens a drop down menu. It's generally used to navigate to help or about screen.
            1. Add `aboutFragment` to navigator.
            2. Right click res > new resource > menu. Name it `options_menu`.
            3. Open `options_menu` in design view and add new `menu item`. Name it as `aboutFragment`.
            4. In [`TitleFragment.kt`](AndroidTrivia-Starter/app/src/main/java/com/example/android/navigation/TitleFragment.kt
) Enable options menu, override lifecycle functions for menu creation and button click.

        7. **Navigation drawer**: Opened using the hamburger icon / nav drawer icon.
            1. Import material library to access nav drawer. Add to app level `build.gradle`.
               ```
               implementation "com.google.android.material:material:$supportlibVersion"
               ```
            2. Add `RulesFragment` to navigator.
            3. Right click res > new resource > menu. Name it `navdrawer_menu`. This is a regular menu like from previous step which has 2 menu items- `aboutFragment` and `rulesFragment`. Display icons by passing them as parameters.
            4. Add `DrawerLayout` and `NavigationView` to [`activity_main.xml`](AndroidTrivia-Starter/app/src/main/res/layout/activity_main.xml).
            5. In [`MainActivity.kt`](AndroidTrivia-Starter/app/src/main/java/com/example/android/navigation/MainActivity.kt) connect drawer to navigation controller. Pass navigation view as parameter to `NavigationUI` in `onSupportNavigateUp()` to navigate user to selected fragment.

    3. **Pass data between fragments**
        > https://codelabs.developers.google.com/codelabs/kotlin-android-training-start-external-activity
        
        1. Add safe args plugin to Gradle. This adds type safety when passing data between fragments or activities. It is a better alternative to passing bundles. This generates `GameFragmentDirections` class.
        2. For navigation, action ID can be replaced with a directions class method.
            ```kotlin
            view.findNavController().navigate((GameFragmentDirections.actionGameFragmentToGameOverFragment()))
            ```
        3. To pass type safe parameters, open `navigation.xml` in design view. Open `gameWonFragment` attributes box and add required arguments.
            ```xml
            <fragment
                android:id="@+id/gameWonFragment"
                android:name="com.example.android.navigation.GameWonFragment"
                android:label="fragment_game_won"
                tools:layout="@layout/fragment_game_won" >
                <action
                    android:id="@+id/action_gameWonFragment_to_gameFragment"
                    app:destination="@id/gameFragment"
                    app:popUpTo="@id/titleFragment" />
                <!--Mandatory arguments-->
                <argument
                    android:name="numQuestions"
                    app:argType="integer" />
                <argument
                    android:name="numCorrect"
                    app:argType="integer" />
            </fragment>
            ```
        4. Pass `numQuestions` and `numCorrect` arguments when navigating to `gameWonFragment` from `gameFragment`.
            ```kotlin
           view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(numQuestions, questionIndex))
            ```
        5. Receive the arguments
            ```kotlin
            val args = GameWonFragmentArgs.fromBundle(arguments!!)
            Toast.makeText(context, "numQuestions: ${args.numQuestions}, numCorrect: ${args.numCorrect}", Toast.LENGTH_SHORT).show()
            ```
