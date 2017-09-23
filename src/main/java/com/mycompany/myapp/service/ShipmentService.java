package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Shipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Shipment.
 */
public interface ShipmentService {

    /**
     * Save a shipment.
     *
     * @param shipment the entity to save
     * @return the persisted entity
     */
    Shipment save(Shipment shipment);

    /**
     *  Get all the shipments.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Shipment> findAll(Pageable pageable);

    /**
     *  Get the "id" shipment.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Shipment findOne(Long id);

    /**
     *  Delete the "id" shipment.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the shipment corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Shipment> search(String query, Pageable pageable);
}
