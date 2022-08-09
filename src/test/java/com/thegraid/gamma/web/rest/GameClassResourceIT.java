package com.thegraid.gamma.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.thegraid.gamma.IntegrationTest;
import com.thegraid.gamma.domain.GameClass;
import com.thegraid.gamma.repository.GameClassRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link GameClassResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GameClassResourceIT {

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REVISION = "AAAAAAAAAA";
    private static final String UPDATED_REVISION = "BBBBBBBBBB";

    private static final String DEFAULT_LAUNCHER_PATH = "AAAAAAAAAA";
    private static final String UPDATED_LAUNCHER_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_GAME_PATH = "AAAAAAAAAA";
    private static final String UPDATED_GAME_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_DOCS_PATH = "AAAAAAAAAA";
    private static final String UPDATED_DOCS_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_PROPS_NAMES = "AAAAAAAAAA";
    private static final String UPDATED_PROPS_NAMES = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATEED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/game-classes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GameClassRepository gameClassRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGameClassMockMvc;

    private GameClass gameClass;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameClass createEntity(EntityManager em) {
        GameClass gameClass = new GameClass()
            .version(DEFAULT_VERSION)
            .name(DEFAULT_NAME)
            .revision(DEFAULT_REVISION)
            .launcherPath(DEFAULT_LAUNCHER_PATH)
            .gamePath(DEFAULT_GAME_PATH)
            .docsPath(DEFAULT_DOCS_PATH)
            .propsNames(DEFAULT_PROPS_NAMES)
            .updateed(DEFAULT_UPDATEED);
        return gameClass;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameClass createUpdatedEntity(EntityManager em) {
        GameClass gameClass = new GameClass()
            .version(UPDATED_VERSION)
            .name(UPDATED_NAME)
            .revision(UPDATED_REVISION)
            .launcherPath(UPDATED_LAUNCHER_PATH)
            .gamePath(UPDATED_GAME_PATH)
            .docsPath(UPDATED_DOCS_PATH)
            .propsNames(UPDATED_PROPS_NAMES)
            .updateed(UPDATED_UPDATEED);
        return gameClass;
    }

    @BeforeEach
    public void initTest() {
        gameClass = createEntity(em);
    }

    @Test
    @Transactional
    void createGameClass() throws Exception {
        int databaseSizeBeforeCreate = gameClassRepository.findAll().size();
        // Create the GameClass
        restGameClassMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gameClass))
            )
            .andExpect(status().isCreated());

        // Validate the GameClass in the database
        List<GameClass> gameClassList = gameClassRepository.findAll();
        assertThat(gameClassList).hasSize(databaseSizeBeforeCreate + 1);
        GameClass testGameClass = gameClassList.get(gameClassList.size() - 1);
        assertThat(testGameClass.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testGameClass.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGameClass.getRevision()).isEqualTo(DEFAULT_REVISION);
        assertThat(testGameClass.getLauncherPath()).isEqualTo(DEFAULT_LAUNCHER_PATH);
        assertThat(testGameClass.getGamePath()).isEqualTo(DEFAULT_GAME_PATH);
        assertThat(testGameClass.getDocsPath()).isEqualTo(DEFAULT_DOCS_PATH);
        assertThat(testGameClass.getPropsNames()).isEqualTo(DEFAULT_PROPS_NAMES);
        assertThat(testGameClass.getUpdateed()).isEqualTo(DEFAULT_UPDATEED);
    }

    @Test
    @Transactional
    void createGameClassWithExistingId() throws Exception {
        // Create the GameClass with an existing ID
        gameClass.setId(1L);

        int databaseSizeBeforeCreate = gameClassRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameClassMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gameClass))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameClass in the database
        List<GameClass> gameClassList = gameClassRepository.findAll();
        assertThat(gameClassList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGameClasses() throws Exception {
        // Initialize the database
        gameClassRepository.saveAndFlush(gameClass);

        // Get all the gameClassList
        restGameClassMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gameClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].revision").value(hasItem(DEFAULT_REVISION)))
            .andExpect(jsonPath("$.[*].launcherPath").value(hasItem(DEFAULT_LAUNCHER_PATH)))
            .andExpect(jsonPath("$.[*].gamePath").value(hasItem(DEFAULT_GAME_PATH)))
            .andExpect(jsonPath("$.[*].docsPath").value(hasItem(DEFAULT_DOCS_PATH)))
            .andExpect(jsonPath("$.[*].propsNames").value(hasItem(DEFAULT_PROPS_NAMES)))
            .andExpect(jsonPath("$.[*].updateed").value(hasItem(DEFAULT_UPDATEED.toString())));
    }

    @Test
    @Transactional
    void getGameClass() throws Exception {
        // Initialize the database
        gameClassRepository.saveAndFlush(gameClass);

        // Get the gameClass
        restGameClassMockMvc
            .perform(get(ENTITY_API_URL_ID, gameClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gameClass.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.revision").value(DEFAULT_REVISION))
            .andExpect(jsonPath("$.launcherPath").value(DEFAULT_LAUNCHER_PATH))
            .andExpect(jsonPath("$.gamePath").value(DEFAULT_GAME_PATH))
            .andExpect(jsonPath("$.docsPath").value(DEFAULT_DOCS_PATH))
            .andExpect(jsonPath("$.propsNames").value(DEFAULT_PROPS_NAMES))
            .andExpect(jsonPath("$.updateed").value(DEFAULT_UPDATEED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingGameClass() throws Exception {
        // Get the gameClass
        restGameClassMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGameClass() throws Exception {
        // Initialize the database
        gameClassRepository.saveAndFlush(gameClass);

        int databaseSizeBeforeUpdate = gameClassRepository.findAll().size();

        // Update the gameClass
        GameClass updatedGameClass = gameClassRepository.findById(gameClass.getId()).get();
        // Disconnect from session so that the updates on updatedGameClass are not directly saved in db
        em.detach(updatedGameClass);
        updatedGameClass
            .version(UPDATED_VERSION)
            .name(UPDATED_NAME)
            .revision(UPDATED_REVISION)
            .launcherPath(UPDATED_LAUNCHER_PATH)
            .gamePath(UPDATED_GAME_PATH)
            .docsPath(UPDATED_DOCS_PATH)
            .propsNames(UPDATED_PROPS_NAMES)
            .updateed(UPDATED_UPDATEED);

        restGameClassMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGameClass.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGameClass))
            )
            .andExpect(status().isOk());

        // Validate the GameClass in the database
        List<GameClass> gameClassList = gameClassRepository.findAll();
        assertThat(gameClassList).hasSize(databaseSizeBeforeUpdate);
        GameClass testGameClass = gameClassList.get(gameClassList.size() - 1);
        assertThat(testGameClass.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testGameClass.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGameClass.getRevision()).isEqualTo(UPDATED_REVISION);
        assertThat(testGameClass.getLauncherPath()).isEqualTo(UPDATED_LAUNCHER_PATH);
        assertThat(testGameClass.getGamePath()).isEqualTo(UPDATED_GAME_PATH);
        assertThat(testGameClass.getDocsPath()).isEqualTo(UPDATED_DOCS_PATH);
        assertThat(testGameClass.getPropsNames()).isEqualTo(UPDATED_PROPS_NAMES);
        assertThat(testGameClass.getUpdateed()).isEqualTo(UPDATED_UPDATEED);
    }

    @Test
    @Transactional
    void putNonExistingGameClass() throws Exception {
        int databaseSizeBeforeUpdate = gameClassRepository.findAll().size();
        gameClass.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameClassMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gameClass.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gameClass))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameClass in the database
        List<GameClass> gameClassList = gameClassRepository.findAll();
        assertThat(gameClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGameClass() throws Exception {
        int databaseSizeBeforeUpdate = gameClassRepository.findAll().size();
        gameClass.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameClassMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gameClass))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameClass in the database
        List<GameClass> gameClassList = gameClassRepository.findAll();
        assertThat(gameClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGameClass() throws Exception {
        int databaseSizeBeforeUpdate = gameClassRepository.findAll().size();
        gameClass.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameClassMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gameClass))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GameClass in the database
        List<GameClass> gameClassList = gameClassRepository.findAll();
        assertThat(gameClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGameClassWithPatch() throws Exception {
        // Initialize the database
        gameClassRepository.saveAndFlush(gameClass);

        int databaseSizeBeforeUpdate = gameClassRepository.findAll().size();

        // Update the gameClass using partial update
        GameClass partialUpdatedGameClass = new GameClass();
        partialUpdatedGameClass.setId(gameClass.getId());

        partialUpdatedGameClass.name(UPDATED_NAME).revision(UPDATED_REVISION).launcherPath(UPDATED_LAUNCHER_PATH);

        restGameClassMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGameClass.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGameClass))
            )
            .andExpect(status().isOk());

        // Validate the GameClass in the database
        List<GameClass> gameClassList = gameClassRepository.findAll();
        assertThat(gameClassList).hasSize(databaseSizeBeforeUpdate);
        GameClass testGameClass = gameClassList.get(gameClassList.size() - 1);
        assertThat(testGameClass.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testGameClass.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGameClass.getRevision()).isEqualTo(UPDATED_REVISION);
        assertThat(testGameClass.getLauncherPath()).isEqualTo(UPDATED_LAUNCHER_PATH);
        assertThat(testGameClass.getGamePath()).isEqualTo(DEFAULT_GAME_PATH);
        assertThat(testGameClass.getDocsPath()).isEqualTo(DEFAULT_DOCS_PATH);
        assertThat(testGameClass.getPropsNames()).isEqualTo(DEFAULT_PROPS_NAMES);
        assertThat(testGameClass.getUpdateed()).isEqualTo(DEFAULT_UPDATEED);
    }

    @Test
    @Transactional
    void fullUpdateGameClassWithPatch() throws Exception {
        // Initialize the database
        gameClassRepository.saveAndFlush(gameClass);

        int databaseSizeBeforeUpdate = gameClassRepository.findAll().size();

        // Update the gameClass using partial update
        GameClass partialUpdatedGameClass = new GameClass();
        partialUpdatedGameClass.setId(gameClass.getId());

        partialUpdatedGameClass
            .version(UPDATED_VERSION)
            .name(UPDATED_NAME)
            .revision(UPDATED_REVISION)
            .launcherPath(UPDATED_LAUNCHER_PATH)
            .gamePath(UPDATED_GAME_PATH)
            .docsPath(UPDATED_DOCS_PATH)
            .propsNames(UPDATED_PROPS_NAMES)
            .updateed(UPDATED_UPDATEED);

        restGameClassMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGameClass.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGameClass))
            )
            .andExpect(status().isOk());

        // Validate the GameClass in the database
        List<GameClass> gameClassList = gameClassRepository.findAll();
        assertThat(gameClassList).hasSize(databaseSizeBeforeUpdate);
        GameClass testGameClass = gameClassList.get(gameClassList.size() - 1);
        assertThat(testGameClass.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testGameClass.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGameClass.getRevision()).isEqualTo(UPDATED_REVISION);
        assertThat(testGameClass.getLauncherPath()).isEqualTo(UPDATED_LAUNCHER_PATH);
        assertThat(testGameClass.getGamePath()).isEqualTo(UPDATED_GAME_PATH);
        assertThat(testGameClass.getDocsPath()).isEqualTo(UPDATED_DOCS_PATH);
        assertThat(testGameClass.getPropsNames()).isEqualTo(UPDATED_PROPS_NAMES);
        assertThat(testGameClass.getUpdateed()).isEqualTo(UPDATED_UPDATEED);
    }

    @Test
    @Transactional
    void patchNonExistingGameClass() throws Exception {
        int databaseSizeBeforeUpdate = gameClassRepository.findAll().size();
        gameClass.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameClassMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gameClass.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gameClass))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameClass in the database
        List<GameClass> gameClassList = gameClassRepository.findAll();
        assertThat(gameClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGameClass() throws Exception {
        int databaseSizeBeforeUpdate = gameClassRepository.findAll().size();
        gameClass.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameClassMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gameClass))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameClass in the database
        List<GameClass> gameClassList = gameClassRepository.findAll();
        assertThat(gameClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGameClass() throws Exception {
        int databaseSizeBeforeUpdate = gameClassRepository.findAll().size();
        gameClass.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameClassMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gameClass))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GameClass in the database
        List<GameClass> gameClassList = gameClassRepository.findAll();
        assertThat(gameClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGameClass() throws Exception {
        // Initialize the database
        gameClassRepository.saveAndFlush(gameClass);

        int databaseSizeBeforeDelete = gameClassRepository.findAll().size();

        // Delete the gameClass
        restGameClassMockMvc
            .perform(delete(ENTITY_API_URL_ID, gameClass.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GameClass> gameClassList = gameClassRepository.findAll();
        assertThat(gameClassList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
