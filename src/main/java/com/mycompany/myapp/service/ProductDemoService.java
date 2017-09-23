package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ProductDemo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ProductDemo.
 */
public interface ProductDemoService {

    /**
     * Save a productDemo.
     *
     * @param productDemo the entity to save
     * @return the persisted entity
     */
    ProductDemo save(ProductDemo productDemo);

    /**
     *  Get all the productDemos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProductDemo> findAll(Pageable pageable);

    /**
     *  Get the "id" productDemo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProductDemo findOne(Long id);

    /**
     *  Delete the "id" productDemo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the productDemo corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProductDemo> search(String query, Pageable pageable);
}
