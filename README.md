### Weather App

*Summary*

A simple weather app that gets your location and displays the forecast for the current day and a few days after that.

*API :* [OpenMeteo](https://open-meteo.com/)

# Pre-requisite 📝

```kotlin
// None
```

# Design/Architectural decisions 📐

The project makes use of common android patterns in modern android codebases.

**Project Structure**

The folders are split into 4 boundaries:
- **Core**:

  Contains the models/data classes that are independent of any framework specific dependencies and represent the business logic.
  In a Clean Arch world you can consider these as your domain classes and interfaces.

- **Data**:

  Contains data sources , local or remote, this is where the implementation for such is kept. All data related actions and formatting happens in this layer as well.
  It may contain framework related dependencies to orchestrate and create instances of data stores like a database or shared preference etc.
  One common pattern used in this area is the repository pattern, which mediates data sources and acts as a source of truth to the consumer.

- **DI**:

  This acts as the glue between the core ,data and UI.The UI relies on the core models and interfaces which are implemented in data.

- **UI**:

  Contains the presentation layer of the app, the screen components and viewmodels. Framework specific dependencies are best suited for this layer.
  In this layer MVI is also used, it looks similar to MVVM but the difference is the actions from a screen a.k.a intents e.g ```HomeScreenIntent``` are predefined and are finite,making the
  the screen state a bit more predictable and it's easier to scan through what actions are possible from a given screen.

  The screen state e.g ```HomeScreenViewState``` is also modelled as a class with immutable properties and makes state management way easier by reducing the state whenever their is a new update received.
  Some design patterns that can be seen here are the Observer pattern when consuming the flow -> state flows in the composables and provides a reactive app.

![Add flow diagram here](/docs/MVI.png)

**Testing**

The data layer is unit tested by mocking out external dependencies and the ui layer on the viewmodels, an integration test
is written that makes use of fake,so as to mimic the real scenario as much as possible over using mocks, which would also turn it to a unit test.

# Technologies 🔨

**Language :** [Kotlin](https://github.com/JetBrains/kotlin)

**Libraries :**

*UI*
- [Compose](https://developer.android.com/jetpack/compose)
- [Coil](https://coil-kt.github.io/coil/compose/https://coil-kt.github.io/coil/compose/)

  *Data*
- [Retrofit](https://square.github.io/retrofit/)
- [OkHTTP](https://square.github.io/okhttp/)
- [kotlinx.serialization](https://kotlinlang.org/docs/serialization.html)

  *Testing*
- [Junit](https://junit.org/junit4/)
- [Mockk](https://mockk.io/)
- [Truth](https://truth.dev/)
- [Turbine](https://github.com/cashapp/turbine)

  *Tooling/Project setup*
- [Hilt(DI)](https://developer.android.com/training/dependency-injection/hilt-android)


# Screenshots 📱

```kotlin
TODO("Add Screenshots Here")
```


![](https://media.giphy.com/media/hWvk9iUU4uBBeyBq0k/giphy.gif)


