package dev.artisra.webquizengine.models

import java.time.LocalDateTime

data class ResolvedQuizResponse(val id: Int, val completedAt: LocalDateTime)