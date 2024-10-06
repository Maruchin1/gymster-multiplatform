package com.maruchin.gymster.core.utils

import java.util.UUID

actual fun uuid(): String = UUID.randomUUID().toString()
