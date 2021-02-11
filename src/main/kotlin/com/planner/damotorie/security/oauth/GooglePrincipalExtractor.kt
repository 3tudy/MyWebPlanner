package com.planner.damotorie.security.oauth

import com.planner.damotorie.dao.AuthType
import java.lang.Exception

class GooglePrincipalExtractor: PrincipalExtractor {

    override fun support(type: AuthType): Boolean {
        return type == AuthType.GOOGLE
    }

    override fun extractPrincipal(map: Map<String, Any>, authType: AuthType): SocialLoginPrincipal {
        try {
            val id: String = map["sub"] as String ?: throw Exception("ID should not be null")
            val nickname: String = map.getOrDefault("name", "") as String
            val email: String = map.getOrDefault("email", "") as String

            return SocialLoginPrincipal(nickname, email, id, AuthType.GOOGLE)
        } catch (e: Exception) {
            throw Exception("Wrong response format")
        }
    }
}