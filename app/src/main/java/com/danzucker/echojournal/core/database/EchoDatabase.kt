package com.danzucker.echojournal.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.danzucker.echojournal.core.database.echo.EchoDao
import com.danzucker.echojournal.core.database.echo.EchoEntity
import com.danzucker.echojournal.core.database.echo.FloatListTypeConverter
import com.danzucker.echojournal.core.database.echo.MoodTypeConverter
import com.danzucker.echojournal.core.database.echotopicrelation.EchoTopicCrossRef
import com.danzucker.echojournal.core.database.topic.TopicEntity

@Database(
    entities = [EchoEntity::class, TopicEntity::class, EchoTopicCrossRef::class],
    version = 1,
)
@TypeConverters(
    MoodTypeConverter::class,
    FloatListTypeConverter::class
)
abstract class EchoDatabase: RoomDatabase() {
    abstract val echoDao: EchoDao
}