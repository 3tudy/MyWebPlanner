package com.planner.damotorie.model.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Schedule(
    @Id @GeneratedValue var sid: Long
)