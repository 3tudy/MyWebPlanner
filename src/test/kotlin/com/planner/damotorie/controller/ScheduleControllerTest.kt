package com.planner.damotorie.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.planner.damotorie.model.dto.ScheduleMeta
import com.planner.damotorie.service.ScheduleService
import org.aspectj.lang.annotation.Before
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders


internal class ScheduleControllerTest() {
    private lateinit var mockMvc: MockMvc
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup(): Unit {
        this.mockMvc = MockMvcBuilders.standaloneSetup(ScheduleController(scheduleService=ScheduleService())).build();
        this.objectMapper = ObjectMapper()
    }

    @Test
    fun createSchedule() {
        val scheduleMeta = ScheduleMeta("first_schedule")
        val json: String = objectMapper.writeValueAsString(scheduleMeta)

        this.mockMvc.perform(post("/schedule/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("name").value("first_schedule"))
    }
}