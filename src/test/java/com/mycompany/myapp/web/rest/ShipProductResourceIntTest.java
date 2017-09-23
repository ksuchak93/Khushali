package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.KhushFinalApp;

import com.mycompany.myapp.domain.ShipProduct;
import com.mycompany.myapp.repository.ShipProductRepository;
import com.mycompany.myapp.service.ShipProductService;
import com.mycompany.myapp.repository.search.ShipProductSearchRepository;
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
 * Test class for the ShipProductResource REST controller.
 *
 * @see ShipProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KhushFinalApp.class)
public class ShipProductResourceIntTest {

    private static final String DEFAULT_QUANTITY = "AAAAAAAAAA";
    private static final String UPDATED_QUANTITY = "BBBBBBBBBB";

    private static final Double DEFAULT_NET_WEIGHT = 1D;
    private static final Double UPDATED_NET_WEIGHT = 2D;

    private static final Double DEFAULT_GROSS_WEIGHT = 1D;
    private static final Double UPDATED_GROSS_WEIGHT = 2D;

    private static final String DEFAULT_PACKEGE = "AAAAAAAAAA";
    private static final String UPDATED_PACKEGE = "BBBBBBBBBB";

    @Autowired
    private ShipProductRepository shipProductRepository;

    @Autowired
    private ShipProductService shipProductService;

    @Autowired
    private ShipProductSearchRepository shipProductSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShipProductMockMvc;

    private ShipProduct shipProduct;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShipProductResource shipProductResource = new ShipProductResource(shipProductService);
        this.restShipProductMockMvc = MockMvcBuilders.standaloneSetup(shipProductResource)
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
    public static ShipProduct createEntity(EntityManager em) {
        ShipProduct shipProduct = new ShipProduct()
            .quantity(DEFAULT_QUANTITY)
            .netWeight(DEFAULT_NET_WEIGHT)
            .grossWeight(DEFAULT_GROSS_WEIGHT)
            .packege(DEFAULT_PACKEGE);
        return shipProduct;
    }

