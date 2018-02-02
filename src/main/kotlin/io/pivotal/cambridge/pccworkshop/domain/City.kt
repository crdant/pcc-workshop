package io.pivotal.cambridge.pccworkshop.domain

import org.springframework.data.gemfire.mapping.annotation.Region
import javax.persistence.*


@Entity
@Table(name = "city")
@Region("cities")
data class City (
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var name: String? = null,

    @Column(nullable = false)
    var county: String? = null,

    @Column(nullable = false)
    var stateCode: String? = null,

    @Column(nullable = false)
    var postalCode: String? = null,

    @Column
    var latitude: String? = null,

    @Column
    var longitude: String? = null

)