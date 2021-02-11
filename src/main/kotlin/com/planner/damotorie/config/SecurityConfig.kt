package com.planner.damotorie.config

import com.planner.damotorie.dao.AuthType
import com.planner.damotorie.dao.UserRepository
import com.planner.damotorie.security.oauth.ClientResources
import com.planner.damotorie.security.oauth.OAuth2AuthenticationSuccessHandler
import com.planner.damotorie.service.MemberService
import com.planner.damotorie.service.UserInfoTokenServices
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.RedirectStrategy
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.context.SecurityContextPersistenceFilter
import org.springframework.web.filter.CompositeFilter
import javax.servlet.Filter

@Configuration
@EnableWebSecurity
@EnableOAuth2Client
open class SecurityConfig(val memberService: MemberService, val passwordEncoder: PasswordEncoder, val oAuth2ClientContext: OAuth2ClientContext,
                          val oAuth2ClientContextFilter: OAuth2ClientContextFilter, val userRepository: UserRepository): WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(memberService)?.passwordEncoder(passwordEncoder)
    }

    override fun configure(web: WebSecurity?) {
        web?.ignoring()?.antMatchers("/resources/**")
    }

    override fun configure(http: HttpSecurity?) {
        http?.headers()
            ?.frameOptions()
            ?.sameOrigin()
            ?.and()
            ?.authorizeRequests()
            ?.antMatchers("/register", "/admin/**", "/login/**")?.anonymous()
            ?.antMatchers("/**")?.hasRole("MEMBER")
            ?.and()
            ?.formLogin()
            ?.and()
            ?.logout()
                ?.logoutUrl("/logout")
                ?.logoutSuccessUrl("/plan")
                ?.invalidateHttpSession(true)
            ?.and()
            ?.csrf()
                ?.disable()
            ?.exceptionHandling()
                ?.accessDeniedPage("/error/401")

        http?.addFilterBefore(socialLoginFilter(), BasicAuthenticationFilter::class.java)
            ?.addFilterAfter(oAuth2ClientContextFilter, SecurityContextPersistenceFilter::class.java)
    }

    private fun socialLoginFilter(): Filter {
        val socialLoginFilters: CompositeFilter = CompositeFilter()
        val filters: List<Filter> = listOf(
            socialLoginFilter(facebook(), "/login/facebook", AuthType.FACEBOOK),
            socialLoginFilter(google(), "/login/google", AuthType.GOOGLE),
            socialLoginFilter(naver(), "/login/naver", AuthType.NAVER),
            socialLoginFilter(kakao(), "/login/kakao", AuthType.KAKAO)
        )

        socialLoginFilters.setFilters(filters)
        return socialLoginFilters
    }

    private fun socialLoginFilter(clientResources: ClientResources, path: String, authType: AuthType): Filter {
        val filter: OAuth2ClientAuthenticationProcessingFilter = OAuth2ClientAuthenticationProcessingFilter(path)
        val restTemplate: OAuth2RestTemplate = OAuth2RestTemplate(clientResources.client, oAuth2ClientContext)
        val messageConverter: MappingJackson2HttpMessageConverter = MappingJackson2HttpMessageConverter()
        messageConverter.supportedMediaTypes = listOf(MediaType.ALL)
        restTemplate.messageConverters = listOf(messageConverter)
        filter.restTemplate = restTemplate

        val tokenServices: UserInfoTokenServices = UserInfoTokenServices(clientResources)

        tokenServices.restTemplate = restTemplate
        filter.setTokenServices(tokenServices)

        filter.setAuthenticationSuccessHandler(OAuth2AuthenticationSuccessHandler(userRepository))
        return filter
    }

    @Bean
    open fun facebook(): ClientResources {
        val properties: Map<String, String> = mapOf<String, String>(
            "authType" to "facebook",
            "clientId" to "",
            "clientSecret" to "",
            "accessTokenUri" to "https://graph.facebook.com/oauth/access_token",
            "userAuthorizationUri" to "https://www.facebook.com/v9.0/dialog/oauth",
            "userInfoEndpointUri" to "https://graph.facebook.com/me?fields=id",
            "tokenName" to "oauth_token",
            "authenticationScheme" to "query",
            "clientAuthenticationScheme" to "form"
        )
        return ClientResources(properties)
    }

    @Bean
    open fun google(): ClientResources {
        val properties: Map<String, String> = mapOf<String, String>(
            "authType" to "google",
            "clientId" to "",
            "clientSecret" to "",
            "accessTokenUri" to "https://oauth2.googleapis.com/token",
            "userAuthorizationUri" to "https://accounts.google.com/o/oauth2/v2/auth",
            "userInfoEndpointUri" to "https://www.googleapis.com/oauth2/v3/userinfo",
            "authenticationScheme" to "header",
            "clientAuthenticationScheme" to "form",
            "scope" to "email,profile"
        )

        return ClientResources(properties)
    }

    @Bean
    open fun naver(): ClientResources {
        val properties: Map<String, String> = mapOf<String, String>(
            "authType" to "naver",
            "clientId" to "",
            "clientSecret" to "",
            "accessTokenUri" to "https://nid.naver.com/oauth2.0/token",
            "userAuthorizationUri" to "https://nid.naver.com/oauth2.0/authorize",
            "userInfoEndpointUri" to "https://openapi.naver.com/v1/nid/me",
            "authenticationScheme" to "header",
            "clientAuthenticationScheme" to "form"
        )

        return ClientResources(properties)
    }

    @Bean
    open fun kakao(): ClientResources {
        val properties: Map<String, String> = mapOf<String, String>(
            "authType" to "kakao",
            "clientId" to "",
            "clientSecret" to "",
            "accessTokenUri" to "https://kauth.kakao.com/oauth/token",
            "userAuthorizationUri" to "https://kauth.kakao.com/oauth/authorize",
            "userInfoEndpointUri" to "https://kapi.kakao.com/v2/user/me",
            "authenticationScheme" to "header",
            "clientAuthenticationScheme" to "form"
        )

        return ClientResources(properties)
    }
}

