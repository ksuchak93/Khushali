package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.CompanyCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CompanyCategory entity.
 */
public interface CompanyCategorySearchRepository extends ElasticsearchRepository<CompanyCategory, Long> {
}