    @Before
    public void initTest() {
        shipProductSearchRepository.deleteAll();
        shipProduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createShipProduct() throws Exception {
        int databaseSizeBeforeCreate = shipProductRepository.findAll().size();

        // Create the ShipProduct
        restShipProductMockMvc.perform(post("/api/ship-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipProduct)))
            .andExpect(status().isCreated());

        // Validate the ShipProduct in the database
        List<ShipProduct> shipProductList = shipProductRepository.findAll();
        assertThat(shipProductList).hasSize(databaseSizeBeforeCreate + 1);
        ShipProduct testShipProduct = shipProductList.get(shipProductList.size() - 1);
        assertThat(testShipProduct.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testShipProduct.getNetWeight()).isEqualTo(DEFAULT_NET_WEIGHT);
        assertThat(testShipProduct.getGrossWeight()).isEqualTo(DEFAULT_GROSS_WEIGHT);
        assertThat(testShipProduct.getPackege()).isEqualTo(DEFAULT_PACKEGE);

        // Validate the ShipProduct in Elasticsearch
        ShipProduct shipProductEs = shipProductSearchRepository.findOne(testShipProduct.getId());
        assertThat(shipProductEs).isEqualToComparingFieldByField(testShipProduct);
    }

    @Test
    @Transactional
    public void createShipProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shipProductRepository.findAll().size();

        // Create the ShipProduct with an existing ID
        shipProduct.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipProductMockMvc.perform(post("/api/ship-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipProduct)))
            .andExpect(status().isBadRequest());

        // Validate the ShipProduct in the database
        List<ShipProduct> shipProductList = shipProductRepository.findAll();
        assertThat(shipProductList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllShipProducts() throws Exception {
        // Initialize the database
        shipProductRepository.saveAndFlush(shipProduct);

        // Get all the shipProductList
        restShipProductMockMvc.perform(get("/api/ship-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.toString())))
            .andExpect(jsonPath("$.[*].netWeight").value(hasItem(DEFAULT_NET_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].grossWeight").value(hasItem(DEFAULT_GROSS_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].packege").value(hasItem(DEFAULT_PACKEGE.toString())));
    }

    @Test
    @Transactional
    public void getShipProduct() throws Exception {
        // Initialize the database
        shipProductRepository.saveAndFlush(shipProduct);

        // Get the shipProduct
        restShipProductMockMvc.perform(get("/api/ship-products/{id}", shipProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shipProduct.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.toString()))
            .andExpect(jsonPath("$.netWeight").value(DEFAULT_NET_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.grossWeight").value(DEFAULT_GROSS_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.packege").value(DEFAULT_PACKEGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShipProduct() throws Exception {
        // Get the shipProduct
        restShipProductMockMvc.perform(get("/api/ship-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipProduct() throws Exception {
        // Initialize the database
        shipProductService.save(shipProduct);

        int databaseSizeBeforeUpdate = shipProductRepository.findAll().size();

        // Update the shipProduct
        ShipProduct updatedShipProduct = shipProductRepository.findOne(shipProduct.getId());
        updatedShipProduct
            .quantity(UPDATED_QUANTITY)
            .netWeight(UPDATED_NET_WEIGHT)
            .grossWeight(UPDATED_GROSS_WEIGHT)
            .packege(UPDATED_PACKEGE);

        restShipProductMockMvc.perform(put("/api/ship-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedShipProduct)))
            .andExpect(status().isOk());

        // Validate the ShipProduct in the database
        List<ShipProduct> shipProductList = shipProductRepository.findAll();
        assertThat(shipProductList).hasSize(databaseSizeBeforeUpdate);
        ShipProduct testShipProduct = shipProductList.get(shipProductList.size() - 1);
        assertThat(testShipProduct.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testShipProduct.getNetWeight()).isEqualTo(UPDATED_NET_WEIGHT);
        assertThat(testShipProduct.getGrossWeight()).isEqualTo(UPDATED_GROSS_WEIGHT);
        assertThat(testShipProduct.getPackege()).isEqualTo(UPDATED_PACKEGE);

        // Validate the ShipProduct in Elasticsearch
        ShipProduct shipProductEs = shipProductSearchRepository.findOne(testShipProduct.getId());
        assertThat(shipProductEs).isEqualToComparingFieldByField(testShipProduct);
    }

    @Test
    @Transactional
    public void updateNonExistingShipProduct() throws Exception {
        int databaseSizeBeforeUpdate = shipProductRepository.findAll().size();

        // Create the ShipProduct

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShipProductMockMvc.perform(put("/api/ship-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipProduct)))
            .andExpect(status().isCreated());

        // Validate the ShipProduct in the database
        List<ShipProduct> shipProductList = shipProductRepository.findAll();
        assertThat(shipProductList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteShipProduct() throws Exception {
        // Initialize the database
        shipProductService.save(shipProduct);

        int databaseSizeBeforeDelete = shipProductRepository.findAll().size();

        // Get the shipProduct
        restShipProductMockMvc.perform(delete("/api/ship-products/{id}", shipProduct.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean shipProductExistsInEs = shipProductSearchRepository.exists(shipProduct.getId());
        assertThat(shipProductExistsInEs).isFalse();

        // Validate the database is empty
        List<ShipProduct> shipProductList = shipProductRepository.findAll();
        assertThat(shipProductList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchShipProduct() throws Exception {
        // Initialize the database
        shipProductService.save(shipProduct);

        // Search the shipProduct
        restShipProductMockMvc.perform(get("/api/_search/ship-products?query=id:" + shipProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.toString())))
            .andExpect(jsonPath("$.[*].netWeight").value(hasItem(DEFAULT_NET_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].grossWeight").value(hasItem(DEFAULT_GROSS_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].packege").value(hasItem(DEFAULT_PACKEGE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipProduct.class);
        ShipProduct shipProduct1 = new ShipProduct();
        shipProduct1.setId(1L);
        ShipProduct shipProduct2 = new ShipProduct();
        shipProduct2.setId(shipProduct1.getId());
        assertThat(shipProduct1).isEqualTo(shipProduct2);
        shipProduct2.setId(2L);
        assertThat(shipProduct1).isNotEqualTo(shipProduct2);
        shipProduct1.setId(null);
        assertThat(shipProduct1).isNotEqualTo(shipProduct2);
    }
}
