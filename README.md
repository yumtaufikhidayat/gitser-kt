# gitser-kt

[![Platform](https://img.shields.io/badge/platform-Android-green)](https://github.com/yumtaufikhidayat/gitser-kt/blob/main/build.gradle)
[![Language](https://img.shields.io/badge/language-Kotlin-blue)](https://github.com/yumtaufikhidayat/gitser-kt/blob/main/build.gradle)
[![MinSdk](https://img.shields.io/badge/minsdk-23-red)](https://github.com/yumtaufikhidayat/gitser-kt/blob/main/build.gradle)

> This app built to accomplish Belajar Fundamental Aplikasi Android course which held by dicoding.com. Please be wise to clone and learn the flow of app. You are not allowed to copy/create derivatives/steal/sell this program codes.

This application is a simple Github user search app using Kotlin. Built using these technologies:
- Material Design
- Kotlin
- View Model (MVVM)
- LiveData
- Retrofit
- View Binding
- Java8 Support
- Room Persistent Database
- Coroutine
- Alarm Manager
- Shared Preference
- Content Provider

### Overview
|<img src=splashscreen.png align="center" height="400" width="200" ></a> |<img src=main.png  align="center" height="400" width="200" ></a>|<img src=detail.png  align="center" height="400" width="200" ></a>|
|:-----------:|:--------:|:--------:|
| *splash screen* | *main* | *detail* |

|<img src=share.png align="center" height="400" width="200" ></a> |<img src=search.png  align="center" height="400" width="200" ></a>|<img src=favorites.png  align="center" height="400" width="200" ></a>|
|:-----------:|:--------:|:--------:|
| *share* | *search* | *favorite* |

|<img src=settings.png align="center" height="400" width="200" ></a> |<img src=notification.png  align="center" height="400" width="200" ></a>|<img src=profile.png  align="center" height="400" width="200" ></a>|
|:-----------:|:--------:|:--------:|
| *settings* | *notification* | *profile* |


### Hardware
- CPU : Intel© Core™ i3-6006U CPU @ 2.0GHz
- Memory : 2 x 4 GB RAM
- Graphics : Intel HD Graphics 520

### Software
#### Operating System
- OS Name : Linux Mint (based on Ubuntu 20.04 LTS)
- Version : 20.1
- Platform : 64 bit

#### Programming Language
- Language Name : Kotlin
- Version : 1.4.30

#### IDE (Integrated Development Environment)
- IDE Name : Android Studio
- Version : 4.1.2

#### Java Build Tools
- Java Build Tools : Gradle
- Android Gradle Plugin Version : 4.1.2
- Android Gradle : 6.5

#### SDK Version and SDK Tools
- Target SDK Version : 30
- Min SDK Version : 23
- Android SDK Tools : 26.1.1

#### AndroidX
- Migrate to AndroidX : Yes

#### Dependencies
##### By Default
        - implementation "org.jetbrains.kotlin:kotlin-stdlib:1.4.31"
        - implementation 'androidx.core:core-ktx:1.3.2'
        - implementation 'androidx.appcompat:appcompat:1.2.0'
        - implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
        - testImplementation 'junit:junit:4.13.2'
        - androidTestImplementation 'androidx.test.ext:junit:1.1.2'
        - androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'

##### By Third Parties
- Material design

        - implementation 'com.google.android.material:material:1.3.0'

- Network

        - implementation 'com.squareup.retrofit2:retrofit:2.9.0'
        - implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
        - implementation 'com.squareup.okhttp3:okhttp:3.14.9'
        - implementation 'com.squareup.okhttp3:logging-interceptor:3.10.0'

- View Model

        - implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
        - implementation "androidx.lifecycle:lifecycle-common-java8:2.3.1"

- Glide

        - implementation 'com.github.bumptech.glide:glide:4.11.0'

- Circle image

        - implementation 'de.hdodenhof:circleimageview:3.1.0'

- Toasty

        - implementation 'com.github.GrenderG:Toasty:1.5.0'

- Room DB

        - def room_version = '2.2.6'
        - implementation "androidx.room:room-runtime:$room_version"
        - implementation "androidx.room:room-ktx:$room_version"
        - testImplementation "androidx.room:room-testing:$room_version"
        - kapt "androidx.room:room-compiler:$room_version"

- Coroutine

        - implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8"
        - implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.0"

- Shared Preferences

        - implementation 'androidx.preference:preference-ktx:1.1.1'

