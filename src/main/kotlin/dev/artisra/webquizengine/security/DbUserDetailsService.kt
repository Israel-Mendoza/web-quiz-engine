package dev.artisra.webquizengine.security

import dev.artisra.webquizengine.respositories.AppUserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class DbUserDetailsService(private val appUserRepository: AppUserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val appUser = appUserRepository.findByEmail(username).firstOrNull()
        logger.info("Loaded user from database: $appUser")
        return User.withUsername(appUser?.email)
            .password(appUser?.password)
            .build()
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(DbUserDetailsService::class.java)
    }
}