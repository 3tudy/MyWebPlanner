package com.planner.damotorie.security.oauth

import com.planner.damotorie.dao.AuthType
import java.security.Principal

data class SocialLoginPrincipal(val nickname: String, val email: String, val socialUid: String, val socialAuthType: AuthType): Principal {
    override fun getName(): String {
        return "SocialLogin"
    }
}