# kmulti-logging

kmulti-logging provides convenient wrappers around a chosen Kotlin
multiplatform logging implementation (currently [KLogging](https://github.com/Lewik/klogging)).

## Download

```
repositories {
    maven { url "https://dl.bintray.com/kmulti/kmulti-logging" }
}
```

Use these dependencies per Kotlin module respectively:

```
compile 'io.github.kmulti:kmulti-logging-common:1.2.41'
compile 'io.github.kmulti:kmulti-logging-js:1.2.41'
compile 'io.github.kmulti:kmulti-logging-jvm:1.2.41'
```

## Usage

Add a logger to a class using a companion object which inherits from `Log`:

```kotlin
companion object : Log() {}
```

Create a logger as a property for use in top-level functions:

```kotlin
private val logger = initTopLevelLogger {}
```

The standard logging prefixes are found and applied automatically.
