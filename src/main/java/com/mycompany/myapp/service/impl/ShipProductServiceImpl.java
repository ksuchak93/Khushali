package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ShipProductService;
import com.mycompany.myapp.domain.ShipProduct;
import com.mycompany.myapp.repository.ShipProductRepository;
import com.mycompany.myapp.repository.search.ShipProductSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ShipProduct.
 */
@Service
@Transactional
public class ShipProductServiceImpl implements ShipProductService{

    private final Logger log = LoggerFactory.getLogger(ShipProductServiceImpl.class);

    private final ShipProductRepository shipProductRepository;

    private final ShipProductSearchRepository shipProductSearchRepository;

    public ShipProductServiceImpl(ShipProductRepository shipProductRepository, ShipProductSearchRepository shipProductSearchRepository) {
        this.shipProductRepository = shipProductRepository;
        this.shipProductSearchRepository = shipProductSearchRepository;
    }

    /**
     * Save a shipProduct.
     *
     * @param shipProduct the entity to save
     * @return the persisted entity
     */
    @Override
    public ShipProduct save(ShipProduct shipProduct) {
        log.debug("Request to save ShipProduct : {}", shipProduct);
        ShipProduct result = shipProductRepository.save(shipProduct);
        shipProductSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the shipProducts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShipProduct> findAll(Pageable pageable) {
        log.debug("Request to get all ShipProducts");
        return shipProductRepository.findAll(pageable);
    }

    /**
     *  Get one shipProduct by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ShipProduct findOne(Long id) {
        log.debug("Request to get ShipProduct : {}", id);
        return shipProductRepository.findOne(id);
    }

    /**
     *  Delete the  shipProduct by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShipProduct : {}", id);
        shipProductRepository.delete(id);
        shipProductSearchRepository.delete(id);
    }

    /**
     * Search for the shipProduct corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShipProduct> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ShipProducts for query {}", query);
        Page<ShipProduct> result = shipProductSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
