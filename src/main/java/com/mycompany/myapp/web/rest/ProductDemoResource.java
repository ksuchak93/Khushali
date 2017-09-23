package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.ProductDemo;
import com.mycompany.myapp.service.ProductDemoService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ProductDemo.
 */
@RestController
@RequestMapping("/api")
public class ProductDemoResource {

    private final Logger log = LoggerFactory.getLogger(ProductDemoResource.class);

    private static final String ENTITY_NAME = "productDemo";

    private final ProductDemoService productDemoService;

    public ProductDemoResource(ProductDemoService productDemoService) {
        this.productDemoService = productDemoService;
    }

    /**
     * POST  /product-demos : Create a new productDemo.
     *
     * @param productDemo the productDemo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productDemo, or with status 400 (Bad Request) if the productDemo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-demos")
    @Timed
    public ResponseEntity<ProductDemo> createProductDemo(@RequestBody ProductDemo productDemo) throws URISyntaxException {
        log.debug("REST request to save ProductDemo : {}", productDemo);
        if (productDemo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new productDemo cannot already have an ID")).body(null);
        }
        ProductDemo result = productDemoService.save(productDemo);
        return ResponseEntity.created(new URI("/api/product-demos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-demos : Updates an existing productDemo.
     *
     * @param productDemo the productDemo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productDemo,
     * or with status 400 (Bad Request) if the productDemo is not valid,
     * or with status 500 (Internal Server Error) if the productDemo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-demos")
    @Timed
    public ResponseEntity<ProductDemo> updateProductDemo(@RequestBody ProductDemo productDemo) throws URISyntaxException {
        log.debug("REST request to update ProductDemo : {}", productDemo);
        if (productDemo.getId() == null) {
            return createProductDemo(productDemo);
        }
        ProductDemo result = productDemoService.save(productDemo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productDemo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-demos : get all the productDemos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of productDemos in body
     */
    @GetMapping("/product-demos")
    @Timed
    public ResponseEntity<List<ProductDemo>> getAllProductDemos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ProductDemos");
        Page<ProductDemo> page = productDemoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/product-demos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /product-demos/:id : get the "id" productDemo.
     *
     * @param id the id of the productDemo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productDemo, or with status 404 (Not Found)
     */
    @GetMapping("/product-demos/{id}")
    @Timed
    public ResponseEntity<ProductDemo> getProductDemo(@PathVariable Long id) {
        log.debug("REST request to get ProductDemo : {}", id);
        ProductDemo productDemo = productDemoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(productDemo));
    }

    /**
     * DELETE  /product-demos/:id : delete the "id" productDemo.
     *
     * @param id the id of the productDemo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-demos/{id}")
    @Timed
    public ResponseEntity<Void> deleteProductDemo(@PathVariable Long id) {
        log.debug("REST request to delete ProductDemo : {}", id);
        productDemoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/product-demos?query=:query : search for the productDemo corresponding
     * to the query.
     *
     * @param query the query of the productDemo search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/product-demos")
    @Timed
    public ResponseEntity<List<ProductDemo>> searchProductDemos(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of ProductDemos for query {}", query);
        Page<ProductDemo> page = productDemoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/product-demos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
