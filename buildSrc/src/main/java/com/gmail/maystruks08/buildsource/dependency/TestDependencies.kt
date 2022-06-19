@file:Suppress("unused")

object TestDependencies {
    const val junit = "junit:junit:${Versions.junit}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val core = "androidx.arch.core:core-testing:${Versions.core}"
    const val coreTest = "androidx.test:core:${Versions.coreTest}"
    const val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
}