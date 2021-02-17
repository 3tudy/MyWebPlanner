package com.planner.damotorie.controller

import com.planner.damotorie.model.dto.ScheduleMeta
import com.planner.damotorie.service.ScheduleService
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/schedule")
class ScheduleController(scheduleService: ScheduleService) {

    @PostMapping("/create")
    fun createSchedule(@RequestBody @Valid schedule: ScheduleMeta, bindingResult: BindingResult): ScheduleMeta {
        return schedule
    }
}