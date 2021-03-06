package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Company.
 */
public interface CompanyService {

    /**
     * Save a company.
     *
     * @param company the entity to save
     * @return the persisted entity
     */
    Company save(Company company);

    /**
     *  Get all the companies.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Company> findAll(Pageable pageable);

    /**
     *  Get the "id" company.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Company findOne(Long id);

    /**
     *  Delete the "id" company.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the company corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Company> search(String query, Pageable pageable);
}
