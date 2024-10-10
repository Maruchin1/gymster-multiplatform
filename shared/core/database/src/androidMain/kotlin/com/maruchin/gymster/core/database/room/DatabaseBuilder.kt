package com.maruchin.gymster.core.database.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

internal fun databaseBuilder(context: Context): RoomDatabase.Builder<GymsterDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("gymster.db")
    return Room.databaseBuilder<GymsterDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
