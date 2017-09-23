package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.CompanyCategory;
import com.mycompany.myapp.service.CompanyCategoryService;
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
 * REST controller for managing CompanyCategory.
 */
@RestController
@RequestMapping("/api")
public class CompanyCategoryResource {

    private final Logger log = LoggerFactory.getLogger(CompanyCategoryResource.class);

    private static final String ENTITY_NAME = "companyCategory";

    private final CompanyCategoryService companyCategoryService;

    public CompanyCategoryResource(CompanyCategoryService companyCategoryService) {
        this.companyCategoryService = companyCategoryService;
    }

    /**
     * POST  /company-categories : Create a new companyCategory.
     *
     * @param companyCategory the companyCategory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyCategory, or with status 400 (Bad Request) if the companyCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-categories")
    @Timed
    public ResponseEntity<CompanyCategory> createCompanyCategory(@RequestBody CompanyCategory companyCategory) throws URISyntaxException {
        log.debug("REST request to save CompanyCategory : {}", companyCategory);
        if (companyCategory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new companyCategory cannot already have an ID")).body(null);
        }
        CompanyCategory result = companyCategoryService.save(companyCategory);
        return ResponseEntity.created(new URI("/api/company-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-categories : Updates an existing companyCategory.
     *
     * @param companyCategory the companyCategory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyCategory,
     * or with status 400 (Bad Request) if the companyCategory is not valid,
     * or with status 500 (Internal Server Error) if the companyCategory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-categories")
    @Timed
    public ResponseEntity<CompanyCategory> updateCompanyCategory(@RequestBody CompanyCategory companyCategory) throws URISyntaxException {
        log.debug("REST request to update CompanyCategory : {}", companyCategory);
        if (companyCategory.getId() == null) {
            return createCompanyCategory(companyCategory);
        }
        CompanyCategory result = companyCategoryService.save(companyCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-categories : get all the companyCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of companyCategories in body
     */
    @GetMapping("/company-categories")
    @Timed
    public ResponseEntity<List<CompanyCategory>> getAllCompanyCategories(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CompanyCategories");
        Page<CompanyCategory> page = companyCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/company-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /company-categories/:id : get the "id" companyCategory.
     *
     * @param id the id of the companyCategory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyCategory, or with status 404 (Not Found)
     */
    @GetMapping("/company-categories/{id}")
    @Timed
    public ResponseEntity<CompanyCategory> getCompanyCategory(@PathVariable Long id) {
        log.debug("REST request to get CompanyCategory : {}", id);
        CompanyCategory companyCategory = companyCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(companyCategory));
    }

    /**
     * DELETE  /company-categories/:id : delete the "id" companyCategory.
     *
     * @param id the id of the companyCategory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyCategory(@PathVariable Long id) {
        log.debug("REST request to delete CompanyCategory : {}", id);
        companyCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/company-categories?query=:query : search for the companyCategory corresponding
     * to the query.
     *
     * @param query the query of the companyCategory search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/company-categories")
    @Timed
    public ResponseEntity<List<CompanyCategory>> searchCompanyCategories(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of CompanyCategories for query {}", query);
        Page<CompanyCategory> page = companyCategoryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/company-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
