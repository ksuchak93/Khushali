package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.ShipProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ShipProduct entity.
 */
public interface ShipProductSearchRepository extends ElasticsearchRepository<ShipProduct, Long> {
}
