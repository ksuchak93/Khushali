package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.SalesOrderProductService;
import com.mycompany.myapp.domain.SalesOrderProduct;
import com.mycompany.myapp.repository.SalesOrderProductRepository;
import com.mycompany.myapp.repository.search.SalesOrderProductSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SalesOrderProduct.
 */
@Service
@Transactional
public class SalesOrderProductServiceImpl implements SalesOrderProductService{

    private final Logger log = LoggerFactory.getLogger(SalesOrderProductServiceImpl.class);

    private final SalesOrderProductRepository salesOrderProductRepository;

    private final SalesOrderProductSearchRepository salesOrderProductSearchRepository;

    public SalesOrderProductServiceImpl(SalesOrderProductRepository salesOrderProductRepository, SalesOrderProductSearchRepository salesOrderProductSearchRepository) {
        this.salesOrderProductRepository = salesOrderProductRepository;
        this.salesOrderProductSearchRepository = salesOrderProductSearchRepository;
    }

    /**
     * Save a salesOrderProduct.
     *
     * @param salesOrderProduct the entity to save
     * @return the persisted entity
     */
    @Override
    public SalesOrderProduct save(SalesOrderProduct salesOrderProduct) {
        log.debug("Request to save SalesOrderProduct : {}", salesOrderProduct);
        SalesOrderProduct result = salesOrderProductRepository.save(salesOrderProduct);
        salesOrderProductSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the salesOrderProducts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SalesOrderProduct> findAll(Pageable pageable) {
        log.debug("Request to get all SalesOrderProducts");
        return salesOrderProductRepository.findAll(pageable);
    }

    /**
     *  Get one salesOrderProduct by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SalesOrderProduct findOne(Long id) {
        log.debug("Request to get SalesOrderProduct : {}", id);
        return salesOrderProductRepository.findOne(id);
    }

    /**
     *  Delete the  salesOrderProduct by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SalesOrderProduct : {}", id);
        salesOrderProductRepository.delete(id);
        salesOrderProductSearchRepository.delete(id);
    }

    /**
     * Search for the salesOrderProduct corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SalesOrderProduct> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SalesOrderProducts for query {}", query);
        Page<SalesOrderProduct> result = salesOrderProductSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
