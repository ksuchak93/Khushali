package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ShipProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ShipProduct.
 */
public interface ShipProductService {

    /**
     * Save a shipProduct.
     *
     * @param shipProduct the entity to save
     * @return the persisted entity
     */
    ShipProduct save(ShipProduct shipProduct);

    /**
     *  Get all the shipProducts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ShipProduct> findAll(Pageable pageable);

    /**
     *  Get the "id" shipProduct.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ShipProduct findOne(Long id);

    /**
     *  Delete the "id" shipProduct.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the shipProduct corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ShipProduct> search(String query, Pageable pageable);
}
