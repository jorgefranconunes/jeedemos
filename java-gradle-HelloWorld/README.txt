= Simple Gradle project





== Installing Gradle

Installation instructions at https://gradle.org/install/#manually

Download from
https://services.gradle.org/distributions/gradle-4.3-bin.zip

----
wget \
    -O ~/tmp/gradle-4.3-bin.zip \
    https://services.gradle.org/distributions/gradle-4.3-bin.zip
----

Install to `~/local/gradle-4.3`

----
unzip ~/tmp/gradle-4.3-bin.zip -d ~/local
ln -s ../local/gradle-4.3/bin/gradle ~/bin/
----

Because `~/bin` is in your PATH (right?) the `gradle` command is now
also in your PATH.

The `gradle` command will use the `JAVA_HOME` envirnoment variable if
it is set. If not set `JAVA_HOME` is not set it will look for a `java`
executable in the PATH.

A Gradle installation is not stricly for a project that has already
been configured to use the Gradle wrapper. But it is indeed necessary
for a new project where the Gradle wrapper has yet to be created.





== Generating the project Gradle wrapper

----
gradle wrapper
----

The above will create a Gradle wrapper that will use a Gradle release
with the same version version as the `gradle` command. The wrapper is
created in the current directory (supposedly the project root
directory) and namd `gradlew`.

The first time the `gradlew` command is executed it will perform a
Gradle installation under `~/.gradle/wrapper`.





== Minimalistic Java program

----
./
├── build.gradle
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src
    └── main
        └── java
            └── com
                └── varmateo
                    └── Main.java
----

Contents of `build.gradle`:

[source,groovy]
----
apply plugin: 'java'
apply plugin: 'application'

description = 'Hello World demo program'
version = '1.0'

mainClassName = 'com.varmateo.App'
----

Contents of `settings.gradle`:

[source,groovy]
----
rootProject.name='helloworld'
----


To build:

----
./gradlew build
----

The above will create the application JAR file under
`./build/libs/helloworld-1.0.jar`.


To run in the development environment:

----
./gradlew run
----





== Emacs Gradle mode

----
M-x package-install gradle-mode
M-x package-install groovy-mode
----
