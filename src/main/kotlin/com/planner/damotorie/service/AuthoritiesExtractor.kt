package com.planner.damotorie.service

import org.springframework.security.core.GrantedAuthority


interface AuthoritiesExtractor {
    /**
     * Extract the authorities from the resource server's response.
     * @param map the response
     * @return the extracted authorities
     */
    fun extractAuthorities(map: Map<String, Any>): List<GrantedAuthority>
}