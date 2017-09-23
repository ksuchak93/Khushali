package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ProductDemoService;
import com.mycompany.myapp.domain.ProductDemo;
import com.mycompany.myapp.repository.ProductDemoRepository;
import com.mycompany.myapp.repository.search.ProductDemoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ProductDemo.
 */
@Service
@Transactional
public class ProductDemoServiceImpl implements ProductDemoService{

    private final Logger log = LoggerFactory.getLogger(ProductDemoServiceImpl.class);

    private final ProductDemoRepository productDemoRepository;

    private final ProductDemoSearchRepository productDemoSearchRepository;

    public ProductDemoServiceImpl(ProductDemoRepository productDemoRepository, ProductDemoSearchRepository productDemoSearchRepository) {
        this.productDemoRepository = productDemoRepository;
        this.productDemoSearchRepository = productDemoSearchRepository;
    }

    /**
     * Save a productDemo.
     *
     * @param productDemo the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductDemo save(ProductDemo productDemo) {
        log.debug("Request to save ProductDemo : {}", productDemo);
        ProductDemo result = productDemoRepository.save(productDemo);
        productDemoSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the productDemos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductDemo> findAll(Pageable pageable) {
        log.debug("Request to get all ProductDemos");
        return productDemoRepository.findAll(pageable);
    }

    /**
     *  Get one productDemo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProductDemo findOne(Long id) {
        log.debug("Request to get ProductDemo : {}", id);
        return productDemoRepository.findOneWithEagerRelationships(id);
    }

    /**
     *  Delete the  productDemo by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductDemo : {}", id);
        productDemoRepository.delete(id);
        productDemoSearchRepository.delete(id);
    }

    /**
     * Search for the productDemo corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductDemo> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProductDemos for query {}", query);
        Page<ProductDemo> result = productDemoSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
