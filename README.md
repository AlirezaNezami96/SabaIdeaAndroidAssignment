# Movie list app with Filimo API

Created a simple application using **Filimo** public API.

## Stacks

- Application has built with pure **Kotlin** and UI elements and components are all built via **Jetpack compose**.
- For http requests, **Retrofit** and **OkHttp** has been used. Also for **Logging interceptor** has been added to log the output.
- For Dependency Injection **Dagger/Hilt** has been implemented.
- To convert and deserialize the JSON objects, I've used **Moshi**.
- To manage the background threads and communicating to UI thread, **Kotlin coroutines** and **Kotlin Flows** has been used.
- **Coil** has been used for loading web images.

## Clean Architecture

To improve code readability and maintainability and for good **Unit tests** and **Integration tests**, Clean architecture would be the grate choose in most common applications.  
By using ViewModel as the data persistence class and some compose helper classes like **remember**, all the data will be persist during configuration changes e.g. Screen rotation.

## To Build and Run

- Kotlin version 1.5.31
- Gradle 7.3.3
- Android studio Chipmunk 2021.2.1 Patch 1
- minSDK 23
  The output debug APK file is available here: https://github.com/AlirezaNezami96/SabaIdeaAndroidAssignment/blob/main/app/app-debug.apk

**By the way, your API JSON output really sucks.** 
