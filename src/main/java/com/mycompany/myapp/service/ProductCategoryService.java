package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ProductCategory.
 */
public interface ProductCategoryService {

    /**
     * Save a productCategory.
     *
     * @param productCategory the entity to save
     * @return the persisted entity
     */
    ProductCategory save(ProductCategory productCategory);

    /**
     *  Get all the productCategories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProductCategory> findAll(Pageable pageable);

    /**
     *  Get the "id" productCategory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProductCategory findOne(Long id);

    /**
     *  Delete the "id" productCategory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the productCategory corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProductCategory> search(String query, Pageable pageable);
}
