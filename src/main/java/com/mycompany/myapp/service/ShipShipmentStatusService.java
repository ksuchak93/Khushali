package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ShipShipmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ShipShipmentStatus.
 */
public interface ShipShipmentStatusService {

    /**
     * Save a shipShipmentStatus.
     *
     * @param shipShipmentStatus the entity to save
     * @return the persisted entity
     */
    ShipShipmentStatus save(ShipShipmentStatus shipShipmentStatus);

    /**
     *  Get all the shipShipmentStatuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ShipShipmentStatus> findAll(Pageable pageable);

    /**
     *  Get the "id" shipShipmentStatus.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ShipShipmentStatus findOne(Long id);

    /**
     *  Delete the "id" shipShipmentStatus.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the shipShipmentStatus corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ShipShipmentStatus> search(String query, Pageable pageable);
}
