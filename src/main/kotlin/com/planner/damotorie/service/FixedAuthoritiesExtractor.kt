package com.planner.damotorie.service

import java.util.ArrayList

import org.springframework.security.core.authority.AuthorityUtils

import org.springframework.security.core.GrantedAuthority


/**
 * Default implementation of [AuthoritiesExtractor]. Extracts the authorities from
 * the map with the key `authorities`. If no such value exists, a single
 * `ROLE_USER` authority is returned.
 *
 * @author Dave Syer
 * @since 1.3.0
 */
class FixedAuthoritiesExtractor : AuthoritiesExtractor {
    override fun extractAuthorities(map: Map<String, Any>): List<GrantedAuthority> {
        var authorities = "ROLE_MEMBER"
//        if (map.containsKey(AUTHORITIES)) {
//            authorities = asAuthorities(map[AUTHORITIES])
//        }
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities)
    }

//    private fun asAuthorities(`object`: Any?): String {
//        var `object` = `object`
//        var arr: Array<Any> = emptyArray()
//        val authorities: MutableList<Any?> = ArrayList()
//        if (`object` is Collection<*>) {
//            `arr` = `object`.toTypedArray<Any>()
//        }
//        if (Array.isArray(`arr`)) {
//            val array = `object` as Array<Any>?
//            for (value in array!!) {
//                if (value is String) {
//                    authorities.add(value)
//                } else if (value is Map<*, *>) {
//                    authorities.add(asAuthority(value))
//                } else {
//                    authorities.add(value)
//                }
//            }
//            return StringUtils.collectionToCommaDelimitedString(authorities)
//        }
//        return `object`.toString()
//    }

    private fun asAuthority(map: Map<*, *>): Any? {
        if (map.size == 1) {
            return map.values.iterator().next()
        }
        for (key in AUTHORITY_KEYS) {
            if (map.containsKey(key)) {
                return map[key]
            }
        }
        return map
    }

    companion object {
        private const val AUTHORITIES = "authorities"
        private val AUTHORITY_KEYS = arrayOf("authority", "role", "value")
    }
}