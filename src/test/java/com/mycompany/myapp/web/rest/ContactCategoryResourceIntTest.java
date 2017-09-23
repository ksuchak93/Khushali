package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.KhushFinalApp;

import com.mycompany.myapp.domain.ContactCategory;
import com.mycompany.myapp.repository.ContactCategoryRepository;
import com.mycompany.myapp.service.ContactCategoryService;
import com.mycompany.myapp.repository.search.ContactCategorySearchRepository;
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
 * Test class for the ContactCategoryResource REST controller.
 *
 * @see ContactCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KhushFinalApp.class)
public class ContactCategoryResourceIntTest {

    private static final String DEFAULT_CATEGORY_ID = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ContactCategoryRepository contactCategoryRepository;

    @Autowired
    private ContactCategoryService contactCategoryService;

    @Autowired
    private ContactCategorySearchRepository contactCategorySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContactCategoryMockMvc;

    private ContactCategory contactCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContactCategoryResource contactCategoryResource = new ContactCategoryResource(contactCategoryService);
        this.restContactCategoryMockMvc = MockMvcBuilders.standaloneSetup(contactCategoryResource)
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
    public static ContactCategory createEntity(EntityManager em) {
        ContactCategory contactCategory = new ContactCategory()
            .categoryId(DEFAULT_CATEGORY_ID)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return contactCategory;
    }

    @Before
    public void initTest() {
        contactCategorySearchRepository.deleteAll();
        contactCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createContactCategory() throws Exception {
        int databaseSizeBeforeCreate = contactCategoryRepository.findAll().size();

        // Create the ContactCategory
        restContactCategoryMockMvc.perform(post("/api/contact-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactCategory)))
            .andExpect(status().isCreated());

        // Validate the ContactCategory in the database
        List<ContactCategory> contactCategoryList = contactCategoryRepository.findAll();
        assertThat(contactCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        ContactCategory testContactCategory = contactCategoryList.get(contactCategoryList.size() - 1);
        assertThat(testContactCategory.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testContactCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContactCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the ContactCategory in Elasticsearch
        ContactCategory contactCategoryEs = contactCategorySearchRepository.findOne(testContactCategory.getId());
        assertThat(contactCategoryEs).isEqualToComparingFieldByField(testContactCategory);
    }

    @Test
    @Transactional
    public void createContactCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactCategoryRepository.findAll().size();

        // Create the ContactCategory with an existing ID
        contactCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactCategoryMockMvc.perform(post("/api/contact-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactCategory)))
            .andExpect(status().isBadRequest());

        // Validate the ContactCategory in the database
        List<ContactCategory> contactCategoryList = contactCategoryRepository.findAll();
        assertThat(contactCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllContactCategories() throws Exception {
        // Initialize the database
        contactCategoryRepository.saveAndFlush(contactCategory);

        // Get all the contactCategoryList
        restContactCategoryMockMvc.perform(get("/api/contact-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getContactCategory() throws Exception {
        // Initialize the database
        contactCategoryRepository.saveAndFlush(contactCategory);

        // Get the contactCategory
        restContactCategoryMockMvc.perform(get("/api/contact-categories/{id}", contactCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contactCategory.getId().intValue()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContactCategory() throws Exception {
        // Get the contactCategory
        restContactCategoryMockMvc.perform(get("/api/contact-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactCategory() throws Exception {
        // Initialize the database
        contactCategoryService.save(contactCategory);

        int databaseSizeBeforeUpdate = contactCategoryRepository.findAll().size();

        // Update the contactCategory
        ContactCategory updatedContactCategory = contactCategoryRepository.findOne(contactCategory.getId());
        updatedContactCategory
            .categoryId(UPDATED_CATEGORY_ID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restContactCategoryMockMvc.perform(put("/api/contact-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContactCategory)))
            .andExpect(status().isOk());

        // Validate the ContactCategory in the database
        List<ContactCategory> contactCategoryList = contactCategoryRepository.findAll();
        assertThat(contactCategoryList).hasSize(databaseSizeBeforeUpdate);
        ContactCategory testContactCategory = contactCategoryList.get(contactCategoryList.size() - 1);
        assertThat(testContactCategory.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testContactCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContactCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the ContactCategory in Elasticsearch
        ContactCategory contactCategoryEs = contactCategorySearchRepository.findOne(testContactCategory.getId());
        assertThat(contactCategoryEs).isEqualToComparingFieldByField(testContactCategory);
    }

    @Test
    @Transactional
    public void updateNonExistingContactCategory() throws Exception {
        int databaseSizeBeforeUpdate = contactCategoryRepository.findAll().size();

        // Create the ContactCategory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContactCategoryMockMvc.perform(put("/api/contact-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactCategory)))
            .andExpect(status().isCreated());

        // Validate the ContactCategory in the database
        List<ContactCategory> contactCategoryList = contactCategoryRepository.findAll();
        assertThat(contactCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContactCategory() throws Exception {
        // Initialize the database
        contactCategoryService.save(contactCategory);

        int databaseSizeBeforeDelete = contactCategoryRepository.findAll().size();

        // Get the contactCategory
        restContactCategoryMockMvc.perform(delete("/api/contact-categories/{id}", contactCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean contactCategoryExistsInEs = contactCategorySearchRepository.exists(contactCategory.getId());
        assertThat(contactCategoryExistsInEs).isFalse();

        // Validate the database is empty
        List<ContactCategory> contactCategoryList = contactCategoryRepository.findAll();
        assertThat(contactCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchContactCategory() throws Exception {
        // Initialize the database
        contactCategoryService.save(contactCategory);

        // Search the contactCategory
        restContactCategoryMockMvc.perform(get("/api/_search/contact-categories?query=id:" + contactCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactCategory.class);
        ContactCategory contactCategory1 = new ContactCategory();
        contactCategory1.setId(1L);
        ContactCategory contactCategory2 = new ContactCategory();
        contactCategory2.setId(contactCategory1.getId());
        assertThat(contactCategory1).isEqualTo(contactCategory2);
        contactCategory2.setId(2L);
        assertThat(contactCategory1).isNotEqualTo(contactCategory2);
        contactCategory1.setId(null);
        assertThat(contactCategory1).isNotEqualTo(contactCategory2);
    }
}
