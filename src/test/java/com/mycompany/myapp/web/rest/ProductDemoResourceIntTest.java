package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.KhushFinalApp;

import com.mycompany.myapp.domain.ProductDemo;
import com.mycompany.myapp.repository.ProductDemoRepository;
import com.mycompany.myapp.service.ProductDemoService;
import com.mycompany.myapp.repository.search.ProductDemoSearchRepository;
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

import com.mycompany.myapp.domain.enumeration.WeightUnit;
/**
 * Test class for the ProductDemoResource REST controller.
 *
 * @see ProductDemoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KhushFinalApp.class)
public class ProductDemoResourceIntTest {

    private static final String DEFAULT_PRODCUT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRODCUT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_FETURES = "AAAAAAAAAA";
    private static final String UPDATED_FETURES = "BBBBBBBBBB";

    private static final String DEFAULT_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_SIZE = "BBBBBBBBBB";

    private static final Double DEFAULT_UNIT_WEIGHT = 1D;
    private static final Double UPDATED_UNIT_WEIGHT = 2D;

    private static final Double DEFAULT_UINT_IN_SOTCK = 1D;
    private static final Double UPDATED_UINT_IN_SOTCK = 2D;

    private static final Double DEFAULT_UNIT_AVAILABLE = 1D;
    private static final Double UPDATED_UNIT_AVAILABLE = 2D;

    private static final Double DEFAULT_UNIT_BLOCKED = 1D;
    private static final Double UPDATED_UNIT_BLOCKED = 2D;

    private static final Double DEFAULT_UNIT_SHIPPED = 1D;
    private static final Double UPDATED_UNIT_SHIPPED = 2D;

    private static final Double DEFAULT_REORDER_LEVEL = 1D;
    private static final Double UPDATED_REORDER_LEVEL = 2D;

    private static final WeightUnit DEFAULT_WEIGHT_UINIT = WeightUnit.GRAMS;
    private static final WeightUnit UPDATED_WEIGHT_UINIT = WeightUnit.KG;

    @Autowired
    private ProductDemoRepository productDemoRepository;

    @Autowired
    private ProductDemoService productDemoService;

    @Autowired
    private ProductDemoSearchRepository productDemoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductDemoMockMvc;

    private ProductDemo productDemo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductDemoResource productDemoResource = new ProductDemoResource(productDemoService);
        this.restProductDemoMockMvc = MockMvcBuilders.standaloneSetup(productDemoResource)
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
    public static ProductDemo createEntity(EntityManager em) {
        ProductDemo productDemo = new ProductDemo()
            .prodcutId(DEFAULT_PRODCUT_ID)
            .name(DEFAULT_NAME)
            .details(DEFAULT_DETAILS)
            .fetures(DEFAULT_FETURES)
            .size(DEFAULT_SIZE)
            .unitWeight(DEFAULT_UNIT_WEIGHT)
            .uintInSotck(DEFAULT_UINT_IN_SOTCK)
            .unitAvailable(DEFAULT_UNIT_AVAILABLE)
            .unitBlocked(DEFAULT_UNIT_BLOCKED)
            .unitShipped(DEFAULT_UNIT_SHIPPED)
            .reorderLevel(DEFAULT_REORDER_LEVEL)
            .weightUinit(DEFAULT_WEIGHT_UINIT);
        return productDemo;
    }

    @Before
    public void initTest() {
        productDemoSearchRepository.deleteAll();
        productDemo = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductDemo() throws Exception {
        int databaseSizeBeforeCreate = productDemoRepository.findAll().size();

        // Create the ProductDemo
        restProductDemoMockMvc.perform(post("/api/product-demos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDemo)))
            .andExpect(status().isCreated());

        // Validate the ProductDemo in the database
        List<ProductDemo> productDemoList = productDemoRepository.findAll();
        assertThat(productDemoList).hasSize(databaseSizeBeforeCreate + 1);
        ProductDemo testProductDemo = productDemoList.get(productDemoList.size() - 1);
        assertThat(testProductDemo.getProdcutId()).isEqualTo(DEFAULT_PRODCUT_ID);
        assertThat(testProductDemo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductDemo.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testProductDemo.getFetures()).isEqualTo(DEFAULT_FETURES);
        assertThat(testProductDemo.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testProductDemo.getUnitWeight()).isEqualTo(DEFAULT_UNIT_WEIGHT);
        assertThat(testProductDemo.getUintInSotck()).isEqualTo(DEFAULT_UINT_IN_SOTCK);
        assertThat(testProductDemo.getUnitAvailable()).isEqualTo(DEFAULT_UNIT_AVAILABLE);
        assertThat(testProductDemo.getUnitBlocked()).isEqualTo(DEFAULT_UNIT_BLOCKED);
        assertThat(testProductDemo.getUnitShipped()).isEqualTo(DEFAULT_UNIT_SHIPPED);
        assertThat(testProductDemo.getReorderLevel()).isEqualTo(DEFAULT_REORDER_LEVEL);
        assertThat(testProductDemo.getWeightUinit()).isEqualTo(DEFAULT_WEIGHT_UINIT);

        // Validate the ProductDemo in Elasticsearch
        ProductDemo productDemoEs = productDemoSearchRepository.findOne(testProductDemo.getId());
        assertThat(productDemoEs).isEqualToComparingFieldByField(testProductDemo);
    }

    @Test
    @Transactional
    public void createProductDemoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productDemoRepository.findAll().size();

        // Create the ProductDemo with an existing ID
        productDemo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductDemoMockMvc.perform(post("/api/product-demos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDemo)))
            .andExpect(status().isBadRequest());

        // Validate the ProductDemo in the database
        List<ProductDemo> productDemoList = productDemoRepository.findAll();
        assertThat(productDemoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProductDemos() throws Exception {
        // Initialize the database
        productDemoRepository.saveAndFlush(productDemo);

        // Get all the productDemoList
        restProductDemoMockMvc.perform(get("/api/product-demos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productDemo.getId().intValue())))
            .andExpect(jsonPath("$.[*].prodcutId").value(hasItem(DEFAULT_PRODCUT_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].fetures").value(hasItem(DEFAULT_FETURES.toString())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.toString())))
            .andExpect(jsonPath("$.[*].unitWeight").value(hasItem(DEFAULT_UNIT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].uintInSotck").value(hasItem(DEFAULT_UINT_IN_SOTCK.doubleValue())))
            .andExpect(jsonPath("$.[*].unitAvailable").value(hasItem(DEFAULT_UNIT_AVAILABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].unitBlocked").value(hasItem(DEFAULT_UNIT_BLOCKED.doubleValue())))
            .andExpect(jsonPath("$.[*].unitShipped").value(hasItem(DEFAULT_UNIT_SHIPPED.doubleValue())))
            .andExpect(jsonPath("$.[*].reorderLevel").value(hasItem(DEFAULT_REORDER_LEVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].weightUinit").value(hasItem(DEFAULT_WEIGHT_UINIT.toString())));
    }

    @Test
    @Transactional
    public void getProductDemo() throws Exception {
        // Initialize the database
        productDemoRepository.saveAndFlush(productDemo);

        // Get the productDemo
        restProductDemoMockMvc.perform(get("/api/product-demos/{id}", productDemo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productDemo.getId().intValue()))
            .andExpect(jsonPath("$.prodcutId").value(DEFAULT_PRODCUT_ID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS.toString()))
            .andExpect(jsonPath("$.fetures").value(DEFAULT_FETURES.toString()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.toString()))
            .andExpect(jsonPath("$.unitWeight").value(DEFAULT_UNIT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.uintInSotck").value(DEFAULT_UINT_IN_SOTCK.doubleValue()))
            .andExpect(jsonPath("$.unitAvailable").value(DEFAULT_UNIT_AVAILABLE.doubleValue()))
            .andExpect(jsonPath("$.unitBlocked").value(DEFAULT_UNIT_BLOCKED.doubleValue()))
            .andExpect(jsonPath("$.unitShipped").value(DEFAULT_UNIT_SHIPPED.doubleValue()))
            .andExpect(jsonPath("$.reorderLevel").value(DEFAULT_REORDER_LEVEL.doubleValue()))
            .andExpect(jsonPath("$.weightUinit").value(DEFAULT_WEIGHT_UINIT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductDemo() throws Exception {
        // Get the productDemo
        restProductDemoMockMvc.perform(get("/api/product-demos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductDemo() throws Exception {
        // Initialize the database
        productDemoService.save(productDemo);

        int databaseSizeBeforeUpdate = productDemoRepository.findAll().size();

        // Update the productDemo
        ProductDemo updatedProductDemo = productDemoRepository.findOne(productDemo.getId());
        updatedProductDemo
            .prodcutId(UPDATED_PRODCUT_ID)
            .name(UPDATED_NAME)
            .details(UPDATED_DETAILS)
            .fetures(UPDATED_FETURES)
            .size(UPDATED_SIZE)
            .unitWeight(UPDATED_UNIT_WEIGHT)
            .uintInSotck(UPDATED_UINT_IN_SOTCK)
            .unitAvailable(UPDATED_UNIT_AVAILABLE)
            .unitBlocked(UPDATED_UNIT_BLOCKED)
            .unitShipped(UPDATED_UNIT_SHIPPED)
            .reorderLevel(UPDATED_REORDER_LEVEL)
            .weightUinit(UPDATED_WEIGHT_UINIT);

        restProductDemoMockMvc.perform(put("/api/product-demos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductDemo)))
            .andExpect(status().isOk());

        // Validate the ProductDemo in the database
        List<ProductDemo> productDemoList = productDemoRepository.findAll();
        assertThat(productDemoList).hasSize(databaseSizeBeforeUpdate);
        ProductDemo testProductDemo = productDemoList.get(productDemoList.size() - 1);
        assertThat(testProductDemo.getProdcutId()).isEqualTo(UPDATED_PRODCUT_ID);
        assertThat(testProductDemo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductDemo.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testProductDemo.getFetures()).isEqualTo(UPDATED_FETURES);
        assertThat(testProductDemo.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testProductDemo.getUnitWeight()).isEqualTo(UPDATED_UNIT_WEIGHT);
        assertThat(testProductDemo.getUintInSotck()).isEqualTo(UPDATED_UINT_IN_SOTCK);
        assertThat(testProductDemo.getUnitAvailable()).isEqualTo(UPDATED_UNIT_AVAILABLE);
        assertThat(testProductDemo.getUnitBlocked()).isEqualTo(UPDATED_UNIT_BLOCKED);
        assertThat(testProductDemo.getUnitShipped()).isEqualTo(UPDATED_UNIT_SHIPPED);
        assertThat(testProductDemo.getReorderLevel()).isEqualTo(UPDATED_REORDER_LEVEL);
        assertThat(testProductDemo.getWeightUinit()).isEqualTo(UPDATED_WEIGHT_UINIT);

        // Validate the ProductDemo in Elasticsearch
        ProductDemo productDemoEs = productDemoSearchRepository.findOne(testProductDemo.getId());
        assertThat(productDemoEs).isEqualToComparingFieldByField(testProductDemo);
    }

    @Test
    @Transactional
    public void updateNonExistingProductDemo() throws Exception {
        int databaseSizeBeforeUpdate = productDemoRepository.findAll().size();

        // Create the ProductDemo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductDemoMockMvc.perform(put("/api/product-demos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDemo)))
            .andExpect(status().isCreated());

        // Validate the ProductDemo in the database
        List<ProductDemo> productDemoList = productDemoRepository.findAll();
        assertThat(productDemoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProductDemo() throws Exception {
        // Initialize the database
        productDemoService.save(productDemo);

        int databaseSizeBeforeDelete = productDemoRepository.findAll().size();

        // Get the productDemo
        restProductDemoMockMvc.perform(delete("/api/product-demos/{id}", productDemo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean productDemoExistsInEs = productDemoSearchRepository.exists(productDemo.getId());
        assertThat(productDemoExistsInEs).isFalse();

        // Validate the database is empty
        List<ProductDemo> productDemoList = productDemoRepository.findAll();
        assertThat(productDemoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProductDemo() throws Exception {
        // Initialize the database
        productDemoService.save(productDemo);

        // Search the productDemo
        restProductDemoMockMvc.perform(get("/api/_search/product-demos?query=id:" + productDemo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productDemo.getId().intValue())))
            .andExpect(jsonPath("$.[*].prodcutId").value(hasItem(DEFAULT_PRODCUT_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].fetures").value(hasItem(DEFAULT_FETURES.toString())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.toString())))
            .andExpect(jsonPath("$.[*].unitWeight").value(hasItem(DEFAULT_UNIT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].uintInSotck").value(hasItem(DEFAULT_UINT_IN_SOTCK.doubleValue())))
            .andExpect(jsonPath("$.[*].unitAvailable").value(hasItem(DEFAULT_UNIT_AVAILABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].unitBlocked").value(hasItem(DEFAULT_UNIT_BLOCKED.doubleValue())))
            .andExpect(jsonPath("$.[*].unitShipped").value(hasItem(DEFAULT_UNIT_SHIPPED.doubleValue())))
            .andExpect(jsonPath("$.[*].reorderLevel").value(hasItem(DEFAULT_REORDER_LEVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].weightUinit").value(hasItem(DEFAULT_WEIGHT_UINIT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductDemo.class);
        ProductDemo productDemo1 = new ProductDemo();
        productDemo1.setId(1L);
        ProductDemo productDemo2 = new ProductDemo();
        productDemo2.setId(productDemo1.getId());
        assertThat(productDemo1).isEqualTo(productDemo2);
        productDemo2.setId(2L);
        assertThat(productDemo1).isNotEqualTo(productDemo2);
        productDemo1.setId(null);
        assertThat(productDemo1).isNotEqualTo(productDemo2);
    }
}
