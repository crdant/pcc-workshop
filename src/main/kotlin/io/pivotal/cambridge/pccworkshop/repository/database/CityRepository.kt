package io.pivotal.cambridge.pccworkshop.repository.database


import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

import io.pivotal.cambridge.pccworkshop.domain.City
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RestResource
import org.springframework.cache.annotation.Cacheable;

@RepositoryRestResource(collectionResourceRel = "cities", path = "cities")
interface CityRepository : PagingAndSortingRepository<City, Long> {
    @Cacheable("cities")
    @RestResource(path = "name", rel = "name")
    fun findByNameIgnoreCase(@Param("q") name: String, pageable: Pageable): Page<City>

    @Cacheable("cities")
    @RestResource(path = "nameContains", rel = "nameContains")
    fun findByNameContainsIgnoreCase(@Param("q") name: String, pageable: Pageable): Page<City>

    @Cacheable("cities")
    @RestResource(path = "state", rel = "state")
    fun findByStateCodeIgnoreCase(@Param("q") stateCode: String, pageable: Pageable): Page<City>

    @Cacheable("cities")
    @RestResource(path = "postalCode", rel = "postalCode")
    fun findByPostalCode(@Param("q") postalCode: String, pageable: Pageable): Page<City>
}