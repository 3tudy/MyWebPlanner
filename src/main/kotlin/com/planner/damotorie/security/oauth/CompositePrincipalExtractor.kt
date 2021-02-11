package com.planner.damotorie.security.oauth

import com.planner.damotorie.dao.AuthType
import java.lang.Exception

class CompositePrincipalExtractor(val extractors: List<PrincipalExtractor>) : PrincipalExtractor {

    override fun support(type: AuthType): Boolean {
        return extractors.any {
            extractor: PrincipalExtractor -> extractor.support(type)
        }
    }

    override fun extractPrincipal(map: Map<String, Any>, authType: AuthType): SocialLoginPrincipal {

        for (extractor: PrincipalExtractor in extractors) {
            if (extractor.support(authType)) {
                return extractor.extractPrincipal(map, authType)
            }
        }

        throw Exception("AuthType ${authType} is not supported")
    }
}