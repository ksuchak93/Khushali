package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.ProductDemo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProductDemo entity.
 */
public interface ProductDemoSearchRepository extends ElasticsearchRepository<ProductDemo, Long> {
}
