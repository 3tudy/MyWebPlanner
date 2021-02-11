package com.planner.damotorie.security.oauth

import com.planner.damotorie.dao.User
import com.planner.damotorie.dao.UserRepository
import org.springframework.security.core.Authentication
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.RedirectStrategy
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import java.lang.Exception
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class OAuth2AuthenticationSuccessHandler(val userRepository: UserRepository): AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val redirectStrategy: RedirectStrategy = DefaultRedirectStrategy()
        val principal: SocialLoginPrincipal = (authentication ?. principal ?: throw Exception("Not authenticated")) as SocialLoginPrincipal
        val user: User = User(principal.nickname, principal.email, principal.socialUid, principal.socialAuthType.toString(), "", "ROLE_MEMBER")

        try {
            userRepository.findBySocialUidAndSocialAuthType(user.socialUid, user.socialAuthType)
        } catch (e: Exception) {
            userRepository.save(user)
        }

        redirectStrategy.sendRedirect(request, response, "/plan")
    }
}