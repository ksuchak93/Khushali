package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.ContactCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ContactCategory entity.
 */
public interface ContactCategorySearchRepository extends ElasticsearchRepository<ContactCategory, Long> {
}
