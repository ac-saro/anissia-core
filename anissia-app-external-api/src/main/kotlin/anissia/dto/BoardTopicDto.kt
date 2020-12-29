package anissia.dto

import anissia.domain.BoardTopic
import java.time.LocalDateTime

data class BoardTopicDto (
    var topicNo: Long = 0,
    var fixed: Boolean = false,
    var topic: String = "",
    var postCount: Int = 0,
    var regDt: LocalDateTime = LocalDateTime.now(),
    var name: String = ""
) {
    constructor(boardTopic: BoardTopic): this(
        topicNo = boardTopic.topicNo,
        fixed = boardTopic.fixed,
        topic = boardTopic.topic,
        postCount = boardTopic.postCount,
        regDt = boardTopic.regDt,
        name = boardTopic.account?.name ?: "탈퇴회원"
    )
}