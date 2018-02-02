package io.pivotal.cambridge.pccworkshop.config


import org.springframework.cloud.cloudfoundry.CloudFoundryServiceInfoCreator
import org.springframework.cloud.cloudfoundry.Tags
import org.springframework.cloud.service.BaseServiceInfo
import java.net.URI
import java.net.URISyntaxException
import java.util.*
import java.util.regex.Pattern


class PCCServiceInfo(id: String, locators: List<String>, users: List<Map<String, String>>) : BaseServiceInfo(id) {

    private val p = Pattern.compile("(.*)\\[(\\d*)\\]")

    var locators: Array<URI>? = null
        private set
    private var user: PCCUser? = null

    val username: String?
        get() = if (user != null) user!!.username else null

    val password: String?
        get() = if (user != null) user!!.password else null

    init {
        parseLocators(locators)
        parseUsers(users)
    }

    private fun parseLocators(locators: List<String>) {
        val uris = ArrayList<URI>(locators.size)

        for (locator in locators) {
            uris.add(parseLocator(locator))
        }

        this.locators = uris.toTypedArray()
    }

    @Throws(IllegalArgumentException::class)
    private fun parseLocator(locator: String): URI {
        val m = p.matcher(locator)
        if (!m.find()) {
            throw IllegalArgumentException("Could not parse locator url. Expected format host[port], received: " + locator)
        } else {
            if (m.groupCount() != 2) {
                throw IllegalArgumentException("Could not parse locator url. Expected format host[port], received: " + locator)
            }
            try {
                return URI("locator://" + m.group(1) + ":" + m.group(2))
            } catch (e: URISyntaxException) {
                throw IllegalArgumentException("Malformed URL " + locator)
            }

        }
    }

    private fun parseUsers(users: List<Map<String, String>>?) {
        if (users == null) return

        for (map in users) {
            val user = PCCUser(map)

            if (user.isClusterOperator) {
                this.user = user
                break
            }
        }
    }

}

class PCCServiceInfoCreator : CloudFoundryServiceInfoCreator<org.springframework.cloud.service.ServiceInfo>(Tags("gemfire")) {

    override fun createServiceInfo(serviceData: Map<String, Any>): org.springframework.cloud.service.ServiceInfo {
        val id = serviceData["name"] as String

        val credentials = getCredentials(serviceData)

        val locators = credentials["locators"] as List<String>
        val users = credentials["users"] as List<Map<String, String>>

        return PCCServiceInfo(id, locators, users)
    }

    override fun accept(serviceData: Map<String, Any>): Boolean {
        return containsLocators(serviceData) || super.accept(serviceData)
    }

    private fun containsLocators(serviceData: Map<String, Any>): Boolean {
        val locators = getCredentials(serviceData)["locators"]
        return locators != null
    }
}


data class PCCUser( val map: Map<String, String>) {
    val username = map["username"]
    val password = map["password"]

    val isClusterOperator: Boolean
        get() = username != null && username.startsWith("cluster_operator")

    init {
        println(this)
    }
}