package com.planner.damotorie.security.oauth

import com.planner.damotorie.dao.AuthType
import java.lang.Exception

class FacebookPrincipalExtractor: PrincipalExtractor {

    override fun support(type: AuthType): Boolean {
        return type == AuthType.FACEBOOK
    }

    override fun extractPrincipal(map: Map<String, Any>, authType: AuthType): Any {
        try {
            return map["id"] ?: throw Exception("ID should not be null")
        } catch (e: Exception) {
            throw Exception("Wrong response format")
        }
    }
}