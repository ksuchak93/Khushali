package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.CompanyCategoryService;
import com.mycompany.myapp.domain.CompanyCategory;
import com.mycompany.myapp.repository.CompanyCategoryRepository;
import com.mycompany.myapp.repository.search.CompanyCategorySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CompanyCategory.
 */
@Service
@Transactional
public class CompanyCategoryServiceImpl implements CompanyCategoryService{

    private final Logger log = LoggerFactory.getLogger(CompanyCategoryServiceImpl.class);

    private final CompanyCategoryRepository companyCategoryRepository;

    private final CompanyCategorySearchRepository companyCategorySearchRepository;

    public CompanyCategoryServiceImpl(CompanyCategoryRepository companyCategoryRepository, CompanyCategorySearchRepository companyCategorySearchRepository) {
        this.companyCategoryRepository = companyCategoryRepository;
        this.companyCategorySearchRepository = companyCategorySearchRepository;
    }

    /**
     * Save a companyCategory.
     *
     * @param companyCategory the entity to save
     * @return the persisted entity
     */
    @Override
    public CompanyCategory save(CompanyCategory companyCategory) {
        log.debug("Request to save CompanyCategory : {}", companyCategory);
        CompanyCategory result = companyCategoryRepository.save(companyCategory);
        companyCategorySearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the companyCategories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompanyCategory> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyCategories");
        return companyCategoryRepository.findAll(pageable);
    }

    /**
     *  Get one companyCategory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CompanyCategory findOne(Long id) {
        log.debug("Request to get CompanyCategory : {}", id);
        return companyCategoryRepository.findOne(id);
    }

    /**
     *  Delete the  companyCategory by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyCategory : {}", id);
        companyCategoryRepository.delete(id);
        companyCategorySearchRepository.delete(id);
    }

    /**
     * Search for the companyCategory corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompanyCategory> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CompanyCategories for query {}", query);
        Page<CompanyCategory> result = companyCategorySearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
