package com.planner.damotorie.service
/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.commons.logging.LogFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.client.OAuth2RestOperations
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.OAuth2Request
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices
import org.springframework.util.Assert
import java.lang.Exception
import java.util.*
import kotlin.collections.HashMap

/**
 * [ResourceServerTokenServices] that uses a user info REST service.
 *
 * @author Dave Syer
 * @since 1.3.0
 */
class UserInfoTokenServices(private val userInfoEndpointUrl: String, private val clientId: String) :
    ResourceServerTokenServices {
    protected val logger = LogFactory.getLog(javaClass)
    var restTemplate: OAuth2RestOperations? = null
    var tokenType = DefaultOAuth2AccessToken.BEARER_TYPE
    var authoritiesExtractor: AuthoritiesExtractor = FixedAuthoritiesExtractor()
    var principalExtractor: PrincipalExtractor = FixedPrincipalExtractor()

    @Throws(AuthenticationException::class, InvalidTokenException::class)
    override fun loadAuthentication(accessToken: String): OAuth2Authentication {
        val map = getMap(userInfoEndpointUrl, accessToken)
        if (map.containsKey("error")) {
            if (logger.isDebugEnabled) {
                logger.debug("userinfo returned error: " + map["error"])
            }
            throw InvalidTokenException(accessToken)
        }
        return extractAuthentication(map)
    }

    private fun extractAuthentication(map: Map<String, Any>): OAuth2Authentication {
        val principal = getPrincipal(map)
        val authorities: List<GrantedAuthority> = authoritiesExtractor.extractAuthorities(map)
        val request = OAuth2Request(null, clientId, null, true, null, null, null, null, null)
        val token = UsernamePasswordAuthenticationToken(
            principal, "N/A",
            authorities
        )
        token.details = map
        return OAuth2Authentication(request, token)
    }

    /**
     * Return the principal that should be used for the token. The default implementation
     * delegates to the [PrincipalExtractor].
     * @param map the source map
     * @return the principal or &quot;unknown&quot;
     */
    protected fun getPrincipal(map: Map<String, Any>): Any {
        val principal: Any = principalExtractor.extractPrincipal(map)
        return principal ?: "unknown"
    }

    override fun readAccessToken(accessToken: String): OAuth2AccessToken {
        throw UnsupportedOperationException("Not supported: read access token")
    }

    private fun getMap(path: String, accessToken: String): Map<String, Any> {
        // TODO 삭제 필요
        val typeInstance: Map<String, Any> = HashMap<String, Any>()
        if (logger.isDebugEnabled) {
            logger.debug("Getting user info from: $path")
        }
        return try {
            var restTemplate = restTemplate
            if (restTemplate == null) {
                val resource = BaseOAuth2ProtectedResourceDetails()
                resource.clientId = clientId
                restTemplate = OAuth2RestTemplate(resource)
            }
            val existingToken = restTemplate.oAuth2ClientContext.accessToken
            if (existingToken == null || accessToken != existingToken.value) {
                val token = DefaultOAuth2AccessToken(accessToken)
                token.tokenType = tokenType
                restTemplate.oAuth2ClientContext.accessToken = token
            }
            restTemplate.getForEntity(path, typeInstance.javaClass).getBody()
//            restTemplate.getForObject(path, typeInstance.javaClass)
        } catch (ex: Exception) {
            logger.warn("Could not fetch user details: " + ex.javaClass + ", " + ex.message)
            Collections.singletonMap<String, Any>("error", "Could not fetch user details")
        }
    }
}