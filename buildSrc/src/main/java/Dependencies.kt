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

        const val flowLayout = "1.10"
        const val material = "1.1.0"
        const val swiperefreshlayout = "1.0.0"
        const val appcompat = "1.0.2"
        const val coreKtx = "1.0.2"
        const val constraintlayout = "1.1.3"
        const val coil = "0.9.5"
        const val imageSlider = "1.3.2"
        const val coroutines = "1.3.2"
        const val rxJava = "2.2.19"
        const val rxAndroid = "2.1.1"
        const val rxBinding = "3.1.0"
        const val dagger = "2.27"
        const val viewModel = "2.1.0"
        const val retrofit2 = "2.8.1"
        const val convertergson = "2.4.0"
        const val room = "2.2.5"
    }

    const val junit = "junit:junit:${Versions.junit}"
    const val testExtJunit = "androidx.test.ext:junit:${Versions.testExtJunit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    const val flowLayout = "org.apmem.tools:layouts:${Versions.flowLayout}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefreshlayout}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val imageSlider = "com.github.smarteist:autoimageslider:${Versions.imageSlider}"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    const val viewModel = "androidx.lifecycle:lifecycle-extensions:${Versions.viewModel}"
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    const val rxBinding = "com.jakewharton.rxbinding3:rxbinding:${Versions.rxBinding}"
    const val dagger = "com.google.dagger:dagger-android:${Versions.dagger}"
    const val daggerProcessor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
    const val convertergson = "com.squareup.retrofit2:converter-gson:${Versions.convertergson}"

    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val roomProcessor = "androidx.room:room-compiler:${Versions.room}"
    const val roomKotlinExtensions = "androidx.room:room-ktx:${Versions.room}"
    const val roomRxJava = "androidx.room:room-rxjava2:${Versions.room}"
}