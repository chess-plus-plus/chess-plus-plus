![](readme-logo.png)

# Chess++

Chess++ is an expansion on the classic game of chess implemented using LibGDX and Google Firebase.

## Structure

This Repo is structured as a standard LibGDX application managed by Gradle. In addition to the top-level build and gradle files there are 3 modules. 
The "Android" module is mainly used to launch the application and contains a few classes that deal with Firebase integration.
The "Core" module contains all of the remaining code, including the frontend and the game engine.

## How to install and run the application

1. Download the ChessPlusPlus.apk binary from the latest relase on our [Releases Page](https://github.com/chess-plus-plus/chess-plus-plus/releases). 
2. Install the APK on your android device of choice, you may have to enable the "Install unknown apps" or similar settings on your phone.
3. Launch the app.

## How to compile the application in Android Studio

After importing the project and performing a gradle sync, select Build -> Build Bundle(s) / APK(s) -> Build APK(s) from the menu.


NB: 
- To compile the project locally you will need a configured "google-services.json" manifest placed in the top level of the android module. 
- This is not included on the Github page for security reasons.
