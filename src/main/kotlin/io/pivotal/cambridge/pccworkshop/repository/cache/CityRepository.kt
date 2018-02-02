package io.pivotal.cambridge.pccworkshop.repository.cache


import io.pivotal.cambridge.pccworkshop.domain.City
import org.springframework.data.gemfire.repository.GemfireRepository

interface CityRepository : GemfireRepository<City, Long>
