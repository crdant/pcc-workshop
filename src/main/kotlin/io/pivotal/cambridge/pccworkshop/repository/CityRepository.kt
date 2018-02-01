package io.pivotal.cambridge.pccworkshop.repository


import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

import io.pivotal.cambridge.pccworkshop.domain.City

@RepositoryRestResource(collectionResourceRel = "cities", path = "cities")
interface CityRepository : PagingAndSortingRepository<City, Long>