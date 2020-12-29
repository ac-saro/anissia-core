package anissia.domain

import javax.persistence.*

@Entity
@Table(
        uniqueConstraints = [UniqueConstraint(columnNames = ["ticker"])],
)
data class BoardTicker (
    @Id
    @Column(nullable = false, length = 10)
    var ticker: String = "",

    @Column(nullable = false, length = 20)
    var name: String = "",

    @Enumerated(EnumType.STRING)
    @Column(nullable = true, length = 10)
    val writeTopic: AccountRole? = AccountRole.ROOT,

    @Enumerated(EnumType.STRING)
    @Column(nullable = true, length = 10)
    val writePost: AccountRole? = AccountRole.ROOT
)