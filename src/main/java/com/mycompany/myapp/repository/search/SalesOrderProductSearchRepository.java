package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.SalesOrderProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SalesOrderProduct entity.
 */
public interface SalesOrderProductSearchRepository extends ElasticsearchRepository<SalesOrderProduct, Long> {
}
