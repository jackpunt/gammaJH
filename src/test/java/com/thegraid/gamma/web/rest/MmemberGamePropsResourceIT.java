package com.thegraid.gamma.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.thegraid.gamma.IntegrationTest;
import com.thegraid.gamma.domain.MmemberGameProps;
import com.thegraid.gamma.repository.MmemberGamePropsRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MmemberGamePropsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MmemberGamePropsResourceIT {

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final Long DEFAULT_SEED = 1L;
    private static final Long UPDATED_SEED = 2L;

    private static final String DEFAULT_MAP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MAP_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_MAP_SIZE = 1L;
    private static final Long UPDATED_MAP_SIZE = 2L;

    private static final Long DEFAULT_NPC_COUNT = 1L;
    private static final Long UPDATED_NPC_COUNT = 2L;

    private static final String DEFAULT_JSON_PROPS = "AAAAAAAAAA";
    private static final String UPDATED_JSON_PROPS = "BBBBBBBBBB";

    private static final String DEFAULT_CONFIG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONFIG_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/mmember-game-props";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MmemberGamePropsRepository mmemberGamePropsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMmemberGamePropsMockMvc;

    private MmemberGameProps mmemberGameProps;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MmemberGameProps createEntity(EntityManager em) {
        MmemberGameProps mmemberGameProps = new MmemberGameProps()
            .version(DEFAULT_VERSION)
            .seed(DEFAULT_SEED)
            .mapName(DEFAULT_MAP_NAME)
            .mapSize(DEFAULT_MAP_SIZE)
            .npcCount(DEFAULT_NPC_COUNT)
            .jsonProps(DEFAULT_JSON_PROPS)
            .configName(DEFAULT_CONFIG_NAME);
        return mmemberGameProps;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MmemberGameProps createUpdatedEntity(EntityManager em) {
        MmemberGameProps mmemberGameProps = new MmemberGameProps()
            .version(UPDATED_VERSION)
            .seed(UPDATED_SEED)
            .mapName(UPDATED_MAP_NAME)
            .mapSize(UPDATED_MAP_SIZE)
            .npcCount(UPDATED_NPC_COUNT)
            .jsonProps(UPDATED_JSON_PROPS)
            .configName(UPDATED_CONFIG_NAME);
        return mmemberGameProps;
    }

    @BeforeEach
    public void initTest() {
        mmemberGameProps = createEntity(em);
    }

    @Test
    @Transactional
    void createMmemberGameProps() throws Exception {
        int databaseSizeBeforeCreate = mmemberGamePropsRepository.findAll().size();
        // Create the MmemberGameProps
        restMmemberGamePropsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mmemberGameProps))
            )
            .andExpect(status().isCreated());

        // Validate the MmemberGameProps in the database
        List<MmemberGameProps> mmemberGamePropsList = mmemberGamePropsRepository.findAll();
        assertThat(mmemberGamePropsList).hasSize(databaseSizeBeforeCreate + 1);
        MmemberGameProps testMmemberGameProps = mmemberGamePropsList.get(mmemberGamePropsList.size() - 1);
        assertThat(testMmemberGameProps.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testMmemberGameProps.getSeed()).isEqualTo(DEFAULT_SEED);
        assertThat(testMmemberGameProps.getMapName()).isEqualTo(DEFAULT_MAP_NAME);
        assertThat(testMmemberGameProps.getMapSize()).isEqualTo(DEFAULT_MAP_SIZE);
        assertThat(testMmemberGameProps.getNpcCount()).isEqualTo(DEFAULT_NPC_COUNT);
        assertThat(testMmemberGameProps.getJsonProps()).isEqualTo(DEFAULT_JSON_PROPS);
        assertThat(testMmemberGameProps.getConfigName()).isEqualTo(DEFAULT_CONFIG_NAME);
    }

    @Test
    @Transactional
    void createMmemberGamePropsWithExistingId() throws Exception {
        // Create the MmemberGameProps with an existing ID
        mmemberGameProps.setId(1L);

        int databaseSizeBeforeCreate = mmemberGamePropsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMmemberGamePropsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mmemberGameProps))
            )
            .andExpect(status().isBadRequest());

        // Validate the MmemberGameProps in the database
        List<MmemberGameProps> mmemberGamePropsList = mmemberGamePropsRepository.findAll();
        assertThat(mmemberGamePropsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMmemberGameProps() throws Exception {
        // Initialize the database
        mmemberGamePropsRepository.saveAndFlush(mmemberGameProps);

        // Get all the mmemberGamePropsList
        restMmemberGamePropsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mmemberGameProps.getId().intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].seed").value(hasItem(DEFAULT_SEED.intValue())))
            .andExpect(jsonPath("$.[*].mapName").value(hasItem(DEFAULT_MAP_NAME)))
            .andExpect(jsonPath("$.[*].mapSize").value(hasItem(DEFAULT_MAP_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].npcCount").value(hasItem(DEFAULT_NPC_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].jsonProps").value(hasItem(DEFAULT_JSON_PROPS)))
            .andExpect(jsonPath("$.[*].configName").value(hasItem(DEFAULT_CONFIG_NAME)));
    }

    @Test
    @Transactional
    void getMmemberGameProps() throws Exception {
        // Initialize the database
        mmemberGamePropsRepository.saveAndFlush(mmemberGameProps);

        // Get the mmemberGameProps
        restMmemberGamePropsMockMvc
            .perform(get(ENTITY_API_URL_ID, mmemberGameProps.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mmemberGameProps.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.seed").value(DEFAULT_SEED.intValue()))
            .andExpect(jsonPath("$.mapName").value(DEFAULT_MAP_NAME))
            .andExpect(jsonPath("$.mapSize").value(DEFAULT_MAP_SIZE.intValue()))
            .andExpect(jsonPath("$.npcCount").value(DEFAULT_NPC_COUNT.intValue()))
            .andExpect(jsonPath("$.jsonProps").value(DEFAULT_JSON_PROPS))
            .andExpect(jsonPath("$.configName").value(DEFAULT_CONFIG_NAME));
    }

    @Test
    @Transactional
    void getNonExistingMmemberGameProps() throws Exception {
        // Get the mmemberGameProps
        restMmemberGamePropsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMmemberGameProps() throws Exception {
        // Initialize the database
        mmemberGamePropsRepository.saveAndFlush(mmemberGameProps);

        int databaseSizeBeforeUpdate = mmemberGamePropsRepository.findAll().size();

        // Update the mmemberGameProps
        MmemberGameProps updatedMmemberGameProps = mmemberGamePropsRepository.findById(mmemberGameProps.getId()).get();
        // Disconnect from session so that the updates on updatedMmemberGameProps are not directly saved in db
        em.detach(updatedMmemberGameProps);
        updatedMmemberGameProps
            .version(UPDATED_VERSION)
            .seed(UPDATED_SEED)
            .mapName(UPDATED_MAP_NAME)
            .mapSize(UPDATED_MAP_SIZE)
            .npcCount(UPDATED_NPC_COUNT)
            .jsonProps(UPDATED_JSON_PROPS)
            .configName(UPDATED_CONFIG_NAME);

        restMmemberGamePropsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMmemberGameProps.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMmemberGameProps))
            )
            .andExpect(status().isOk());

        // Validate the MmemberGameProps in the database
        List<MmemberGameProps> mmemberGamePropsList = mmemberGamePropsRepository.findAll();
        assertThat(mmemberGamePropsList).hasSize(databaseSizeBeforeUpdate);
        MmemberGameProps testMmemberGameProps = mmemberGamePropsList.get(mmemberGamePropsList.size() - 1);
        assertThat(testMmemberGameProps.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testMmemberGameProps.getSeed()).isEqualTo(UPDATED_SEED);
        assertThat(testMmemberGameProps.getMapName()).isEqualTo(UPDATED_MAP_NAME);
        assertThat(testMmemberGameProps.getMapSize()).isEqualTo(UPDATED_MAP_SIZE);
        assertThat(testMmemberGameProps.getNpcCount()).isEqualTo(UPDATED_NPC_COUNT);
        assertThat(testMmemberGameProps.getJsonProps()).isEqualTo(UPDATED_JSON_PROPS);
        assertThat(testMmemberGameProps.getConfigName()).isEqualTo(UPDATED_CONFIG_NAME);
    }

    @Test
    @Transactional
    void putNonExistingMmemberGameProps() throws Exception {
        int databaseSizeBeforeUpdate = mmemberGamePropsRepository.findAll().size();
        mmemberGameProps.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMmemberGamePropsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mmemberGameProps.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mmemberGameProps))
            )
            .andExpect(status().isBadRequest());

        // Validate the MmemberGameProps in the database
        List<MmemberGameProps> mmemberGamePropsList = mmemberGamePropsRepository.findAll();
        assertThat(mmemberGamePropsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMmemberGameProps() throws Exception {
        int databaseSizeBeforeUpdate = mmemberGamePropsRepository.findAll().size();
        mmemberGameProps.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMmemberGamePropsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mmemberGameProps))
            )
            .andExpect(status().isBadRequest());

        // Validate the MmemberGameProps in the database
        List<MmemberGameProps> mmemberGamePropsList = mmemberGamePropsRepository.findAll();
        assertThat(mmemberGamePropsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMmemberGameProps() throws Exception {
        int databaseSizeBeforeUpdate = mmemberGamePropsRepository.findAll().size();
        mmemberGameProps.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMmemberGamePropsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mmemberGameProps))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MmemberGameProps in the database
        List<MmemberGameProps> mmemberGamePropsList = mmemberGamePropsRepository.findAll();
        assertThat(mmemberGamePropsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMmemberGamePropsWithPatch() throws Exception {
        // Initialize the database
        mmemberGamePropsRepository.saveAndFlush(mmemberGameProps);

        int databaseSizeBeforeUpdate = mmemberGamePropsRepository.findAll().size();

        // Update the mmemberGameProps using partial update
        MmemberGameProps partialUpdatedMmemberGameProps = new MmemberGameProps();
        partialUpdatedMmemberGameProps.setId(mmemberGameProps.getId());

        partialUpdatedMmemberGameProps.seed(UPDATED_SEED).npcCount(UPDATED_NPC_COUNT);

        restMmemberGamePropsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMmemberGameProps.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMmemberGameProps))
            )
            .andExpect(status().isOk());

        // Validate the MmemberGameProps in the database
        List<MmemberGameProps> mmemberGamePropsList = mmemberGamePropsRepository.findAll();
        assertThat(mmemberGamePropsList).hasSize(databaseSizeBeforeUpdate);
        MmemberGameProps testMmemberGameProps = mmemberGamePropsList.get(mmemberGamePropsList.size() - 1);
        assertThat(testMmemberGameProps.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testMmemberGameProps.getSeed()).isEqualTo(UPDATED_SEED);
        assertThat(testMmemberGameProps.getMapName()).isEqualTo(DEFAULT_MAP_NAME);
        assertThat(testMmemberGameProps.getMapSize()).isEqualTo(DEFAULT_MAP_SIZE);
        assertThat(testMmemberGameProps.getNpcCount()).isEqualTo(UPDATED_NPC_COUNT);
        assertThat(testMmemberGameProps.getJsonProps()).isEqualTo(DEFAULT_JSON_PROPS);
        assertThat(testMmemberGameProps.getConfigName()).isEqualTo(DEFAULT_CONFIG_NAME);
    }

    @Test
    @Transactional
    void fullUpdateMmemberGamePropsWithPatch() throws Exception {
        // Initialize the database
        mmemberGamePropsRepository.saveAndFlush(mmemberGameProps);

        int databaseSizeBeforeUpdate = mmemberGamePropsRepository.findAll().size();

        // Update the mmemberGameProps using partial update
        MmemberGameProps partialUpdatedMmemberGameProps = new MmemberGameProps();
        partialUpdatedMmemberGameProps.setId(mmemberGameProps.getId());

        partialUpdatedMmemberGameProps
            .version(UPDATED_VERSION)
            .seed(UPDATED_SEED)
            .mapName(UPDATED_MAP_NAME)
            .mapSize(UPDATED_MAP_SIZE)
            .npcCount(UPDATED_NPC_COUNT)
            .jsonProps(UPDATED_JSON_PROPS)
            .configName(UPDATED_CONFIG_NAME);

        restMmemberGamePropsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMmemberGameProps.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMmemberGameProps))
            )
            .andExpect(status().isOk());

        // Validate the MmemberGameProps in the database
        List<MmemberGameProps> mmemberGamePropsList = mmemberGamePropsRepository.findAll();
        assertThat(mmemberGamePropsList).hasSize(databaseSizeBeforeUpdate);
        MmemberGameProps testMmemberGameProps = mmemberGamePropsList.get(mmemberGamePropsList.size() - 1);
        assertThat(testMmemberGameProps.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testMmemberGameProps.getSeed()).isEqualTo(UPDATED_SEED);
        assertThat(testMmemberGameProps.getMapName()).isEqualTo(UPDATED_MAP_NAME);
        assertThat(testMmemberGameProps.getMapSize()).isEqualTo(UPDATED_MAP_SIZE);
        assertThat(testMmemberGameProps.getNpcCount()).isEqualTo(UPDATED_NPC_COUNT);
        assertThat(testMmemberGameProps.getJsonProps()).isEqualTo(UPDATED_JSON_PROPS);
        assertThat(testMmemberGameProps.getConfigName()).isEqualTo(UPDATED_CONFIG_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingMmemberGameProps() throws Exception {
        int databaseSizeBeforeUpdate = mmemberGamePropsRepository.findAll().size();
        mmemberGameProps.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMmemberGamePropsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mmemberGameProps.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mmemberGameProps))
            )
            .andExpect(status().isBadRequest());

        // Validate the MmemberGameProps in the database
        List<MmemberGameProps> mmemberGamePropsList = mmemberGamePropsRepository.findAll();
        assertThat(mmemberGamePropsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMmemberGameProps() throws Exception {
        int databaseSizeBeforeUpdate = mmemberGamePropsRepository.findAll().size();
        mmemberGameProps.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMmemberGamePropsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mmemberGameProps))
            )
            .andExpect(status().isBadRequest());

        // Validate the MmemberGameProps in the database
        List<MmemberGameProps> mmemberGamePropsList = mmemberGamePropsRepository.findAll();
        assertThat(mmemberGamePropsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMmemberGameProps() throws Exception {
        int databaseSizeBeforeUpdate = mmemberGamePropsRepository.findAll().size();
        mmemberGameProps.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMmemberGamePropsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mmemberGameProps))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MmemberGameProps in the database
        List<MmemberGameProps> mmemberGamePropsList = mmemberGamePropsRepository.findAll();
        assertThat(mmemberGamePropsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMmemberGameProps() throws Exception {
        // Initialize the database
        mmemberGamePropsRepository.saveAndFlush(mmemberGameProps);

        int databaseSizeBeforeDelete = mmemberGamePropsRepository.findAll().size();

        // Delete the mmemberGameProps
        restMmemberGamePropsMockMvc
            .perform(delete(ENTITY_API_URL_ID, mmemberGameProps.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MmemberGameProps> mmemberGamePropsList = mmemberGamePropsRepository.findAll();
        assertThat(mmemberGamePropsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
