package com.planner.damotorie.security.oauth

import com.planner.damotorie.dao.AuthType
import java.lang.Exception

class NaverPrincipalExtractor: PrincipalExtractor {

    override fun support(type: AuthType): Boolean {
        return type == AuthType.NAVER
    }

    override fun extractPrincipal(map: Map<String, Any>, authType: AuthType): SocialLoginPrincipal {
        try {
            val response: Map<String, String> = map["response"] as Map<String, String> ?: throw Exception("Response should not be empty")
            val id: String = response["id"] ?: throw Exception("ID should not be null")
            val nickname: String = response.getOrDefault("nickname", "")
            val email: String = response.getOrDefault("email", "")

            return SocialLoginPrincipal(nickname, email, id, AuthType.NAVER)
        } catch (e: Exception) {
            throw Exception("Wrong response format")
        }
    }
}