# gradle-postgresql-embedded

Gradle plugin to run embedded PostgreSQL server, based on the [postgresql-embedded](https://github.com/yandex-qatools/postgresql-embedded) project.

## Installation

Build script snippet for use in all Gradle versions:
```groovy
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.github.honourednihilist:gradle-postgresql-embedded:0.1.0"
  }
}

apply plugin: "com.github.honourednihilist.gradle-postgresql-embedded"
```

Build script snippet for new, incubating, plugin mechanism introduced in Gradle 2.1:
```groovy
plugins {
    id "com.github.honourednihilist.gradle-postgresql-embedded" version "0.1.0"
}
```

## Usage

The gradle-postgresql-embedded plugin adds only one task - _startPostgres_. This task runs an embedded PostgreSQL server and stops it when the build is finished (by default).
You can make your tasks dependent on it. Here is an example of how the plugin is used in a 'real-world' project - [gradle-postgresql-embedded-example](https://github.com/honourednihilist/gradle-postgresql-embedded-example).


### Configuration

These are all possible configuration options and its default values:

```groovy
postgresEmbedded {
	version = "V9_6_3"
	host = "localhost"
	port = 0 // zero value means a random port
	dbName = "embedded"
	username = "username"
	password = "password"
	artifactStorePath = "~/.embedpostgresql" // where PostgreSQL distributions are stored after downloading, inside home directory by default 
	stopWhenBuildFinished = true
	timeoutMillisBeforeStop = 0
}
```

You can specify any supported version from [postgresql-embedded](https://github.com/yandex-qatools/postgresql-embedded/blob/postgresql-embedded-2.4/src/main/java/ru/yandex/qatools/embed/postgresql/distribution/Version.java), and a custom version of PostgreSQL as well.
