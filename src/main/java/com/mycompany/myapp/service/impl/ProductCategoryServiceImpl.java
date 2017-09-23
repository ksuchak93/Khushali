package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ProductCategoryService;
import com.mycompany.myapp.domain.ProductCategory;
import com.mycompany.myapp.repository.ProductCategoryRepository;
import com.mycompany.myapp.repository.search.ProductCategorySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ProductCategory.
 */
@Service
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService{

    private final Logger log = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);

    private final ProductCategoryRepository productCategoryRepository;

    private final ProductCategorySearchRepository productCategorySearchRepository;

    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository, ProductCategorySearchRepository productCategorySearchRepository) {
        this.productCategoryRepository = productCategoryRepository;
        this.productCategorySearchRepository = productCategorySearchRepository;
    }

    /**
     * Save a productCategory.
     *
     * @param productCategory the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductCategory save(ProductCategory productCategory) {
        log.debug("Request to save ProductCategory : {}", productCategory);
        ProductCategory result = productCategoryRepository.save(productCategory);
        productCategorySearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the productCategories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductCategory> findAll(Pageable pageable) {
        log.debug("Request to get all ProductCategories");
        return productCategoryRepository.findAll(pageable);
    }

    /**
     *  Get one productCategory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProductCategory findOne(Long id) {
        log.debug("Request to get ProductCategory : {}", id);
        return productCategoryRepository.findOne(id);
    }

    /**
     *  Delete the  productCategory by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductCategory : {}", id);
        productCategoryRepository.delete(id);
        productCategorySearchRepository.delete(id);
    }

    /**
     * Search for the productCategory corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductCategory> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProductCategories for query {}", query);
        Page<ProductCategory> result = productCategorySearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
