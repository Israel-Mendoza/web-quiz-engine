package dev.artisra.webquizengine.mappers

import dev.artisra.webquizengine.entities.AppUser
import dev.artisra.webquizengine.models.AppUserRegistrationResponse
import dev.artisra.webquizengine.models.AppUserRegistrationRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AppUserMapperImpl(private val passwordEncoder: PasswordEncoder) : AppUserMapper {
    override fun requestToEntity(registrationRequest: AppUserRegistrationRequest): AppUser {
        return AppUser(
            email = registrationRequest.email,
            password = passwordEncoder.encode(registrationRequest.password)
        )
    }

    override fun entityToClient(appUser: AppUser) = AppUserRegistrationResponse(appUser.id, appUser.email)
}