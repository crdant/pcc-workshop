package io.pivotal.cambridge.pccworkshop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@SpringBootApplication
@RestController
@EnableJpaRepositories
@EnableCaching
class Application {
    @RequestMapping("/greeting")
    fun hello( @RequestParam(name="name", defaultValue="World") name : String ) : String {
        return "Hello ${name}!"
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

