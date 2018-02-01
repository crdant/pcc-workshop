package io.pivotal.cambridge.pccworkshop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@SpringBootApplication
@RestController
@EnableJpaRepositories
class Application {
    @RequestMapping("/greeting")
    fun hello( @RequestParam(name="name", defaultValue="World") name : String ) : String {
        return "Hello ${name}!"
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

