package com.mycompany.myapp.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache("users", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Country.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Address.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.ProductCategory.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.ProductDemo.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.ProductDemo.class.getName() + ".salesOrders", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.ContactCategory.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.CompanyCategory.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Company.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Company.class.getName() + ".addresses", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Company.class.getName() + ".companyCategories", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Contact.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Contact.class.getName() + ".addresses", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.SalesOrder.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.SalesOrder.class.getName() + ".salesOrderProducts", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.SalesOrderProduct.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Shipment.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Shipment.class.getName() + ".shipProducts", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Shipment.class.getName() + ".salesOrders", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.ShipShipmentStatus.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.ShipProduct.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
