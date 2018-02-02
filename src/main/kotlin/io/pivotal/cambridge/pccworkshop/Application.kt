package io.pivotal.cambridge.pccworkshop

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@SpringBootApplication
@EnableGemfireRepositories(basePackageClasses = [ io.pivotal.cambridge.pccworkshop.repository.cache.CityRepository::class ])
@EnableJpaRepositories(basePackageClasses = [ io.pivotal.cambridge.pccworkshop.repository.database.CityRepository::class ])
@EnableCaching
class WorkshopApplication

fun main(args: Array<String>) {
    SpringApplication.run(WorkshopApplication::class.java, *args)
}

