package com.planner.damotorie.dao

import javax.persistence.*

@Entity
class ScheduleContent(content: String, schedule: Schedule) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var scheduleContentId: Long? = null

    var content: String = content

    @OneToOne(targetEntity = Schedule::class)
    var schedule: Schedule = schedule

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ScheduleContent

        if (scheduleContentId != other.scheduleContentId) return false
        if (content != other.content) return false
        if (schedule != other.schedule) return false

        return true
    }

    override fun hashCode(): Int {
        var result = scheduleContentId?.hashCode() ?: 0
        result = 31 * result + content.hashCode()
        result = 31 * result + schedule.hashCode()
        return result
    }
}
