package com.planner.damotorie.service

import java.lang.Exception

/**
 * Default implementation of [PrincipalExtractor]. Extracts the principal from the
 * map with well known keys.
 *
 * @author Phillip Webb
 * @since 1.4.0
 */
class FixedPrincipalExtractor : PrincipalExtractor {
    override fun extractPrincipal(map: Map<String, Any>): Any {

        if (!("response" in map.keys)) {
            for (key in PRINCIPAL_KEYS) {
                if (map.containsKey(key)) {
                    return map[key] ?: Exception("user/me body has null")
                }
            }
        } else if ("response" in map.keys) {
            val response: Map<String, Any> = map["response"] as Map<String, Any>
            for (key in PRINCIPAL_KEYS) {
                if (response.containsKey(key)) {
                    return response[key] ?: Exception("user/me body has null")
                }
            }
        }



        throw Exception("Can't find principal information from user/me")
    }

    companion object {
        private val PRINCIPAL_KEYS = arrayOf(
            "user", "username", "userid", "user_id", "login",
            "id", "name"
        )
    }
}