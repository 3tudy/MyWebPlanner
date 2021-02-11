package com.planner.damotorie.security.oauth

import com.planner.damotorie.dao.AuthType
import java.lang.Exception

class NaverPrincipalExtractor: PrincipalExtractor {

    override fun support(type: AuthType): Boolean {
        return type == AuthType.NAVER
    }

    override fun extractPrincipal(map: Map<String, Any>, authType: AuthType): Any {
        try {
            val response: Map<String, String> = map["response"] as Map<String, String> ?: throw Exception("Response should not be empty")
            return response["id"] ?: throw Exception("ID should not be null")
        } catch (e: Exception) {
            throw Exception("Wrong response format")
        }
    }
}