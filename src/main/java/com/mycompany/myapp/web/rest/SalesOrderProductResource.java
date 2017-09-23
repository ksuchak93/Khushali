package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.SalesOrderProduct;
import com.mycompany.myapp.service.SalesOrderProductService;
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
 * REST controller for managing SalesOrderProduct.
 */
@RestController
@RequestMapping("/api")
public class SalesOrderProductResource {

    private final Logger log = LoggerFactory.getLogger(SalesOrderProductResource.class);

    private static final String ENTITY_NAME = "salesOrderProduct";

    private final SalesOrderProductService salesOrderProductService;

    public SalesOrderProductResource(SalesOrderProductService salesOrderProductService) {
        this.salesOrderProductService = salesOrderProductService;
    }

    /**
     * POST  /sales-order-products : Create a new salesOrderProduct.
     *
     * @param salesOrderProduct the salesOrderProduct to create
     * @return the ResponseEntity with status 201 (Created) and with body the new salesOrderProduct, or with status 400 (Bad Request) if the salesOrderProduct has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sales-order-products")
    @Timed
    public ResponseEntity<SalesOrderProduct> createSalesOrderProduct(@RequestBody SalesOrderProduct salesOrderProduct) throws URISyntaxException {
        log.debug("REST request to save SalesOrderProduct : {}", salesOrderProduct);
        if (salesOrderProduct.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new salesOrderProduct cannot already have an ID")).body(null);
        }
        SalesOrderProduct result = salesOrderProductService.save(salesOrderProduct);
        return ResponseEntity.created(new URI("/api/sales-order-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sales-order-products : Updates an existing salesOrderProduct.
     *
     * @param salesOrderProduct the salesOrderProduct to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated salesOrderProduct,
     * or with status 400 (Bad Request) if the salesOrderProduct is not valid,
     * or with status 500 (Internal Server Error) if the salesOrderProduct couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sales-order-products")
    @Timed
    public ResponseEntity<SalesOrderProduct> updateSalesOrderProduct(@RequestBody SalesOrderProduct salesOrderProduct) throws URISyntaxException {
        log.debug("REST request to update SalesOrderProduct : {}", salesOrderProduct);
        if (salesOrderProduct.getId() == null) {
            return createSalesOrderProduct(salesOrderProduct);
        }
        SalesOrderProduct result = salesOrderProductService.save(salesOrderProduct);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, salesOrderProduct.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sales-order-products : get all the salesOrderProducts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of salesOrderProducts in body
     */
    @GetMapping("/sales-order-products")
    @Timed
    public ResponseEntity<List<SalesOrderProduct>> getAllSalesOrderProducts(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of SalesOrderProducts");
        Page<SalesOrderProduct> page = salesOrderProductService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sales-order-products");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sales-order-products/:id : get the "id" salesOrderProduct.
     *
     * @param id the id of the salesOrderProduct to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the salesOrderProduct, or with status 404 (Not Found)
     */
    @GetMapping("/sales-order-products/{id}")
    @Timed
    public ResponseEntity<SalesOrderProduct> getSalesOrderProduct(@PathVariable Long id) {
        log.debug("REST request to get SalesOrderProduct : {}", id);
        SalesOrderProduct salesOrderProduct = salesOrderProductService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(salesOrderProduct));
    }

    /**
     * DELETE  /sales-order-products/:id : delete the "id" salesOrderProduct.
     *
     * @param id the id of the salesOrderProduct to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sales-order-products/{id}")
    @Timed
    public ResponseEntity<Void> deleteSalesOrderProduct(@PathVariable Long id) {
        log.debug("REST request to delete SalesOrderProduct : {}", id);
        salesOrderProductService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/sales-order-products?query=:query : search for the salesOrderProduct corresponding
     * to the query.
     *
     * @param query the query of the salesOrderProduct search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/sales-order-products")
    @Timed
    public ResponseEntity<List<SalesOrderProduct>> searchSalesOrderProducts(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of SalesOrderProducts for query {}", query);
        Page<SalesOrderProduct> page = salesOrderProductService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sales-order-products");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
