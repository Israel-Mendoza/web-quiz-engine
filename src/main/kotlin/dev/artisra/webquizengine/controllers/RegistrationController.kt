package dev.artisra.webquizengine.controllers

import dev.artisra.webquizengine.exceptions.UserAlreadyTakenException
import dev.artisra.webquizengine.mappers.AppUserMapper
import dev.artisra.webquizengine.models.AppUserRegistrationResponse
import dev.artisra.webquizengine.models.AppUserRegistrationRequest
import dev.artisra.webquizengine.services.AppUserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class RegistrationController(
    private val appUserService: AppUserService,
    private val appUserMapper: AppUserMapper,
) {
    @PostMapping("/register")
    fun registerNewUser(@Valid @RequestBody registrationReq: AppUserRegistrationRequest): ResponseEntity<AppUserRegistrationResponse> {
        if (appUserService.emailIsTaken(registrationReq.email)) throw UserAlreadyTakenException(registrationReq.email)
        val registeredAppUser = appUserService.registerUser(registrationReq)
        return ResponseEntity.ok(appUserMapper.entityToClient(registeredAppUser))
    }
}