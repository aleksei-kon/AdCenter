const val kotlin_version = "1.3.50"

object BuildPlugins {

    private object Versions {

        const val gradle = "3.5.1"
    }

    const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
}

object Libs {

    private object Versions {

        const val junit = "4.12"
        const val testExtJunit = "1.1.0"
        const val espresso = "3.1.1"

        const val material = "1.0.0"
        const val appcompat = "1.0.2"
        const val coreKtx = "1.0.2"
        const val constraintlayout = "1.1.3"
        const val okhttp = "3.12.1"
        const val gson = "2.8.5"
        const val koin = "2.0.1"
        const val glide = "4.9.0"
        const val imageSlider = "1.3.2"
        const val coroutines = "1.3.2"
    }

    const val junit = "junit:junit:${Versions.junit}"
    const val testExtJunit = "androidx.test.ext:junit:${Versions.testExtJunit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    const val material = "com.google.android.material:material:${Versions.material}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideAnnotationProcessor = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val imageSlider = "com.github.smarteist:autoimageslider:${Versions.imageSlider}"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    const val koin = "org.koin:koin-android:${Versions.koin}"
    const val koinScope = "org.koin:koin-androidx-scope:${Versions.koin}"
    const val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    //const val koinFragment = "org.koin:koin-androidx-fragment:${Versions.koin}"
    const val koinExperimental = "org.koin:koin-androidx-ext:${Versions.koin}"
}