package io.pivotal.cambridge.pccworkshop.repository

import io.pivotal.cambridge.pccworkshop.domain.City
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "cities", path = "cities")
class SlowCityRepository ( @Autowired val cityRepository : CityRepository ) : CityRepository by cityRepository {

    override fun findAll(): MutableIterable<City> {
        simulateSlowService()
        println("In the slow repository")
        return cityRepository.findAll()
    }

    // Don't do this at home
    private fun simulateSlowService() {
        try {
            val time = 3000L
            Thread.sleep(time)
        } catch (e: InterruptedException) {
            throw IllegalStateException(e)
        }

    }
}