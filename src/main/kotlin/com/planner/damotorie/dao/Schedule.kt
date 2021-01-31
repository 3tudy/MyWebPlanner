package com.planner.damotorie.dao

import javax.persistence.*

@Entity
class Schedule(scheduleName: String) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var scheduleId: Long? = null

    var scheduleName: String = scheduleName

    @OneToOne(targetEntity = ScheduleContent::class, fetch = FetchType.LAZY)
    lateinit var scheduleContent: ScheduleContent

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Schedule

        if (scheduleId != other.scheduleId) return false
        if (scheduleName != other.scheduleName) return false
        if (scheduleContent != other.scheduleContent) return false

        return true
    }

    override fun hashCode(): Int {
        var result = scheduleId?.hashCode() ?: 0
        result = 31 * result + scheduleName.hashCode()
        result = 31 * result + scheduleContent.hashCode()
        return result
    }


}
