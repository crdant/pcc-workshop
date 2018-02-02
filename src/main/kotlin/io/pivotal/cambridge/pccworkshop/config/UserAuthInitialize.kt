package io.pivotal.cambridge.pccworkshop.config


import org.apache.geode.LogWriter
import org.apache.geode.distributed.DistributedMember
import org.apache.geode.security.AuthInitialize
import org.apache.geode.security.AuthenticationFailedException

import java.util.Properties

class UserAuthInitialize : AuthInitialize {

    private var securitylog: LogWriter? = null
    private var systemlog: LogWriter? = null

    @Throws(AuthenticationFailedException::class)
    override fun init(systemLogger: LogWriter, securityLogger: LogWriter) {
        this.systemlog = systemLogger
        this.securitylog = securityLogger
    }

    @Throws(AuthenticationFailedException::class)
    override fun getCredentials(props: Properties, server: DistributedMember, isPeer: Boolean): Properties {

        val username = props.getProperty(USERNAME) ?: throw AuthenticationFailedException("UserAuthInitialize: username not set.")

        val password = props.getProperty(PASSWORD) ?: throw AuthenticationFailedException("UserAuthInitialize: password not set.")

        val properties = Properties()
        properties.setProperty(USERNAME, username)
        properties.setProperty(PASSWORD, password)
        return properties
    }

    override fun close() {}

    companion object {

        private val USERNAME = "security-username"
        private val PASSWORD = "security-password"

        @JvmStatic fun create(): AuthInitialize {
            return UserAuthInitialize()
        }
    }
}