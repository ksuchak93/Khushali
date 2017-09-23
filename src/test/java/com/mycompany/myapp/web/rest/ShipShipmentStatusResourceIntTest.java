package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.KhushFinalApp;

import com.mycompany.myapp.domain.ShipShipmentStatus;
import com.mycompany.myapp.repository.ShipShipmentStatusRepository;
import com.mycompany.myapp.service.ShipShipmentStatusService;
import com.mycompany.myapp.repository.search.ShipShipmentStatusSearchRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ShipShipmentStatusResource REST controller.
 *
 * @see ShipShipmentStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KhushFinalApp.class)
public class ShipShipmentStatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private ShipShipmentStatusRepository shipShipmentStatusRepository;

    @Autowired
    private ShipShipmentStatusService shipShipmentStatusService;

    @Autowired
    private ShipShipmentStatusSearchRepository shipShipmentStatusSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShipShipmentStatusMockMvc;

    private ShipShipmentStatus shipShipmentStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShipShipmentStatusResource shipShipmentStatusResource = new ShipShipmentStatusResource(shipShipmentStatusService);
        this.restShipShipmentStatusMockMvc = MockMvcBuilders.standaloneSetup(shipShipmentStatusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShipShipmentStatus createEntity(EntityManager em) {
        ShipShipmentStatus shipShipmentStatus = new ShipShipmentStatus()
            .name(DEFAULT_NAME)
            .status(DEFAULT_STATUS);
        return shipShipmentStatus;
    }

    @Before
    public void initTest() {
        shipShipmentStatusSearchRepository.deleteAll();
        shipShipmentStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createShipShipmentStatus() throws Exception {
        int databaseSizeBeforeCreate = shipShipmentStatusRepository.findAll().size();

        // Create the ShipShipmentStatus
        restShipShipmentStatusMockMvc.perform(post("/api/ship-shipment-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipShipmentStatus)))
            .andExpect(status().isCreated());

        // Validate the ShipShipmentStatus in the database
        List<ShipShipmentStatus> shipShipmentStatusList = shipShipmentStatusRepository.findAll();
        assertThat(shipShipmentStatusList).hasSize(databaseSizeBeforeCreate + 1);
        ShipShipmentStatus testShipShipmentStatus = shipShipmentStatusList.get(shipShipmentStatusList.size() - 1);
        assertThat(testShipShipmentStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShipShipmentStatus.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the ShipShipmentStatus in Elasticsearch
        ShipShipmentStatus shipShipmentStatusEs = shipShipmentStatusSearchRepository.findOne(testShipShipmentStatus.getId());
        assertThat(shipShipmentStatusEs).isEqualToComparingFieldByField(testShipShipmentStatus);
    }

    @Test
    @Transactional
    public void createShipShipmentStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shipShipmentStatusRepository.findAll().size();

        // Create the ShipShipmentStatus with an existing ID
        shipShipmentStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipShipmentStatusMockMvc.perform(post("/api/ship-shipment-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipShipmentStatus)))
            .andExpect(status().isBadRequest());

        // Validate the ShipShipmentStatus in the database
        List<ShipShipmentStatus> shipShipmentStatusList = shipShipmentStatusRepository.findAll();
        assertThat(shipShipmentStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllShipShipmentStatuses() throws Exception {
        // Initialize the database
        shipShipmentStatusRepository.saveAndFlush(shipShipmentStatus);

        // Get all the shipShipmentStatusList
        restShipShipmentStatusMockMvc.perform(get("/api/ship-shipment-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipShipmentStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getShipShipmentStatus() throws Exception {
        // Initialize the database
        shipShipmentStatusRepository.saveAndFlush(shipShipmentStatus);

        // Get the shipShipmentStatus
        restShipShipmentStatusMockMvc.perform(get("/api/ship-shipment-statuses/{id}", shipShipmentStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shipShipmentStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShipShipmentStatus() throws Exception {
        // Get the shipShipmentStatus
        restShipShipmentStatusMockMvc.perform(get("/api/ship-shipment-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipShipmentStatus() throws Exception {
        // Initialize the database
        shipShipmentStatusService.save(shipShipmentStatus);

        int databaseSizeBeforeUpdate = shipShipmentStatusRepository.findAll().size();

        // Update the shipShipmentStatus
        ShipShipmentStatus updatedShipShipmentStatus = shipShipmentStatusRepository.findOne(shipShipmentStatus.getId());
        updatedShipShipmentStatus
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS);

        restShipShipmentStatusMockMvc.perform(put("/api/ship-shipment-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedShipShipmentStatus)))
            .andExpect(status().isOk());

        // Validate the ShipShipmentStatus in the database
        List<ShipShipmentStatus> shipShipmentStatusList = shipShipmentStatusRepository.findAll();
        assertThat(shipShipmentStatusList).hasSize(databaseSizeBeforeUpdate);
        ShipShipmentStatus testShipShipmentStatus = shipShipmentStatusList.get(shipShipmentStatusList.size() - 1);
        assertThat(testShipShipmentStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShipShipmentStatus.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the ShipShipmentStatus in Elasticsearch
        ShipShipmentStatus shipShipmentStatusEs = shipShipmentStatusSearchRepository.findOne(testShipShipmentStatus.getId());
        assertThat(shipShipmentStatusEs).isEqualToComparingFieldByField(testShipShipmentStatus);
    }

    @Test
    @Transactional
    public void updateNonExistingShipShipmentStatus() throws Exception {
        int databaseSizeBeforeUpdate = shipShipmentStatusRepository.findAll().size();

        // Create the ShipShipmentStatus

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShipShipmentStatusMockMvc.perform(put("/api/ship-shipment-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipShipmentStatus)))
            .andExpect(status().isCreated());

        // Validate the ShipShipmentStatus in the database
        List<ShipShipmentStatus> shipShipmentStatusList = shipShipmentStatusRepository.findAll();
        assertThat(shipShipmentStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteShipShipmentStatus() throws Exception {
        // Initialize the database
        shipShipmentStatusService.save(shipShipmentStatus);

        int databaseSizeBeforeDelete = shipShipmentStatusRepository.findAll().size();

        // Get the shipShipmentStatus
        restShipShipmentStatusMockMvc.perform(delete("/api/ship-shipment-statuses/{id}", shipShipmentStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean shipShipmentStatusExistsInEs = shipShipmentStatusSearchRepository.exists(shipShipmentStatus.getId());
        assertThat(shipShipmentStatusExistsInEs).isFalse();

        // Validate the database is empty
        List<ShipShipmentStatus> shipShipmentStatusList = shipShipmentStatusRepository.findAll();
        assertThat(shipShipmentStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchShipShipmentStatus() throws Exception {
        // Initialize the database
        shipShipmentStatusService.save(shipShipmentStatus);

        // Search the shipShipmentStatus
        restShipShipmentStatusMockMvc.perform(get("/api/_search/ship-shipment-statuses?query=id:" + shipShipmentStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipShipmentStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipShipmentStatus.class);
        ShipShipmentStatus shipShipmentStatus1 = new ShipShipmentStatus();
        shipShipmentStatus1.setId(1L);
        ShipShipmentStatus shipShipmentStatus2 = new ShipShipmentStatus();
        shipShipmentStatus2.setId(shipShipmentStatus1.getId());
        assertThat(shipShipmentStatus1).isEqualTo(shipShipmentStatus2);
        shipShipmentStatus2.setId(2L);
        assertThat(shipShipmentStatus1).isNotEqualTo(shipShipmentStatus2);
        shipShipmentStatus1.setId(null);
        assertThat(shipShipmentStatus1).isNotEqualTo(shipShipmentStatus2);
    }
}
