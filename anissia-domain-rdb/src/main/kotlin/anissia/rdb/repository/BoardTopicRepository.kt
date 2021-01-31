package anissia.rdb.repository

import anissia.rdb.domain.BoardTopic
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface BoardTopicRepository : JpaRepository<BoardTopic, Long>, QuerydslPredicateExecutor<BoardTopic> {

    @EntityGraph(attributePaths = ["account"])
    fun findWithAccountByTickerAndTopicNo(ticker: String, topicNo: Long): BoardTopic?

    @EntityGraph(attributePaths = ["account"])
    fun findAllWithAccountByTickerOrderByTickerAscFixedDescTopicNoDesc(ticker: String, pageable: Pageable): Page<BoardTopic>

    fun findTop5ByTickerAndFixedOrderByTopicNoDesc(ticker: String, fixed: Boolean = false): List<BoardTopic>
}