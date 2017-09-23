package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.KhushFinalApp;

import com.mycompany.myapp.domain.CompanyCategory;
import com.mycompany.myapp.repository.CompanyCategoryRepository;
import com.mycompany.myapp.service.CompanyCategoryService;
import com.mycompany.myapp.repository.search.CompanyCategorySearchRepository;
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
 * Test class for the CompanyCategoryResource REST controller.
 *
 * @see CompanyCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KhushFinalApp.class)
public class CompanyCategoryResourceIntTest {

    private static final String DEFAULT_CATEGORY_ID = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CompanyCategoryRepository companyCategoryRepository;

    @Autowired
    private CompanyCategoryService companyCategoryService;

    @Autowired
    private CompanyCategorySearchRepository companyCategorySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyCategoryMockMvc;

    private CompanyCategory companyCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyCategoryResource companyCategoryResource = new CompanyCategoryResource(companyCategoryService);
        this.restCompanyCategoryMockMvc = MockMvcBuilders.standaloneSetup(companyCategoryResource)
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
    public static CompanyCategory createEntity(EntityManager em) {
        CompanyCategory companyCategory = new CompanyCategory()
            .categoryId(DEFAULT_CATEGORY_ID)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return companyCategory;
    }

    @Before
    public void initTest() {
        companyCategorySearchRepository.deleteAll();
        companyCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyCategory() throws Exception {
        int databaseSizeBeforeCreate = companyCategoryRepository.findAll().size();

        // Create the CompanyCategory
        restCompanyCategoryMockMvc.perform(post("/api/company-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyCategory)))
            .andExpect(status().isCreated());

        // Validate the CompanyCategory in the database
        List<CompanyCategory> companyCategoryList = companyCategoryRepository.findAll();
        assertThat(companyCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyCategory testCompanyCategory = companyCategoryList.get(companyCategoryList.size() - 1);
        assertThat(testCompanyCategory.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testCompanyCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompanyCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the CompanyCategory in Elasticsearch
        CompanyCategory companyCategoryEs = companyCategorySearchRepository.findOne(testCompanyCategory.getId());
        assertThat(companyCategoryEs).isEqualToComparingFieldByField(testCompanyCategory);
    }

    @Test
    @Transactional
    public void createCompanyCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyCategoryRepository.findAll().size();

        // Create the CompanyCategory with an existing ID
        companyCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyCategoryMockMvc.perform(post("/api/company-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyCategory)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyCategory in the database
        List<CompanyCategory> companyCategoryList = companyCategoryRepository.findAll();
        assertThat(companyCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompanyCategories() throws Exception {
        // Initialize the database
        companyCategoryRepository.saveAndFlush(companyCategory);

        // Get all the companyCategoryList
        restCompanyCategoryMockMvc.perform(get("/api/company-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCompanyCategory() throws Exception {
        // Initialize the database
        companyCategoryRepository.saveAndFlush(companyCategory);

        // Get the companyCategory
        restCompanyCategoryMockMvc.perform(get("/api/company-categories/{id}", companyCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyCategory.getId().intValue()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyCategory() throws Exception {
        // Get the companyCategory
        restCompanyCategoryMockMvc.perform(get("/api/company-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyCategory() throws Exception {
        // Initialize the database
        companyCategoryService.save(companyCategory);

        int databaseSizeBeforeUpdate = companyCategoryRepository.findAll().size();

        // Update the companyCategory
        CompanyCategory updatedCompanyCategory = companyCategoryRepository.findOne(companyCategory.getId());
        updatedCompanyCategory
            .categoryId(UPDATED_CATEGORY_ID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restCompanyCategoryMockMvc.perform(put("/api/company-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompanyCategory)))
            .andExpect(status().isOk());

        // Validate the CompanyCategory in the database
        List<CompanyCategory> companyCategoryList = companyCategoryRepository.findAll();
        assertThat(companyCategoryList).hasSize(databaseSizeBeforeUpdate);
        CompanyCategory testCompanyCategory = companyCategoryList.get(companyCategoryList.size() - 1);
        assertThat(testCompanyCategory.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testCompanyCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompanyCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the CompanyCategory in Elasticsearch
        CompanyCategory companyCategoryEs = companyCategorySearchRepository.findOne(testCompanyCategory.getId());
        assertThat(companyCategoryEs).isEqualToComparingFieldByField(testCompanyCategory);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyCategory() throws Exception {
        int databaseSizeBeforeUpdate = companyCategoryRepository.findAll().size();

        // Create the CompanyCategory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyCategoryMockMvc.perform(put("/api/company-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyCategory)))
            .andExpect(status().isCreated());

        // Validate the CompanyCategory in the database
        List<CompanyCategory> companyCategoryList = companyCategoryRepository.findAll();
        assertThat(companyCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompanyCategory() throws Exception {
        // Initialize the database
        companyCategoryService.save(companyCategory);

        int databaseSizeBeforeDelete = companyCategoryRepository.findAll().size();

        // Get the companyCategory
        restCompanyCategoryMockMvc.perform(delete("/api/company-categories/{id}", companyCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean companyCategoryExistsInEs = companyCategorySearchRepository.exists(companyCategory.getId());
        assertThat(companyCategoryExistsInEs).isFalse();

        // Validate the database is empty
        List<CompanyCategory> companyCategoryList = companyCategoryRepository.findAll();
        assertThat(companyCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCompanyCategory() throws Exception {
        // Initialize the database
        companyCategoryService.save(companyCategory);

        // Search the companyCategory
        restCompanyCategoryMockMvc.perform(get("/api/_search/company-categories?query=id:" + companyCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyCategory.class);
        CompanyCategory companyCategory1 = new CompanyCategory();
        companyCategory1.setId(1L);
        CompanyCategory companyCategory2 = new CompanyCategory();
        companyCategory2.setId(companyCategory1.getId());
        assertThat(companyCategory1).isEqualTo(companyCategory2);
        companyCategory2.setId(2L);
        assertThat(companyCategory1).isNotEqualTo(companyCategory2);
        companyCategory1.setId(null);
        assertThat(companyCategory1).isNotEqualTo(companyCategory2);
    }
}
