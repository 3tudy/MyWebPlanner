package com.planner.damotorie.security.oauth

import com.planner.damotorie.dao.AuthType
import java.lang.Exception

class KakaoPrincipalExtractor: PrincipalExtractor {

    override fun support(type: AuthType): Boolean {
        return type == AuthType.KAKAO
    }

    override fun extractPrincipal(map: Map<String, Any>, authType: AuthType): Any {
        try {
            return map["id"] ?: throw Exception("ID should not be null")
        } catch (e: Exception) {
            throw Exception("Wrong response format")
        }
    }
}