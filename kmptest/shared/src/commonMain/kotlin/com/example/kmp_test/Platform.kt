package com.example.kmp_test

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform