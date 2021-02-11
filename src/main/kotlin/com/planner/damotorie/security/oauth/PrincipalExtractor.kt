package com.planner.damotorie.security.oauth

import com.planner.damotorie.dao.AuthType
import com.sun.xml.bind.v2.schemagen.episode.Klass

interface PrincipalExtractor {

    fun support(type: AuthType): Boolean

    /**
     * Extract the principal that should be used for the token.
     * @param map the source map
     * @return the extracted principal or `null`
     */
    fun extractPrincipal(map: Map<String, Any>, authType: AuthType): Any
}