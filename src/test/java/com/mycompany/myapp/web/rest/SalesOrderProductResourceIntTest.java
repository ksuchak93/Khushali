package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.KhushFinalApp;

import com.mycompany.myapp.domain.SalesOrderProduct;
import com.mycompany.myapp.repository.SalesOrderProductRepository;
import com.mycompany.myapp.service.SalesOrderProductService;
import com.mycompany.myapp.repository.search.SalesOrderProductSearchRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SalesOrderProductResource REST controller.
 *
 * @see SalesOrderProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KhushFinalApp.class)
public class SalesOrderProductResourceIntTest {

    private static final String DEFAULT_QUANTITY = "AAAAAAAAAA";
    private static final String UPDATED_QUANTITY = "BBBBBBBBBB";

    private static final Double DEFAULT_UNIT_PRICE = 1D;
    private static final Double UPDATED_UNIT_PRICE = 2D;

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;

    private static final Double DEFAULT_TOTAL = 1D;
    private static final Double UPDATED_TOTAL = 2D;

    private static final Boolean DEFAULT_FULFILLED = false;
    private static final Boolean UPDATED_FULFILLED = true;

    private static final Double DEFAULT_SHIPPED_QUANTITY = 1D;
    private static final Double UPDATED_SHIPPED_QUANTITY = 2D;

    private static final ZonedDateTime DEFAULT_SHIP_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SHIP_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private SalesOrderProductRepository salesOrderProductRepository;

    @Autowired
    private SalesOrderProductService salesOrderProductService;

    @Autowired
    private SalesOrderProductSearchRepository salesOrderProductSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSalesOrderProductMockMvc;

    private SalesOrderProduct salesOrderProduct;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SalesOrderProductResource salesOrderProductResource = new SalesOrderProductResource(salesOrderProductService);
        this.restSalesOrderProductMockMvc = MockMvcBuilders.standaloneSetup(salesOrderProductResource)
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
    public static SalesOrderProduct createEntity(EntityManager em) {
        SalesOrderProduct salesOrderProduct = new SalesOrderProduct()
            .quantity(DEFAULT_QUANTITY)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .discount(DEFAULT_DISCOUNT)
            .total(DEFAULT_TOTAL)
            .fulfilled(DEFAULT_FULFILLED)
            .shippedQuantity(DEFAULT_SHIPPED_QUANTITY)
            .shipDate(DEFAULT_SHIP_DATE);
        return salesOrderProduct;
    }

