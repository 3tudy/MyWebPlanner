package com.planner.damotorie.dao

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ScheduleContentRepository: JpaRepository<ScheduleContent, Long> {

}