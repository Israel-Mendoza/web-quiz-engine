package dev.artisra.webquizengine.exceptions

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import dev.artisra.webquizengine.models.CustomErrorMessage
import jakarta.validation.ConstraintViolationException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(QuizNotFound::class)
    fun handleQuizNotFound(ex: QuizNotFound, req: WebRequest) =
        ResponseEntity(CustomErrorMessage("Quiz not found"), HttpStatus.NOT_FOUND)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException, req: WebRequest): ResponseEntity<Map<String, String>> {
        val errors = mutableMapOf<String, String>()
        ex.bindingResult.fieldErrors.forEach { error ->
            errors[error.field] = error.defaultMessage ?: "Validation error"
        }
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException): ResponseEntity<CustomErrorMessage> {
        val errorMessage = when (val cause = ex.cause) {
            is JsonMappingException -> {
                val fieldName = cause.path.joinToString(".") { it.fieldName ?: "" }
                "Error parsing field: $fieldName"
            }
            is JsonParseException -> "Malformed JSON request"
            else -> "Error parsing JSON"
        }

        val customError = CustomErrorMessage(errorMessage)
        return ResponseEntity(customError, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UserAlreadyTakenException::class)
    fun handlerUserAlreadyTaken(ex: UserAlreadyTakenException): ResponseEntity<CustomErrorMessage> {
        val errorMessage = "Email ${ex.takenEmail} is already taken!"
        return ResponseEntity(CustomErrorMessage(errorMessage), HttpStatus.BAD_REQUEST)
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ExceptionHandler::class.java)
    }
}