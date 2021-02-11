package com.planner.damotorie.security.oauth

import com.planner.damotorie.dao.AuthType
import java.lang.Exception

class KakaoPrincipalExtractor: PrincipalExtractor {

    override fun support(type: AuthType): Boolean {
        return type == AuthType.KAKAO
    }

    override fun extractPrincipal(map: Map<String, Any>, authType: AuthType): SocialLoginPrincipal {
        try {
            val id: String = map["id"] as String ?: throw Exception("ID should not be null")

            val nickname: String = with(map.getOrDefault("kakao_account", emptyMap<String, Any>()) as Map<String, Any>) {
                getOrDefault("nickname", "") as String
            }

            val email: String = with(map.getOrDefault("kakao_account", emptyMap<String, Any>()) as Map<String, Any>) {
                getOrDefault("email", "") as String
            }

            return SocialLoginPrincipal(nickname, email, id, AuthType.KAKAO)
        } catch (e: Exception) {
            throw Exception("Wrong response format")
        }
    }
}