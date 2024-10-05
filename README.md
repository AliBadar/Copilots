# Goally
Copilots App - App consuming a [Copilots Api](https://devapi.getgoally.com/v1/api/devices/copilot-list/) to display the List of Copilots and its details with the Activities.


**App features:**
- Get Copilots Listing
- Show the Details and Activities of Copilots
- Saved Copilots in Local Database using Room


## Architecture
Uses concepts of the notorious architecture called [MVVM](https://www.geeksforgeeks.org/mvvm-model-view-viewmodel-architecture-pattern-in-android/).</br>

* Better separation of concerns. Each module has a clear API., Feature related classes life in different modules and can't be referenced without explicit module dependency.
* Features can be developed in parallel eg. by different teams
* Built with Modern Android Development practices
* Utilized ViewModels, Repository pattern for data
* Includes unit tests for Room Database, Repository, ViewModels, API Service response.
* Architecture is composed of different layers; the model, view, view model.


## Tech stack - Library:
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  JetPack
    - [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle) - Used get lifecyle event of an activity or fragment and performs some action in response to change
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
    - [Room](https://developer.android.com/topic/libraries/architecture/room) - Used to create room db and store the data.
    - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components like ripple animation, cardView.
- [Dependency Injection](https://developer.android.com/training/dependency-injection)
- [Hilt](https://dagger.dev/hilt) - Easier way to incorporate Dagger DI into Android apps.
- [Retrofit](https://github.com/square/retrofit) - Used for REST api communication.
- [OkHttp](http://square.github.io/okhttp/) - HTTP client that's efficient by default: HTTP/2 support allows all requests to the same host to share a socket
- [MockK](https://mockk.io) - For Mocking and Unit Testing


## Known Issue :
* There was in issue in the Project Provided,  Only Unit Test Cases was running Properly, UI and AndroidTest was not running, there is an issue in the project. I have written the Room DB test cases as well which I have commented, so it was not running on this project provided.