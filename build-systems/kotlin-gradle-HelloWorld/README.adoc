= Simple Gradle project with Kotlin





== Development environment setup

To install Kotlin mode for Emacs follow the steps below.

Add the MELPA package repo to your `~/.emacs`:

----
(add-to-list 'package-archives
             '("melpa" . "https://melpa.org/packages/"))
----

Now install the `kotlin-mode` package:

----
M-x package-install kotlin
----

And add this one line to the `custom-set-variables` section in your
`~/.emacs` to have a sensible indentation on Kotlin source files.

----
 '(kotlin-tab-width 4)
----


Finally, add support to Kotlin compiler error messags. Add the
following to your `~/.emacs`:

----
(add-to-list
 'compilation-error-regexp-alist-alist
 '(kotlin "^e: \\(.*\\): (\\([0-9]+\\), \\([0-9]+\\)):" 1 2 3 2 1))
(add-to-list 'compilation-error-regexp-alist 'kotlin)
----






== Minimalistic Kotlin program

To build:

----
./gradlew build
----


To run in the development environment:

----
./gradlew run
----
