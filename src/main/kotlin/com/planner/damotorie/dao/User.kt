package com.planner.damotorie.dao

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class User(nickname: String, email: String, socialAuthType: String, password: String, role: String) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var uid: Long? = null

    val nickname: String = nickname

    val email: String = email

    val socialAuthType: String = socialAuthType

    val password: String = password

    val role: String = role

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (uid != other.uid) return false
        if (nickname != other.nickname) return false
        if (email != other.email) return false
        if (socialAuthType != other.socialAuthType) return false
        if (password != other.password) return false
        if (role != other.role) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uid.hashCode()
        result = 31 * result + nickname.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + socialAuthType.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + role.hashCode()
        return result
    }


}


