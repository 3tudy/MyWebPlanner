package com.planner.damotorie.service

import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails
import org.springframework.security.oauth2.common.AuthenticationScheme

class ClientResources {

    val client: AuthorizationCodeResourceDetails = AuthorizationCodeResourceDetails()

    constructor(properties: Map<String, String>) {
        client.clientId = properties["clientId"]
        client.clientSecret = properties["clientSecret"]
        client.accessTokenUri = properties["accessTokenUri"]
        client.userAuthorizationUri = properties["userAuthorizationUri"]
        client.tokenName = properties["tokenName"]
        client.authenticationScheme = AuthenticationScheme.valueOf(properties["authenticationScheme"]!!)
        client.clientAuthenticationScheme = AuthenticationScheme.valueOf(properties["clientAuthenticationScheme"]!!)

        if ("scope" in properties.keys)
            client.scope = properties["scope"]!!.split(",")
    }
}