package com.planner.damotorie.controller

import com.planner.damotorie.dao.*
import com.planner.damotorie.dao.Date
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.util.*

@RequestMapping("/plan")
@Controller
class PlannerController {

    @Autowired
    lateinit var scheduleRepository: ScheduleRepository

    @Autowired
    lateinit var scheduleContentRepository: ScheduleContentRepository

    @Autowired
    lateinit var eventRepository: EventRepository

    @GetMapping
    fun getCurrentMonthlyPlan(): ModelAndView {
        val currentDate: Calendar = Calendar.getInstance(Locale.KOREA)
        val year: Int = currentDate.get(Calendar.YEAR)
        val month: Int = currentDate.get(Calendar.MONTH) + 1

        val firstDateOfMonth: LocalDateTime = LocalDateTime.of(year, month, 1, 0, 0)
        val lastDateOfMonth: LocalDateTime = firstDateOfMonth.plusMonths(1).minusSeconds(1)

        val schedules: List<Event> = eventRepository.findByDateRange(firstDateOfMonth, lastDateOfMonth)
        val date: List<List<Date>> = createMonthInfo(year, month, schedules)
        return ModelAndView("calendar_month", mapOf("year" to year, "month" to month, "date" to date))
    }

    @GetMapping("/year/{year}")
    fun getYearlyPlan(@PathVariable year: Int): ModelAndView {
        return ModelAndView("home", mapOf("type" to "year", "value" to year))
    }

    @GetMapping("/year/{year}/month/{month}")
    fun getMonthlyPlan(@PathVariable year: Int,
                       @PathVariable month: Int): ModelAndView {

        val firstDateOfMonth: LocalDateTime = LocalDateTime.of(year, month, 1, 0, 0)
        val lastDateOfMonth: LocalDateTime = firstDateOfMonth.plusMonths(1).minusDays(1)

        val schedules: List<Event> = eventRepository.findByDateRange(firstDateOfMonth, lastDateOfMonth)
        val dates: List<List<Date>> = createMonthInfo(year, month, schedules)

        return ModelAndView("calendar_month", mapOf("year" to year, "month" to month, "date" to dates))
    }

    @GetMapping("/year/{year}/month/{month}/day/{day}")
    fun getDailyPlan(@PathVariable year: Int,
                     @PathVariable month: Int,
                     @PathVariable day: Int): ModelAndView {

        val date: LocalDateTime = LocalDateTime.of(year, month, day, 0, 0, 0)
        val nextDate: LocalDateTime = date.plusDays(1)
        val events: List<Event> = eventRepository.findByDateRange(date, nextDate)
        val plans: List<Schedule> = events.map { event -> event.schedule }

        return ModelAndView("calendar_day", mapOf("year" to year, "month" to month, "day" to day, "plans" to plans))
    }

    @GetMapping("/form")
    fun getRegisterForm(): ModelAndView {
        return ModelAndView("form")
    }

    @PostMapping
    fun createPlan(@RequestParam("title") title: String,
                   @RequestParam("date") date: String,
                   @RequestParam("content") content:String): ModelAndView {

        val scheduleDate: LocalDateTime = LocalDateTime.parse(date)
        val newSchedule: Schedule = Schedule(title)
        val newScheduleContent: ScheduleContent = ScheduleContent(content, newSchedule)
        val newEvent: Event = Event(newSchedule, scheduleDate, scheduleDate, "")


        scheduleRepository.save(newSchedule)
        scheduleContentRepository.save(newScheduleContent)
        eventRepository.save(newEvent)

        return ModelAndView("redirect:/plan")
    }

    @PutMapping
    fun updatePlan() {

    }

    @DeleteMapping
    fun deletePlan() {

    }

    fun createMonthInfo(year: Int, month: Int, schedules: List<Event>): List<List<Date>> {
        val currentMonthFirstDate: Calendar = Calendar.Builder().setDate(year, month - 1, 1).build()
        val currentMonthLastDate: Calendar = Calendar.Builder().setDate(year, month - 1, currentMonthFirstDate.getActualMaximum(Calendar.DATE)).build()

        if (currentMonthFirstDate.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            while (currentMonthFirstDate.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                println("${currentMonthFirstDate.get(Calendar.MONTH)}/${currentMonthFirstDate.get(Calendar.DATE)} ${currentMonthFirstDate.get(Calendar.DAY_OF_WEEK)}")
                currentMonthFirstDate.add(Calendar.DATE, -1)
            }
        }

        var i:Int = 0
        val monthTable: MutableList<MutableList<Date>> = mutableListOf()
        while (true) {
            if (i % 7 == 0) {
                monthTable.add(mutableListOf())
            }

            print("${currentMonthFirstDate.get(Calendar.MONTH) + 1}/${currentMonthFirstDate.get(Calendar.DATE)} ")

            val d: Date
            if (currentMonthFirstDate.get(Calendar.MONTH) + 1 == month) {
                if (schedules.any { schedule -> LocalDate.ofInstant(currentMonthFirstDate.toInstant(), currentMonthFirstDate.timeZone.toZoneId()).equals(schedule.start.toLocalDate()) }) {
                    d = Date(
                        currentMonthFirstDate.get(Calendar.DATE),
                        currentMonthFirstDate.get(Calendar.DAY_OF_WEEK),
                        currentMonthFirstDate.get(Calendar.MONTH) == month - 1,
                        true
                    )
                } else {
                    d = Date(
                        currentMonthFirstDate.get(Calendar.DATE),
                        currentMonthFirstDate.get(Calendar.DAY_OF_WEEK),
                        currentMonthFirstDate.get(Calendar.MONTH) == month - 1,
                        false
                    )
                }

            } else {
                d = Date(
                    currentMonthFirstDate.get(Calendar.DATE),
                    currentMonthFirstDate.get(Calendar.DAY_OF_WEEK),
                    currentMonthFirstDate.get(Calendar.MONTH) == month - 1,
                    false
                )
            }

            monthTable.last().add(d)
            i += 1

            if ((currentMonthFirstDate.after(currentMonthLastDate) || currentMonthFirstDate == currentMonthLastDate) &&
                currentMonthFirstDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                break

            currentMonthFirstDate.add(Calendar.DATE, 1)
        }

        return monthTable
    }
}