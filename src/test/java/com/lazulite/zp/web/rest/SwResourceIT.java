package com.lazulite.zp.web.rest;

import com.lazulite.zp.ZpApp;
import com.lazulite.zp.domain.Sw;
import com.lazulite.zp.repository.SwRepository;
import com.lazulite.zp.service.SwService;
import com.lazulite.zp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.lazulite.zp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link SwResource} REST controller.
 */
@SpringBootTest(classes = ZpApp.class)
public class SwResourceIT {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    @Autowired
    private SwRepository swRepository;

    @Autowired
    private SwService swService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restSwMockMvc;

    private Sw sw;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SwResource swResource = new SwResource(swService);
        this.restSwMockMvc = MockMvcBuilders.standaloneSetup(swResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sw createEntity(EntityManager em) {
        Sw sw = new Sw()
            .text(DEFAULT_TEXT);
        return sw;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sw createUpdatedEntity(EntityManager em) {
        Sw sw = new Sw()
            .text(UPDATED_TEXT);
        return sw;
    }

    @BeforeEach
    public void initTest() {
        sw = createEntity(em);
    }

    @Test
    @Transactional
    public void createSw() throws Exception {
        int databaseSizeBeforeCreate = swRepository.findAll().size();

        // Create the Sw
        restSwMockMvc.perform(post("/api/sws")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sw)))
            .andExpect(status().isCreated());

        // Validate the Sw in the database
        List<Sw> swList = swRepository.findAll();
        assertThat(swList).hasSize(databaseSizeBeforeCreate + 1);
        Sw testSw = swList.get(swList.size() - 1);
        assertThat(testSw.getText()).isEqualTo(DEFAULT_TEXT);
    }

    @Test
    @Transactional
    public void createSwWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = swRepository.findAll().size();

        // Create the Sw with an existing ID
        sw.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSwMockMvc.perform(post("/api/sws")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sw)))
            .andExpect(status().isBadRequest());

        // Validate the Sw in the database
        List<Sw> swList = swRepository.findAll();
        assertThat(swList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSws() throws Exception {
        // Initialize the database
        swRepository.saveAndFlush(sw);

        // Get all the swList
        restSwMockMvc.perform(get("/api/sws?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sw.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())));
    }
    
    @Test
    @Transactional
    public void getSw() throws Exception {
        // Initialize the database
        swRepository.saveAndFlush(sw);

        // Get the sw
        restSwMockMvc.perform(get("/api/sws/{id}", sw.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sw.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSw() throws Exception {
        // Get the sw
        restSwMockMvc.perform(get("/api/sws/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSw() throws Exception {
        // Initialize the database
        swService.save(sw);

        int databaseSizeBeforeUpdate = swRepository.findAll().size();

        // Update the sw
        Sw updatedSw = swRepository.findById(sw.getId()).get();
        // Disconnect from session so that the updates on updatedSw are not directly saved in db
        em.detach(updatedSw);
        updatedSw
            .text(UPDATED_TEXT);

        restSwMockMvc.perform(put("/api/sws")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSw)))
            .andExpect(status().isOk());

        // Validate the Sw in the database
        List<Sw> swList = swRepository.findAll();
        assertThat(swList).hasSize(databaseSizeBeforeUpdate);
        Sw testSw = swList.get(swList.size() - 1);
        assertThat(testSw.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void updateNonExistingSw() throws Exception {
        int databaseSizeBeforeUpdate = swRepository.findAll().size();

        // Create the Sw

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSwMockMvc.perform(put("/api/sws")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sw)))
            .andExpect(status().isBadRequest());

        // Validate the Sw in the database
        List<Sw> swList = swRepository.findAll();
        assertThat(swList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSw() throws Exception {
        // Initialize the database
        swService.save(sw);

        int databaseSizeBeforeDelete = swRepository.findAll().size();

        // Delete the sw
        restSwMockMvc.perform(delete("/api/sws/{id}", sw.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Sw> swList = swRepository.findAll();
        assertThat(swList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sw.class);
        Sw sw1 = new Sw();
        sw1.setId(1L);
        Sw sw2 = new Sw();
        sw2.setId(sw1.getId());
        assertThat(sw1).isEqualTo(sw2);
        sw2.setId(2L);
        assertThat(sw1).isNotEqualTo(sw2);
        sw1.setId(null);
        assertThat(sw1).isNotEqualTo(sw2);
    }
}
