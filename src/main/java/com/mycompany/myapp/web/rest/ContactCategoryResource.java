package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.ContactCategory;
import com.mycompany.myapp.service.ContactCategoryService;
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
 * REST controller for managing ContactCategory.
 */
@RestController
@RequestMapping("/api")
public class ContactCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ContactCategoryResource.class);

    private static final String ENTITY_NAME = "contactCategory";

    private final ContactCategoryService contactCategoryService;

    public ContactCategoryResource(ContactCategoryService contactCategoryService) {
        this.contactCategoryService = contactCategoryService;
    }

    /**
     * POST  /contact-categories : Create a new contactCategory.
     *
     * @param contactCategory the contactCategory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contactCategory, or with status 400 (Bad Request) if the contactCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contact-categories")
    @Timed
    public ResponseEntity<ContactCategory> createContactCategory(@RequestBody ContactCategory contactCategory) throws URISyntaxException {
        log.debug("REST request to save ContactCategory : {}", contactCategory);
        if (contactCategory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new contactCategory cannot already have an ID")).body(null);
        }
        ContactCategory result = contactCategoryService.save(contactCategory);
        return ResponseEntity.created(new URI("/api/contact-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contact-categories : Updates an existing contactCategory.
     *
     * @param contactCategory the contactCategory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contactCategory,
     * or with status 400 (Bad Request) if the contactCategory is not valid,
     * or with status 500 (Internal Server Error) if the contactCategory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contact-categories")
    @Timed
    public ResponseEntity<ContactCategory> updateContactCategory(@RequestBody ContactCategory contactCategory) throws URISyntaxException {
        log.debug("REST request to update ContactCategory : {}", contactCategory);
        if (contactCategory.getId() == null) {
            return createContactCategory(contactCategory);
        }
        ContactCategory result = contactCategoryService.save(contactCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contactCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contact-categories : get all the contactCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of contactCategories in body
     */
    @GetMapping("/contact-categories")
    @Timed
    public ResponseEntity<List<ContactCategory>> getAllContactCategories(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ContactCategories");
        Page<ContactCategory> page = contactCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contact-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /contact-categories/:id : get the "id" contactCategory.
     *
     * @param id the id of the contactCategory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contactCategory, or with status 404 (Not Found)
     */
    @GetMapping("/contact-categories/{id}")
    @Timed
    public ResponseEntity<ContactCategory> getContactCategory(@PathVariable Long id) {
        log.debug("REST request to get ContactCategory : {}", id);
        ContactCategory contactCategory = contactCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contactCategory));
    }

    /**
     * DELETE  /contact-categories/:id : delete the "id" contactCategory.
     *
     * @param id the id of the contactCategory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contact-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteContactCategory(@PathVariable Long id) {
        log.debug("REST request to delete ContactCategory : {}", id);
        contactCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/contact-categories?query=:query : search for the contactCategory corresponding
     * to the query.
     *
     * @param query the query of the contactCategory search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/contact-categories")
    @Timed
    public ResponseEntity<List<ContactCategory>> searchContactCategories(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of ContactCategories for query {}", query);
        Page<ContactCategory> page = contactCategoryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/contact-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
