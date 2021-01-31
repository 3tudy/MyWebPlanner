package com.planner.damotorie.dao

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Event(schedule: Schedule, start: LocalDateTime, end: LocalDateTime, location: String) {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    var eventId: Long? = null

    @ManyToOne(targetEntity = Schedule::class, fetch = FetchType.LAZY)
    val schedule: Schedule = schedule

    val start: LocalDateTime = start

    val end: LocalDateTime = end

    val location: String = location

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Event

        if (eventId != other.eventId) return false
        if (schedule != other.schedule) return false
        if (start != other.start) return false
        if (end != other.end) return false
        if (location != other.location) return false

        return true
    }

    override fun hashCode(): Int {
        var result = eventId?.hashCode() ?: 0
        result = 31 * result + schedule.hashCode()
        result = 31 * result + start.hashCode()
        result = 31 * result + end.hashCode()
        result = 31 * result + location.hashCode()
        return result
    }


}
