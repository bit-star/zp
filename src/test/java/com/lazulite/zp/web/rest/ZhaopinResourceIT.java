package com.lazulite.zp.web.rest;

import com.lazulite.zp.ZpApp;
import com.lazulite.zp.domain.Zhaopin;
import com.lazulite.zp.repository.ZhaopinRepository;
import com.lazulite.zp.service.ZhaopinService;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.lazulite.zp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ZhaopinResource} REST controller.
 */
@SpringBootTest(classes = ZpApp.class)
public class ZhaopinResourceIT {

    private static final String DEFAULT_ZWMC = "AAAAAAAAAA";
    private static final String UPDATED_ZWMC = "BBBBBBBBBB";

    private static final String DEFAULT_GSMC = "AAAAAAAAAA";
    private static final String UPDATED_GSMC = "BBBBBBBBBB";

    private static final String DEFAULT_GZDD = "AAAAAAAAAA";
    private static final String UPDATED_GZDD = "BBBBBBBBBB";

    private static final Long DEFAULT_XZ_LOW = 1L;
    private static final Long UPDATED_XZ_LOW = 2L;

    private static final Long DEFAULT_XZ_HEIGHT = 1L;
    private static final Long UPDATED_XZ_HEIGHT = 2L;

    private static final Instant DEFAULT_PTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final Long DEFAULT_CLUSTER = 1L;
    private static final Long UPDATED_CLUSTER = 2L;

    @Autowired
    private ZhaopinRepository zhaopinRepository;

    @Autowired
    private ZhaopinService zhaopinService;

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

    private MockMvc restZhaopinMockMvc;

