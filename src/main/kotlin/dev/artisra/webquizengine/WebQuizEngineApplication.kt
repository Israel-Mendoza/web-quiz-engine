package dev.artisra.webquizengine

import dev.artisra.webquizengine.services.QuizService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

@SpringBootApplication
class WebQuizEngine

fun main(args: Array<String>) {
    runApplication<WebQuizEngine>(*args)
}

@Component
class Runner(private val quizService: QuizService) : CommandLineRunner {
    override fun run(vararg args: String?) {
//        repeat(3) {
//            val page = quizService.getQuizPage(it)
//            logger.info("Page $it")
//            logger.info(page.toString())
//        }
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(Runner::class.java)
    }
}