package com.planner.damotorie.dao

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime

@Repository
interface EventRepository: JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e WHERE ?1 < e.start AND e.start < ?2")
    fun findByDateRange(startDate: LocalDateTime, endDate: LocalDateTime): List<Event>

    fun findByStart(date: LocalDateTime): Event

    fun findByEnd(date: LocalDateTime): Event
}