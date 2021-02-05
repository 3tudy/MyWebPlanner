package com.planner.damotorie.dao

enum class AuthType(val id: Int, val typeName: String) {
    HOMEPAGE(1, "HOMEPAGE"),
    FACEBOOK(2, "FACEBOOK"),
    GOOGLE(3, "GOOGLE"),
    NAVER(4, "NAVER"),
    KAKAO(5, "KAKAO"),
}