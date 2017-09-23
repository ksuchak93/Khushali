package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CompanyCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CompanyCategory.
 */
public interface CompanyCategoryService {

    /**
     * Save a companyCategory.
     *
     * @param companyCategory the entity to save
     * @return the persisted entity
     */
    CompanyCategory save(CompanyCategory companyCategory);

    /**
     *  Get all the companyCategories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CompanyCategory> findAll(Pageable pageable);

    /**
     *  Get the "id" companyCategory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CompanyCategory findOne(Long id);

    /**
     *  Delete the "id" companyCategory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the companyCategory corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CompanyCategory> search(String query, Pageable pageable);
}