    private Zhaopin zhaopin;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ZhaopinResource zhaopinResource = new ZhaopinResource(zhaopinService);
        this.restZhaopinMockMvc = MockMvcBuilders.standaloneSetup(zhaopinResource)
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
    public static Zhaopin createEntity(EntityManager em) {
        Zhaopin zhaopin = new Zhaopin()
            .zwmc(DEFAULT_ZWMC)
            .gsmc(DEFAULT_GSMC)
            .gzdd(DEFAULT_GZDD)
            .xzLow(DEFAULT_XZ_LOW)
            .xzHeight(DEFAULT_XZ_HEIGHT)
            .ptime(DEFAULT_PTIME)
            .href(DEFAULT_HREF)
            .cluster(DEFAULT_CLUSTER);
        return zhaopin;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zhaopin createUpdatedEntity(EntityManager em) {
        Zhaopin zhaopin = new Zhaopin()
            .zwmc(UPDATED_ZWMC)
            .gsmc(UPDATED_GSMC)
            .gzdd(UPDATED_GZDD)
            .xzLow(UPDATED_XZ_LOW)
            .xzHeight(UPDATED_XZ_HEIGHT)
            .ptime(UPDATED_PTIME)
            .href(UPDATED_HREF)
            .cluster(UPDATED_CLUSTER);
        return zhaopin;
    }

    @BeforeEach
    public void initTest() {
        zhaopin = createEntity(em);
    }

    @Test
    @Transactional
    public void createZhaopin() throws Exception {
        int databaseSizeBeforeCreate = zhaopinRepository.findAll().size();

        // Create the Zhaopin
        restZhaopinMockMvc.perform(post("/api/zhaopins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zhaopin)))
            .andExpect(status().isCreated());

        // Validate the Zhaopin in the database
        List<Zhaopin> zhaopinList = zhaopinRepository.findAll();
        assertThat(zhaopinList).hasSize(databaseSizeBeforeCreate + 1);
        Zhaopin testZhaopin = zhaopinList.get(zhaopinList.size() - 1);
        assertThat(testZhaopin.getZwmc()).isEqualTo(DEFAULT_ZWMC);
        assertThat(testZhaopin.getGsmc()).isEqualTo(DEFAULT_GSMC);
        assertThat(testZhaopin.getGzdd()).isEqualTo(DEFAULT_GZDD);
        assertThat(testZhaopin.getXzLow()).isEqualTo(DEFAULT_XZ_LOW);
        assertThat(testZhaopin.getXzHeight()).isEqualTo(DEFAULT_XZ_HEIGHT);
        assertThat(testZhaopin.getPtime()).isEqualTo(DEFAULT_PTIME);
        assertThat(testZhaopin.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testZhaopin.getCluster()).isEqualTo(DEFAULT_CLUSTER);
    }

    @Test
    @Transactional
    public void createZhaopinWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = zhaopinRepository.findAll().size();

        // Create the Zhaopin with an existing ID
        zhaopin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restZhaopinMockMvc.perform(post("/api/zhaopins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zhaopin)))
            .andExpect(status().isBadRequest());

        // Validate the Zhaopin in the database
        List<Zhaopin> zhaopinList = zhaopinRepository.findAll();
        assertThat(zhaopinList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllZhaopins() throws Exception {
        // Initialize the database
        zhaopinRepository.saveAndFlush(zhaopin);

        // Get all the zhaopinList
        restZhaopinMockMvc.perform(get("/api/zhaopins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zhaopin.getId().intValue())))
            .andExpect(jsonPath("$.[*].zwmc").value(hasItem(DEFAULT_ZWMC.toString())))
            .andExpect(jsonPath("$.[*].gsmc").value(hasItem(DEFAULT_GSMC.toString())))
            .andExpect(jsonPath("$.[*].gzdd").value(hasItem(DEFAULT_GZDD.toString())))
            .andExpect(jsonPath("$.[*].xzLow").value(hasItem(DEFAULT_XZ_LOW.intValue())))
            .andExpect(jsonPath("$.[*].xzHeight").value(hasItem(DEFAULT_XZ_HEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].ptime").value(hasItem(DEFAULT_PTIME.toString())))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF.toString())))
            .andExpect(jsonPath("$.[*].cluster").value(hasItem(DEFAULT_CLUSTER.intValue())));
    }
    
    @Test
    @Transactional
    public void getZhaopin() throws Exception {
        // Initialize the database
        zhaopinRepository.saveAndFlush(zhaopin);

        // Get the zhaopin
        restZhaopinMockMvc.perform(get("/api/zhaopins/{id}", zhaopin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(zhaopin.getId().intValue()))
            .andExpect(jsonPath("$.zwmc").value(DEFAULT_ZWMC.toString()))
            .andExpect(jsonPath("$.gsmc").value(DEFAULT_GSMC.toString()))
            .andExpect(jsonPath("$.gzdd").value(DEFAULT_GZDD.toString()))
            .andExpect(jsonPath("$.xzLow").value(DEFAULT_XZ_LOW.intValue()))
            .andExpect(jsonPath("$.xzHeight").value(DEFAULT_XZ_HEIGHT.intValue()))
            .andExpect(jsonPath("$.ptime").value(DEFAULT_PTIME.toString()))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF.toString()))
            .andExpect(jsonPath("$.cluster").value(DEFAULT_CLUSTER.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingZhaopin() throws Exception {
        // Get the zhaopin
        restZhaopinMockMvc.perform(get("/api/zhaopins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateZhaopin() throws Exception {
        // Initialize the database
        zhaopinService.save(zhaopin);

        int databaseSizeBeforeUpdate = zhaopinRepository.findAll().size();

        // Update the zhaopin
        Zhaopin updatedZhaopin = zhaopinRepository.findById(zhaopin.getId()).get();
        // Disconnect from session so that the updates on updatedZhaopin are not directly saved in db
        em.detach(updatedZhaopin);
        updatedZhaopin
            .zwmc(UPDATED_ZWMC)
            .gsmc(UPDATED_GSMC)
            .gzdd(UPDATED_GZDD)
            .xzLow(UPDATED_XZ_LOW)
            .xzHeight(UPDATED_XZ_HEIGHT)
            .ptime(UPDATED_PTIME)
            .href(UPDATED_HREF)
            .cluster(UPDATED_CLUSTER);

        restZhaopinMockMvc.perform(put("/api/zhaopins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedZhaopin)))
            .andExpect(status().isOk());

        // Validate the Zhaopin in the database
        List<Zhaopin> zhaopinList = zhaopinRepository.findAll();
        assertThat(zhaopinList).hasSize(databaseSizeBeforeUpdate);
        Zhaopin testZhaopin = zhaopinList.get(zhaopinList.size() - 1);
        assertThat(testZhaopin.getZwmc()).isEqualTo(UPDATED_ZWMC);
        assertThat(testZhaopin.getGsmc()).isEqualTo(UPDATED_GSMC);
        assertThat(testZhaopin.getGzdd()).isEqualTo(UPDATED_GZDD);
        assertThat(testZhaopin.getXzLow()).isEqualTo(UPDATED_XZ_LOW);
        assertThat(testZhaopin.getXzHeight()).isEqualTo(UPDATED_XZ_HEIGHT);
        assertThat(testZhaopin.getPtime()).isEqualTo(UPDATED_PTIME);
        assertThat(testZhaopin.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testZhaopin.getCluster()).isEqualTo(UPDATED_CLUSTER);
    }

    @Test
    @Transactional
    public void updateNonExistingZhaopin() throws Exception {
        int databaseSizeBeforeUpdate = zhaopinRepository.findAll().size();

        // Create the Zhaopin

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZhaopinMockMvc.perform(put("/api/zhaopins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zhaopin)))
            .andExpect(status().isBadRequest());

        // Validate the Zhaopin in the database
        List<Zhaopin> zhaopinList = zhaopinRepository.findAll();
        assertThat(zhaopinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteZhaopin() throws Exception {
        // Initialize the database
        zhaopinService.save(zhaopin);

        int databaseSizeBeforeDelete = zhaopinRepository.findAll().size();

        // Delete the zhaopin
        restZhaopinMockMvc.perform(delete("/api/zhaopins/{id}", zhaopin.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Zhaopin> zhaopinList = zhaopinRepository.findAll();
        assertThat(zhaopinList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Zhaopin.class);
        Zhaopin zhaopin1 = new Zhaopin();
        zhaopin1.setId(1L);
        Zhaopin zhaopin2 = new Zhaopin();
        zhaopin2.setId(zhaopin1.getId());
        assertThat(zhaopin1).isEqualTo(zhaopin2);
        zhaopin2.setId(2L);
        assertThat(zhaopin1).isNotEqualTo(zhaopin2);
        zhaopin1.setId(null);
        assertThat(zhaopin1).isNotEqualTo(zhaopin2);
    }
}
