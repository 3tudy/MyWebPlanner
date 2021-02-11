package com.planner.damotorie.security.oauth

import com.planner.damotorie.dao.AuthType
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails
import org.springframework.security.oauth2.common.AuthenticationScheme
import java.lang.Exception

class ClientResources {

    val authType: AuthType
    val client: AuthorizationCodeResourceDetails = AuthorizationCodeResourceDetails()
    val userInfoEndpointUri: String

    constructor(properties: Map<String, String>) {
        authType = AuthType.valueOf(properties["authType"]!!.toUpperCase())

        client.clientId = properties["clientId"]
        client.clientSecret = properties["clientSecret"]
        client.accessTokenUri = properties["accessTokenUri"]
        client.userAuthorizationUri = properties["userAuthorizationUri"]
        client.tokenName = properties["tokenName"]
        client.authenticationScheme = AuthenticationScheme.valueOf(properties["authenticationScheme"]!!)
        client.clientAuthenticationScheme = AuthenticationScheme.valueOf(properties["clientAuthenticationScheme"]!!)

        if ("scope" in properties.keys)
            client.scope = properties["scope"]!!.split(",")

        this.userInfoEndpointUri = properties["userInfoEndpointUri"] ?: throw Exception("UserInfoEndpointUri should be provided")
    }
}