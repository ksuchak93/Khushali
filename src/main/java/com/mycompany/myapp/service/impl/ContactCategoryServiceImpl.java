package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ContactCategoryService;
import com.mycompany.myapp.domain.ContactCategory;
import com.mycompany.myapp.repository.ContactCategoryRepository;
import com.mycompany.myapp.repository.search.ContactCategorySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ContactCategory.
 */
@Service
@Transactional
public class ContactCategoryServiceImpl implements ContactCategoryService{

    private final Logger log = LoggerFactory.getLogger(ContactCategoryServiceImpl.class);

    private final ContactCategoryRepository contactCategoryRepository;

    private final ContactCategorySearchRepository contactCategorySearchRepository;

    public ContactCategoryServiceImpl(ContactCategoryRepository contactCategoryRepository, ContactCategorySearchRepository contactCategorySearchRepository) {
        this.contactCategoryRepository = contactCategoryRepository;
        this.contactCategorySearchRepository = contactCategorySearchRepository;
    }

    /**
     * Save a contactCategory.
     *
     * @param contactCategory the entity to save
     * @return the persisted entity
     */
    @Override
    public ContactCategory save(ContactCategory contactCategory) {
        log.debug("Request to save ContactCategory : {}", contactCategory);
        ContactCategory result = contactCategoryRepository.save(contactCategory);
        contactCategorySearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the contactCategories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContactCategory> findAll(Pageable pageable) {
        log.debug("Request to get all ContactCategories");
        return contactCategoryRepository.findAll(pageable);
    }

    /**
     *  Get one contactCategory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ContactCategory findOne(Long id) {
        log.debug("Request to get ContactCategory : {}", id);
        return contactCategoryRepository.findOne(id);
    }

    /**
     *  Delete the  contactCategory by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContactCategory : {}", id);
        contactCategoryRepository.delete(id);
        contactCategorySearchRepository.delete(id);
    }

    /**
     * Search for the contactCategory corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContactCategory> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ContactCategories for query {}", query);
        Page<ContactCategory> result = contactCategorySearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
