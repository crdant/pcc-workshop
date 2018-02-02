package io.pivotal.cambridge.pccworkshop.config

import io.pivotal.cambridge.pccworkshop.domain.City
import io.pivotal.spring.cloud.service.gemfire.GemfireServiceConnectorConfig
import org.apache.geode.cache.GemFireCache
import org.apache.geode.cache.client.ClientCache
import org.apache.geode.cache.client.ClientRegionShortcut
import org.apache.geode.pdx.ReflectionBasedAutoSerializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.CloudFactory
import org.springframework.cloud.service.ServiceConnectorConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.gemfire.cache.GemfireCacheManager;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean
import org.apache.geode.distributed.Locator.getLocators
import io.pivotal.spring.cloud.service.common.GemfireServiceInfo
import org.apache.geode.cache.client.ClientCacheFactory
import org.springframework.cloud.Cloud
import org.springframework.cloud.config.java.AbstractCloudConfig
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import java.net.URI
import javax.sql.DataSource


@Configuration
@EnableCaching
@Profile("cloud")
class CityCloudConfig : AbstractCloudConfig() {

    private val SECURITY_CLIENT = "security-client-auth-init"
    private val SECURITY_USERNAME = "security-username"
    private val SECURITY_PASSWORD = "security-password"

    val dataSource : DataSource
        @Bean(name = [ "jpaStore" ])
        get () {
            return connectionFactory().dataSource()
        }
      val gemfireClientCache: ClientCache
        @Bean(name = [ "gemfireCache" ])
        get() {
            val cloudFactory = CloudFactory()
            val cloud = cloudFactory.cloud
            val cacheService = cloud.getServiceInfo("workshop-cache") as PCCServiceInfo

            val factory = ClientCacheFactory();
            for ( locator in cacheService.locators ?: emptyArray<URI>()) {
                factory.addPoolLocator(locator.getHost(), locator.getPort());
            }
            factory.set(SECURITY_CLIENT, "io.pivotal.cambridge.pccworkshop.config.UserAuthInitialize.create");
            factory.set(SECURITY_USERNAME, cacheService.username);
            factory.set(SECURITY_PASSWORD, cacheService.password);
            factory.setPdxSerializer(ReflectionBasedAutoSerializer(".*"));
            factory.setPoolSubscriptionEnabled(true); // to enable CQ
            return factory.create();
        }

    @Bean(name = [ "cities" ])
    fun cityRegion(@Autowired clientCache: GemFireCache): ClientRegionFactoryBean<String, City> {
        val cityRegionFactory = ClientRegionFactoryBean<String, City>()
        cityRegionFactory.setCache(clientCache)
        cityRegionFactory.setShortcut(ClientRegionShortcut.PROXY)
        cityRegionFactory.setName("cities")

        return cityRegionFactory
    }

    @Bean(name = [ "cacheManager" ])
    fun createGemfireCacheManager(@Autowired clientCache: ClientCache): GemfireCacheManager {
        val gemfireCacheManager = GemfireCacheManager()
        gemfireCacheManager.setCache(clientCache)

        return gemfireCacheManager
    }

}