package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.ShipShipmentStatus;
import com.mycompany.myapp.service.ShipShipmentStatusService;
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
 * REST controller for managing ShipShipmentStatus.
 */
@RestController
@RequestMapping("/api")
public class ShipShipmentStatusResource {

    private final Logger log = LoggerFactory.getLogger(ShipShipmentStatusResource.class);

    private static final String ENTITY_NAME = "shipShipmentStatus";

    private final ShipShipmentStatusService shipShipmentStatusService;

    public ShipShipmentStatusResource(ShipShipmentStatusService shipShipmentStatusService) {
        this.shipShipmentStatusService = shipShipmentStatusService;
    }

    /**
     * POST  /ship-shipment-statuses : Create a new shipShipmentStatus.
     *
     * @param shipShipmentStatus the shipShipmentStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shipShipmentStatus, or with status 400 (Bad Request) if the shipShipmentStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ship-shipment-statuses")
    @Timed
    public ResponseEntity<ShipShipmentStatus> createShipShipmentStatus(@RequestBody ShipShipmentStatus shipShipmentStatus) throws URISyntaxException {
        log.debug("REST request to save ShipShipmentStatus : {}", shipShipmentStatus);
        if (shipShipmentStatus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new shipShipmentStatus cannot already have an ID")).body(null);
        }
        ShipShipmentStatus result = shipShipmentStatusService.save(shipShipmentStatus);
        return ResponseEntity.created(new URI("/api/ship-shipment-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ship-shipment-statuses : Updates an existing shipShipmentStatus.
     *
     * @param shipShipmentStatus the shipShipmentStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shipShipmentStatus,
     * or with status 400 (Bad Request) if the shipShipmentStatus is not valid,
     * or with status 500 (Internal Server Error) if the shipShipmentStatus couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ship-shipment-statuses")
    @Timed
    public ResponseEntity<ShipShipmentStatus> updateShipShipmentStatus(@RequestBody ShipShipmentStatus shipShipmentStatus) throws URISyntaxException {
        log.debug("REST request to update ShipShipmentStatus : {}", shipShipmentStatus);
        if (shipShipmentStatus.getId() == null) {
            return createShipShipmentStatus(shipShipmentStatus);
        }
        ShipShipmentStatus result = shipShipmentStatusService.save(shipShipmentStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shipShipmentStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ship-shipment-statuses : get all the shipShipmentStatuses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of shipShipmentStatuses in body
     */
    @GetMapping("/ship-shipment-statuses")
    @Timed
    public ResponseEntity<List<ShipShipmentStatus>> getAllShipShipmentStatuses(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ShipShipmentStatuses");
        Page<ShipShipmentStatus> page = shipShipmentStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ship-shipment-statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ship-shipment-statuses/:id : get the "id" shipShipmentStatus.
     *
     * @param id the id of the shipShipmentStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shipShipmentStatus, or with status 404 (Not Found)
     */
    @GetMapping("/ship-shipment-statuses/{id}")
    @Timed
    public ResponseEntity<ShipShipmentStatus> getShipShipmentStatus(@PathVariable Long id) {
        log.debug("REST request to get ShipShipmentStatus : {}", id);
        ShipShipmentStatus shipShipmentStatus = shipShipmentStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(shipShipmentStatus));
    }

    /**
     * DELETE  /ship-shipment-statuses/:id : delete the "id" shipShipmentStatus.
     *
     * @param id the id of the shipShipmentStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ship-shipment-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteShipShipmentStatus(@PathVariable Long id) {
        log.debug("REST request to delete ShipShipmentStatus : {}", id);
        shipShipmentStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ship-shipment-statuses?query=:query : search for the shipShipmentStatus corresponding
     * to the query.
     *
     * @param query the query of the shipShipmentStatus search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ship-shipment-statuses")
    @Timed
    public ResponseEntity<List<ShipShipmentStatus>> searchShipShipmentStatuses(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of ShipShipmentStatuses for query {}", query);
        Page<ShipShipmentStatus> page = shipShipmentStatusService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ship-shipment-statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
