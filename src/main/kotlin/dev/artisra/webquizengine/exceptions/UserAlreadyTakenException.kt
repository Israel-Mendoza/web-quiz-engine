package dev.artisra.webquizengine.exceptions

class UserAlreadyTakenException(val takenEmail: String) : RuntimeException()