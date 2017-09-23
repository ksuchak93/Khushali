package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.SalesOrderProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing SalesOrderProduct.
 */
public interface SalesOrderProductService {

    /**
     * Save a salesOrderProduct.
     *
     * @param salesOrderProduct the entity to save
     * @return the persisted entity
     */
    SalesOrderProduct save(SalesOrderProduct salesOrderProduct);

    /**
     *  Get all the salesOrderProducts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SalesOrderProduct> findAll(Pageable pageable);

    /**
     *  Get the "id" salesOrderProduct.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SalesOrderProduct findOne(Long id);

    /**
     *  Delete the "id" salesOrderProduct.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the salesOrderProduct corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SalesOrderProduct> search(String query, Pageable pageable);
}
