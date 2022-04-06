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

|<img src=share.png align="center" height="400" width="200" ></a> |<img src=search.png  align="center" height="400" width="200" ></a>|<img src=favorite.png  align="center" height="400" width="200" ></a>|
|:-----------:|:--------:|:--------:|
| *share* | *search* | *favorite* |

|<img src=settings.png align="center" height="400" width="200" ></a> |<img src=notification.png  align="center" height="400" width="200" ></a>|<img src=profile.png  align="center" height="400" width="200" ></a>|
|:-----------:|:--------:|:--------:|
| *settings* | *notification* | *profile* |


### Hardware
- CPU : Intel© Core™ i3-6006U CPU @ 2.0GHz
- Memory : 2 x 8 GB RAM
- Graphics : Intel HD Graphics 520

### Software
#### Operating System
- OS Name : Linux Mint (based on Ubuntu 20.04 LTS)
- Version : 20.3
- Platform : 64 bit

#### Programming Language
- Language Name : Kotlin
- Version : 1.6.20

#### IDE (Integrated Development Environment)
- IDE Name : Android Studio
- Version : Android Studio Bumblebee | 2021.1.1 Patch 2

#### Java Build Tools
- Java Build Tools : Gradle
- Android Gradle Plugin : 7.1.2
- Android Gradle : 7.2

#### SDK Version and SDK Tools
- Target SDK Version : 32
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

        - implementation 'com.google.android.material:material:1.5.0'

- Network

        - implementation 'com.squareup.retrofit2:retrofit:2.9.0'
        - implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
        - implementation 'com.squareup.okhttp3:okhttp:4.9.2'
        - implementation 'com.squareup.okhttp3:logging-interceptor:4.9.2'

- View Model

        - implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
        - implementation "androidx.lifecycle:lifecycle-common-java8:2.4.1"

- Glide

        - implementation 'com.github.bumptech.glide:glide:4.13.1'

- Circle image

        - implementation 'de.hdodenhof:circleimageview:3.1.0'

- Toasty

        - implementation 'com.github.GrenderG:Toasty:1.5.0'

- Room DB

        - def room_version = '2.4.2'
        - implementation "androidx.room:room-runtime:$room_version"
        - implementation "androidx.room:room-ktx:$room_version"
        - testImplementation "androidx.room:room-testing:$room_version"
        - kapt "androidx.room:room-compiler:$room_version"

- Coroutine

        - implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0"
        - implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0"

- Shared Preferences

        - implementation 'androidx.preference:preference-ktx:1.2.0'

- Shimmer

        - implementation 'com.facebook.shimmer:shimmer:0.5.0'