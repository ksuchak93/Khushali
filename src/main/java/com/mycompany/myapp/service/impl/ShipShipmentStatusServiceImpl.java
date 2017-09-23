package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ShipShipmentStatusService;
import com.mycompany.myapp.domain.ShipShipmentStatus;
import com.mycompany.myapp.repository.ShipShipmentStatusRepository;
import com.mycompany.myapp.repository.search.ShipShipmentStatusSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ShipShipmentStatus.
 */
@Service
@Transactional
public class ShipShipmentStatusServiceImpl implements ShipShipmentStatusService{

    private final Logger log = LoggerFactory.getLogger(ShipShipmentStatusServiceImpl.class);

    private final ShipShipmentStatusRepository shipShipmentStatusRepository;

    private final ShipShipmentStatusSearchRepository shipShipmentStatusSearchRepository;

    public ShipShipmentStatusServiceImpl(ShipShipmentStatusRepository shipShipmentStatusRepository, ShipShipmentStatusSearchRepository shipShipmentStatusSearchRepository) {
        this.shipShipmentStatusRepository = shipShipmentStatusRepository;
        this.shipShipmentStatusSearchRepository = shipShipmentStatusSearchRepository;
    }

    /**
     * Save a shipShipmentStatus.
     *
     * @param shipShipmentStatus the entity to save
     * @return the persisted entity
     */
    @Override
    public ShipShipmentStatus save(ShipShipmentStatus shipShipmentStatus) {
        log.debug("Request to save ShipShipmentStatus : {}", shipShipmentStatus);
        ShipShipmentStatus result = shipShipmentStatusRepository.save(shipShipmentStatus);
        shipShipmentStatusSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the shipShipmentStatuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShipShipmentStatus> findAll(Pageable pageable) {
        log.debug("Request to get all ShipShipmentStatuses");
        return shipShipmentStatusRepository.findAll(pageable);
    }

    /**
     *  Get one shipShipmentStatus by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ShipShipmentStatus findOne(Long id) {
        log.debug("Request to get ShipShipmentStatus : {}", id);
        return shipShipmentStatusRepository.findOne(id);
    }

    /**
     *  Delete the  shipShipmentStatus by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShipShipmentStatus : {}", id);
        shipShipmentStatusRepository.delete(id);
        shipShipmentStatusSearchRepository.delete(id);
    }

    /**
     * Search for the shipShipmentStatus corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShipShipmentStatus> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ShipShipmentStatuses for query {}", query);
        Page<ShipShipmentStatus> result = shipShipmentStatusSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
