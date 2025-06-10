package com.danzucker.echojournal.core.database.echotopicrelation

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.danzucker.echojournal.core.database.echo.EchoEntity
import com.danzucker.echojournal.core.database.topic.TopicEntity

@Entity(
    primaryKeys = ["echoId", "topic"],
)
data class EchoTopicCrossRef(
    val echoId: Int,
    val topic: String
)

// To get all the topics related to an echo
data class EchoWithTopics(
    @Embedded val echo: EchoEntity,
    @Relation(
        parentColumn = "echoId",
        entityColumn = "topic",
        associateBy = Junction(EchoTopicCrossRef::class)
    )
    val topics: List<TopicEntity>
)

// If you want to get all the echos related to a topic, no usecase for this yet on the app, but we can define it

data class TopicWithEchos(
    @Embedded val topic: TopicEntity,
    @Relation(
        parentColumn = "topic",
        entityColumn = "echoId",
        associateBy = Junction(EchoTopicCrossRef::class)
    )
    val echos: List<EchoEntity>
)
