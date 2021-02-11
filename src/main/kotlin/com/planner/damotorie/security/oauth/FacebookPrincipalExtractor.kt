package com.planner.damotorie.security.oauth

import com.planner.damotorie.dao.AuthType
import java.lang.Exception

class FacebookPrincipalExtractor: PrincipalExtractor {

    override fun support(type: AuthType): Boolean {
        return type == AuthType.FACEBOOK
    }

    override fun extractPrincipal(map: Map<String, Any>, authType: AuthType): SocialLoginPrincipal  {
        try {
            val id: String = map["id"] as String ?: throw Exception("ID should not be null")
            val nickname: String = map.getOrDefault("name", "") as String
            val email: String = map.getOrDefault("email", "") as String

            return SocialLoginPrincipal(nickname, email, id, AuthType.FACEBOOK)
        } catch (e: Exception) {
            throw Exception("Wrong response format")
        }
    }
}