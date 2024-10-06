package com.maruchin.gymster.core.utils

import platform.Foundation.NSUUID

actual fun uuid(): String = NSUUID.UUID().UUIDString()