    @Before
    public void initTest() {
        salesOrderProductSearchRepository.deleteAll();
        salesOrderProduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalesOrderProduct() throws Exception {
        int databaseSizeBeforeCreate = salesOrderProductRepository.findAll().size();

        // Create the SalesOrderProduct
        restSalesOrderProductMockMvc.perform(post("/api/sales-order-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesOrderProduct)))
            .andExpect(status().isCreated());

        // Validate the SalesOrderProduct in the database
        List<SalesOrderProduct> salesOrderProductList = salesOrderProductRepository.findAll();
        assertThat(salesOrderProductList).hasSize(databaseSizeBeforeCreate + 1);
        SalesOrderProduct testSalesOrderProduct = salesOrderProductList.get(salesOrderProductList.size() - 1);
        assertThat(testSalesOrderProduct.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testSalesOrderProduct.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testSalesOrderProduct.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testSalesOrderProduct.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testSalesOrderProduct.isFulfilled()).isEqualTo(DEFAULT_FULFILLED);
        assertThat(testSalesOrderProduct.getShippedQuantity()).isEqualTo(DEFAULT_SHIPPED_QUANTITY);
        assertThat(testSalesOrderProduct.getShipDate()).isEqualTo(DEFAULT_SHIP_DATE);

        // Validate the SalesOrderProduct in Elasticsearch
        SalesOrderProduct salesOrderProductEs = salesOrderProductSearchRepository.findOne(testSalesOrderProduct.getId());
        assertThat(salesOrderProductEs).isEqualToComparingFieldByField(testSalesOrderProduct);
    }

    @Test
    @Transactional
    public void createSalesOrderProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salesOrderProductRepository.findAll().size();

        // Create the SalesOrderProduct with an existing ID
        salesOrderProduct.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesOrderProductMockMvc.perform(post("/api/sales-order-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesOrderProduct)))
            .andExpect(status().isBadRequest());

        // Validate the SalesOrderProduct in the database
        List<SalesOrderProduct> salesOrderProductList = salesOrderProductRepository.findAll();
        assertThat(salesOrderProductList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSalesOrderProducts() throws Exception {
        // Initialize the database
        salesOrderProductRepository.saveAndFlush(salesOrderProduct);

        // Get all the salesOrderProductList
        restSalesOrderProductMockMvc.perform(get("/api/sales-order-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesOrderProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.toString())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].fulfilled").value(hasItem(DEFAULT_FULFILLED.booleanValue())))
            .andExpect(jsonPath("$.[*].shippedQuantity").value(hasItem(DEFAULT_SHIPPED_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].shipDate").value(hasItem(sameInstant(DEFAULT_SHIP_DATE))));
    }

    @Test
    @Transactional
    public void getSalesOrderProduct() throws Exception {
        // Initialize the database
        salesOrderProductRepository.saveAndFlush(salesOrderProduct);

        // Get the salesOrderProduct
        restSalesOrderProductMockMvc.perform(get("/api/sales-order-products/{id}", salesOrderProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salesOrderProduct.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.toString()))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.fulfilled").value(DEFAULT_FULFILLED.booleanValue()))
            .andExpect(jsonPath("$.shippedQuantity").value(DEFAULT_SHIPPED_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.shipDate").value(sameInstant(DEFAULT_SHIP_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingSalesOrderProduct() throws Exception {
        // Get the salesOrderProduct
        restSalesOrderProductMockMvc.perform(get("/api/sales-order-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalesOrderProduct() throws Exception {
        // Initialize the database
        salesOrderProductService.save(salesOrderProduct);

        int databaseSizeBeforeUpdate = salesOrderProductRepository.findAll().size();

        // Update the salesOrderProduct
        SalesOrderProduct updatedSalesOrderProduct = salesOrderProductRepository.findOne(salesOrderProduct.getId());
        updatedSalesOrderProduct
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .discount(UPDATED_DISCOUNT)
            .total(UPDATED_TOTAL)
            .fulfilled(UPDATED_FULFILLED)
            .shippedQuantity(UPDATED_SHIPPED_QUANTITY)
            .shipDate(UPDATED_SHIP_DATE);

        restSalesOrderProductMockMvc.perform(put("/api/sales-order-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSalesOrderProduct)))
            .andExpect(status().isOk());

        // Validate the SalesOrderProduct in the database
        List<SalesOrderProduct> salesOrderProductList = salesOrderProductRepository.findAll();
        assertThat(salesOrderProductList).hasSize(databaseSizeBeforeUpdate);
        SalesOrderProduct testSalesOrderProduct = salesOrderProductList.get(salesOrderProductList.size() - 1);
        assertThat(testSalesOrderProduct.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testSalesOrderProduct.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testSalesOrderProduct.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testSalesOrderProduct.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testSalesOrderProduct.isFulfilled()).isEqualTo(UPDATED_FULFILLED);
        assertThat(testSalesOrderProduct.getShippedQuantity()).isEqualTo(UPDATED_SHIPPED_QUANTITY);
        assertThat(testSalesOrderProduct.getShipDate()).isEqualTo(UPDATED_SHIP_DATE);

        // Validate the SalesOrderProduct in Elasticsearch
        SalesOrderProduct salesOrderProductEs = salesOrderProductSearchRepository.findOne(testSalesOrderProduct.getId());
        assertThat(salesOrderProductEs).isEqualToComparingFieldByField(testSalesOrderProduct);
    }

    @Test
    @Transactional
    public void updateNonExistingSalesOrderProduct() throws Exception {
        int databaseSizeBeforeUpdate = salesOrderProductRepository.findAll().size();

        // Create the SalesOrderProduct

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSalesOrderProductMockMvc.perform(put("/api/sales-order-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesOrderProduct)))
            .andExpect(status().isCreated());

        // Validate the SalesOrderProduct in the database
        List<SalesOrderProduct> salesOrderProductList = salesOrderProductRepository.findAll();
        assertThat(salesOrderProductList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSalesOrderProduct() throws Exception {
        // Initialize the database
        salesOrderProductService.save(salesOrderProduct);

        int databaseSizeBeforeDelete = salesOrderProductRepository.findAll().size();

        // Get the salesOrderProduct
        restSalesOrderProductMockMvc.perform(delete("/api/sales-order-products/{id}", salesOrderProduct.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean salesOrderProductExistsInEs = salesOrderProductSearchRepository.exists(salesOrderProduct.getId());
        assertThat(salesOrderProductExistsInEs).isFalse();

        // Validate the database is empty
        List<SalesOrderProduct> salesOrderProductList = salesOrderProductRepository.findAll();
        assertThat(salesOrderProductList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSalesOrderProduct() throws Exception {
        // Initialize the database
        salesOrderProductService.save(salesOrderProduct);

        // Search the salesOrderProduct
        restSalesOrderProductMockMvc.perform(get("/api/_search/sales-order-products?query=id:" + salesOrderProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesOrderProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.toString())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].fulfilled").value(hasItem(DEFAULT_FULFILLED.booleanValue())))
            .andExpect(jsonPath("$.[*].shippedQuantity").value(hasItem(DEFAULT_SHIPPED_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].shipDate").value(hasItem(sameInstant(DEFAULT_SHIP_DATE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesOrderProduct.class);
        SalesOrderProduct salesOrderProduct1 = new SalesOrderProduct();
        salesOrderProduct1.setId(1L);
        SalesOrderProduct salesOrderProduct2 = new SalesOrderProduct();
        salesOrderProduct2.setId(salesOrderProduct1.getId());
        assertThat(salesOrderProduct1).isEqualTo(salesOrderProduct2);
        salesOrderProduct2.setId(2L);
        assertThat(salesOrderProduct1).isNotEqualTo(salesOrderProduct2);
        salesOrderProduct1.setId(null);
        assertThat(salesOrderProduct1).isNotEqualTo(salesOrderProduct2);
    }
}
