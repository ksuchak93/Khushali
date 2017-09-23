package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ContactCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ContactCategory.
 */
public interface ContactCategoryService {

    /**
     * Save a contactCategory.
     *
     * @param contactCategory the entity to save
     * @return the persisted entity
     */
    ContactCategory save(ContactCategory contactCategory);

    /**
     *  Get all the contactCategories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ContactCategory> findAll(Pageable pageable);

    /**
     *  Get the "id" contactCategory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ContactCategory findOne(Long id);

    /**
     *  Delete the "id" contactCategory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the contactCategory corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ContactCategory> search(String query, Pageable pageable);
}
