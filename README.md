# gitser-kt
This application is a simple Github user search app using Kotlin. This application using:
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
|<img src=splashscreen.jpg align="center" height="400" width="248" ></a> |<img src=main.jpg  align="center" height="400" width="248" ></a>|<img src=detail.jpg  align="center" height="400" width="248" ></a>|
|:-----------:|:--------:|:--------:|
| *splash screen* | *main* | *detail* |

|<img src=share.jpg align="center" height="400" width="248" ></a> |<img src=search.jpg  align="center" height="400" width="248" ></a>|<img src=favorite.jpg  align="center" height="400" width="248" ></a>|
|:-----------:|:--------:|:--------:|
| *share* | *search* | *favorite* |

|<img src=settings.jpg align="center" height="400" width="248" ></a> |<img src=notification.jpg  align="center" height="400" width="248" ></a>|<img src=profile.jpg  align="center" height="400" width="248" ></a>|
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
- Android Gradle Plugin Version : 4.0.0
- Android Gradle : 6.5

#### SDK Version and SDK Tools
- Target SDK Version : 30
- Min SDK Version : 23
- Android SDK Tools : 26.1.1

#### AndroidX
- Migrate to AndroidX : Yes

#### Dependencies
##### By Default
        - implementation "org.jetbrains.kotlin:kotlin-stdlib:1.4.30"
        - implementation 'androidx.core:core-ktx:1.3.2'
        - implementation 'androidx.appcompat:appcompat:1.2.0'
        - implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
        - testImplementation 'junit:junit:4.13.1'
        - androidTestImplementation 'androidx.test.ext:junit:1.1.2'
        - androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'

##### By Third Parties
- Material design

        - implementation 'com.google.android.material:material:1.3.0'

- Network

        - implementation 'com.squareup.retrofit2:retrofit:2.6.2'
        - implementation 'com.squareup.retrofit2:converter-gson:2.6.2'
        - implementation 'com.squareup.okhttp3:okhttp:3.12.0'
        - implementation 'com.squareup.okhttp3:logging-interceptor:3.10.0'

- View Model

        - implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
        - implementation "androidx.lifecycle:lifecycle-common-java8:2.2.0"

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

