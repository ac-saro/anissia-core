package anissia.rdb.domain

import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(uniqueConstraints = [
        UniqueConstraint(name = "account_pk1", columnNames = ["an"]),
        UniqueConstraint(name = "account_pk2", columnNames = ["email"]),
        UniqueConstraint(name = "account_pk3", columnNames = ["name"]),
])
data class Account (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(nullable = false)
        var an: Long = 0, // account number

        @Column(nullable = false, length = 64)
        var email: String = "",

        @Column(nullable = false, length = 512)
        var password: String = "",

        @Column(nullable = false, length = 16, unique = true)
        var name: String = "",

        @Column(nullable = false)
        var regDt: LocalDateTime = LocalDateTime.now(),

        @UpdateTimestamp
        @Column(nullable = false)
        var lastLoginDt: LocalDateTime = LocalDateTime.now(),

        @Column(nullable = true)
        var banExpireDt: LocalDateTime? = null,

        @ElementCollection
        @CollectionTable(name = "AccountRole", joinColumns = [JoinColumn(name = "an")])
        @Enumerated(EnumType.STRING)
        @Column(name = "role", nullable = false, length = 10)
        val roles: MutableSet<AccountRole> = mutableSetOf(),

        // deprecated
        @Column(nullable = false, length = 64)
        var oldAccount: String = "",

        @Column(nullable = false)
        var oldAccountNo: Long = 0
) {
        val isBan: Boolean get() = lastLoginDt.isAfter(LocalDateTime.now())
        val isAdmin: Boolean get() = roles.any { it == AccountRole.TRANSLATOR || it == AccountRole.ROOT }
}

/*
CREATE TABLE `account` (
`an` bigint(20) NOT NULL AUTO_INCREMENT,
`email` varchar(64) NOT NULL,
`password` varchar(512) NOT NULL,
`name` varchar(16) NOT NULL,
`reg_dt` datetime NOT NULL,
`last_login_dt` datetime NOT NULL,
`ban_expire_dt` datetime DEFAULT NULL,
`old_account` varchar(64) NOT NULL,
`old_account_no` bigint(20) NOT NULL,
PRIMARY KEY (`an`),
UNIQUE KEY `account_pk2` (`email`),
UNIQUE KEY `account_pk3` (`name`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `account_role` (
  `an` bigint(20) NOT NULL,
  `role` varchar(10) NOT NULL,
  PRIMARY KEY (`an`,`role`),
  CONSTRAINT `account_role_fk1` FOREIGN KEY (`an`) REFERENCES `account` (`an`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
*/