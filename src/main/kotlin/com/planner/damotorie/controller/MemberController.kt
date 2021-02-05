package com.planner.damotorie.controller

import com.planner.damotorie.dao.AuthType
import com.planner.damotorie.dao.dto.UserDto
import com.planner.damotorie.service.MemberService
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.lang.Exception

@Controller
@RequestMapping
class MemberController(val memberService: MemberService) {

    @GetMapping("/register")
    fun registerForm(): String {
        return "register"
    }

    @PostMapping("/register", consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun register(@RequestParam params: HashMap<String, String>): String {
        val userDto: UserDto = UserDto(params["email"] ?: throw Exception("Email should not be null"),
                                        params["nickname"] ?: throw Exception("Nickname should not be null"),
                                        params["password"] ?: throw Exception("Password should not be null"))
        memberService.registerNewUser(userDto, AuthType.HOMEPAGE)
        return "redirect:/plan"
    }
}