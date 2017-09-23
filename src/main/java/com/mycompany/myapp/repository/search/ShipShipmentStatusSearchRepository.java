package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.ShipShipmentStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ShipShipmentStatus entity.
 */
public interface ShipShipmentStatusSearchRepository extends ElasticsearchRepository<ShipShipmentStatus, Long> {
}
