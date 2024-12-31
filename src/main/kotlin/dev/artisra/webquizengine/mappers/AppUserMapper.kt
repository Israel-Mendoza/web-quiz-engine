package dev.artisra.webquizengine.mappers

import dev.artisra.webquizengine.entities.AppUser
import dev.artisra.webquizengine.models.AppUserRegistrationResponse
import dev.artisra.webquizengine.models.AppUserRegistrationRequest

interface AppUserMapper {
    fun requestToEntity(registrationRequest: AppUserRegistrationRequest): AppUser
    fun entityToClient(appUser: AppUser): AppUserRegistrationResponse
}