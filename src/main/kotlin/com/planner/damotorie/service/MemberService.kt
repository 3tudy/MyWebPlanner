package com.planner.damotorie.service

import com.planner.damotorie.dao.AuthType
import com.planner.damotorie.dao.User
import com.planner.damotorie.dao.UserRepository
import com.planner.damotorie.dao.dto.UserDto
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class MemberService(val userRepository: UserRepository, val passwordEncoder: PasswordEncoder): UserDetailsService {

    override fun loadUserByUsername(email: String?): UserDetails {
        val user: User = userRepository.findByEmail(email ?: throw Exception("Email information should not be null!"))

        val authorities: List<GrantedAuthority> = arrayListOf<GrantedAuthority>(SimpleGrantedAuthority("ROLE_MEMBER"))
        return org.springframework.security.core.userdetails.User(email, user.password, authorities)
    }

    fun registerNewUser(userDto: UserDto, authType: AuthType): User {
        val user: User = User(nickname = userDto.nickname, email = userDto.email,
            socialAuthType = authType.typeName,
            password = passwordEncoder.encode(userDto.password),
            role = "ROLE_MEMBER")

        return userRepository.save(user)
    }
}
