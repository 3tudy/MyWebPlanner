package com.planner.damotorie.config

import com.planner.damotorie.service.MemberService
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
open class SecurityConfig(val memberService: MemberService, val passwordEncoder: PasswordEncoder): WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(memberService)?.passwordEncoder(passwordEncoder)
    }

    override fun configure(web: WebSecurity?) {
        web?.ignoring()?.antMatchers("/resources/**")
    }

    override fun configure(http: HttpSecurity?) {
        http?.headers()
            ?.frameOptions()
            ?.sameOrigin()
            ?.and()
            ?.authorizeRequests()
            ?.antMatchers("/register", "/admin/h2/**")?.anonymous()
            ?.antMatchers("/**")?.hasRole("MEMBER")
            ?.and()
            ?.formLogin()
            ?.and()
            ?.logout()
                ?.logoutUrl("/logout")
                ?.logoutSuccessUrl("/plan")
                ?.invalidateHttpSession(true)
            ?.and()
            ?.csrf()
                ?.disable()
            ?.exceptionHandling()
                ?.accessDeniedPage("/error/401")

    }
}

