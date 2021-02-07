package com.planner.damotorie.service

interface PrincipalExtractor {
    /**
     * Extract the principal that should be used for the token.
     * @param map the source map
     * @return the extracted principal or `null`
     */
    fun extractPrincipal(map: Map<String, Any>): Any
}