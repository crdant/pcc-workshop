package io.pivotal.cambridge.pccworkshop

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@SpringBootApplication
// @EnableJpaRepositories
@RestController
class WorkshopApplication {
    @RequestMapping("/greeting")
    fun hello( @RequestParam(name="name", defaultValue="World") name : String ) : String {
        return "Hello ${name}!"
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(WorkshopApplication::class.java, *args)
}

