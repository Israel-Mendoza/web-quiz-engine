package dev.artisra.webquizengine.services

import dev.artisra.webquizengine.entities.AppUser
import dev.artisra.webquizengine.mappers.AppUserMapper
import dev.artisra.webquizengine.models.AppUserRegistrationRequest
import dev.artisra.webquizengine.respositories.AppUserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class AppUserService(
    private val appUserRepository: AppUserRepository,
    private val appUserMapper: AppUserMapper
) {
    fun emailIsTaken(username: String): Boolean {
        val appUserInDb = getUserByEmail(username)
        return appUserInDb != null
    }

    fun registerUser(registrationRequest: AppUserRegistrationRequest): AppUser {
        val newAppUser = appUserMapper.requestToEntity(registrationRequest)
        return appUserRepository.save(newAppUser)
    }

    fun getUserByUserDetails(userDetails: UserDetails): AppUser? {
        val email = userDetails.username!!
        return getUserByEmail(email)
    }

    private fun getUserByEmail(email: String) = appUserRepository.findByEmail(email).firstOrNull()

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AppUserService::class.java)
    }
}