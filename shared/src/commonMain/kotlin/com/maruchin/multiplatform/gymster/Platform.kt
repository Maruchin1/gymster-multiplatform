package com.maruchin.multiplatform.gymster

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform