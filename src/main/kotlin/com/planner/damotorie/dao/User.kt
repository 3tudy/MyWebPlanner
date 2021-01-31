package com.planner.damotorie.dao

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class User(@GeneratedValue(strategy = GenerationType.AUTO) @Id val uid: Long, val nickname: String, val email: String,
                val socialAuthType: String, val password: String, val role: Long)


