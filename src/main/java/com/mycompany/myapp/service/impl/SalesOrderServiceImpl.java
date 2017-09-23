package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.SalesOrderService;
import com.mycompany.myapp.domain.SalesOrder;
import com.mycompany.myapp.repository.SalesOrderRepository;
import com.mycompany.myapp.repository.search.SalesOrderSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SalesOrder.
 */
@Service
@Transactional
public class SalesOrderServiceImpl implements SalesOrderService{

    private final Logger log = LoggerFactory.getLogger(SalesOrderServiceImpl.class);

    private final SalesOrderRepository salesOrderRepository;

    private final SalesOrderSearchRepository salesOrderSearchRepository;

    public SalesOrderServiceImpl(SalesOrderRepository salesOrderRepository, SalesOrderSearchRepository salesOrderSearchRepository) {
        this.salesOrderRepository = salesOrderRepository;
        this.salesOrderSearchRepository = salesOrderSearchRepository;
    }

    /**
     * Save a salesOrder.
     *
     * @param salesOrder the entity to save
     * @return the persisted entity
     */
    @Override
    public SalesOrder save(SalesOrder salesOrder) {
        log.debug("Request to save SalesOrder : {}", salesOrder);
        SalesOrder result = salesOrderRepository.save(salesOrder);
        salesOrderSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the salesOrders.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SalesOrder> findAll(Pageable pageable) {
        log.debug("Request to get all SalesOrders");
        return salesOrderRepository.findAll(pageable);
    }

    /**
     *  Get one salesOrder by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SalesOrder findOne(Long id) {
        log.debug("Request to get SalesOrder : {}", id);
        return salesOrderRepository.findOne(id);
    }

    /**
     *  Delete the  salesOrder by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SalesOrder : {}", id);
        salesOrderRepository.delete(id);
        salesOrderSearchRepository.delete(id);
    }

    /**
     * Search for the salesOrder corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SalesOrder> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SalesOrders for query {}", query);
        Page<SalesOrder> result = salesOrderSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
