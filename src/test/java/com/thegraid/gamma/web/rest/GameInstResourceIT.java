package com.thegraid.gamma.web.rest;

import static com.thegraid.gamma.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.thegraid.gamma.IntegrationTest;
import com.thegraid.gamma.domain.GameInst;
import com.thegraid.gamma.repository.GameInstRepository;
import com.thegraid.gamma.service.dto.GameInstDTO;
import com.thegraid.gamma.service.mapper.GameInstMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link GameInstResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GameInstResourceIT {

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    private static final String DEFAULT_GAME_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GAME_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HOST_URL = "AAAAAAAAAA";
    private static final String UPDATED_HOST_URL = "BBBBBBBBBB";

    private static final String DEFAULT_PASSCODE = "AAAAAAAAAA";
    private static final String UPDATED_PASSCODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_STARTED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_STARTED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FINISHED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FINISHED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_SCORE_A = 1;
    private static final Integer UPDATED_SCORE_A = 2;

    private static final Integer DEFAULT_SCORE_B = 1;
    private static final Integer UPDATED_SCORE_B = 2;

    private static final Integer DEFAULT_TICKS = 1;
    private static final Integer UPDATED_TICKS = 2;

    private static final String ENTITY_API_URL = "/api/game-insts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GameInstRepository gameInstRepository;

    @Autowired
    private GameInstMapper gameInstMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGameInstMockMvc;

    private GameInst gameInst;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameInst createEntity(EntityManager em) {
        GameInst gameInst = new GameInst()
            .version(DEFAULT_VERSION)
            .gameName(DEFAULT_GAME_NAME)
            .hostUrl(DEFAULT_HOST_URL)
            .passcode(DEFAULT_PASSCODE)
            .created(DEFAULT_CREATED)
            .started(DEFAULT_STARTED)
            .finished(DEFAULT_FINISHED)
            .updated(DEFAULT_UPDATED)
            .scoreA(DEFAULT_SCORE_A)
            .scoreB(DEFAULT_SCORE_B)
            .ticks(DEFAULT_TICKS);
        return gameInst;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameInst createUpdatedEntity(EntityManager em) {
        GameInst gameInst = new GameInst()
            .version(UPDATED_VERSION)
            .gameName(UPDATED_GAME_NAME)
            .hostUrl(UPDATED_HOST_URL)
            .passcode(UPDATED_PASSCODE)
            .created(UPDATED_CREATED)
            .started(UPDATED_STARTED)
            .finished(UPDATED_FINISHED)
            .updated(UPDATED_UPDATED)
            .scoreA(UPDATED_SCORE_A)
            .scoreB(UPDATED_SCORE_B)
            .ticks(UPDATED_TICKS);
        return gameInst;
    }

    @BeforeEach
    public void initTest() {
        gameInst = createEntity(em);
    }

    @Test
    @Transactional
    void createGameInst() throws Exception {
        int databaseSizeBeforeCreate = gameInstRepository.findAll().size();
        // Create the GameInst
        GameInstDTO gameInstDTO = gameInstMapper.toDto(gameInst);
        restGameInstMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gameInstDTO))
            )
            .andExpect(status().isCreated());

        // Validate the GameInst in the database
        List<GameInst> gameInstList = gameInstRepository.findAll();
        assertThat(gameInstList).hasSize(databaseSizeBeforeCreate + 1);
        GameInst testGameInst = gameInstList.get(gameInstList.size() - 1);
        assertThat(testGameInst.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testGameInst.getGameName()).isEqualTo(DEFAULT_GAME_NAME);
        assertThat(testGameInst.getHostUrl()).isEqualTo(DEFAULT_HOST_URL);
        assertThat(testGameInst.getPasscode()).isEqualTo(DEFAULT_PASSCODE);
        assertThat(testGameInst.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testGameInst.getStarted()).isEqualTo(DEFAULT_STARTED);
        assertThat(testGameInst.getFinished()).isEqualTo(DEFAULT_FINISHED);
        assertThat(testGameInst.getUpdated()).isEqualTo(DEFAULT_UPDATED);
        assertThat(testGameInst.getScoreA()).isEqualTo(DEFAULT_SCORE_A);
        assertThat(testGameInst.getScoreB()).isEqualTo(DEFAULT_SCORE_B);
        assertThat(testGameInst.getTicks()).isEqualTo(DEFAULT_TICKS);
    }

    @Test
    @Transactional
    void createGameInstWithExistingId() throws Exception {
        // Create the GameInst with an existing ID
        gameInst.setId(1L);
        GameInstDTO gameInstDTO = gameInstMapper.toDto(gameInst);

        int databaseSizeBeforeCreate = gameInstRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameInstMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gameInstDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameInst in the database
        List<GameInst> gameInstList = gameInstRepository.findAll();
        assertThat(gameInstList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = gameInstRepository.findAll().size();
        // set the field null
        gameInst.setCreated(null);

        // Create the GameInst, which fails.
        GameInstDTO gameInstDTO = gameInstMapper.toDto(gameInst);

        restGameInstMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gameInstDTO))
            )
            .andExpect(status().isBadRequest());

        List<GameInst> gameInstList = gameInstRepository.findAll();
        assertThat(gameInstList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = gameInstRepository.findAll().size();
        // set the field null
        gameInst.setUpdated(null);

        // Create the GameInst, which fails.
        GameInstDTO gameInstDTO = gameInstMapper.toDto(gameInst);

        restGameInstMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gameInstDTO))
            )
            .andExpect(status().isBadRequest());

        List<GameInst> gameInstList = gameInstRepository.findAll();
        assertThat(gameInstList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGameInsts() throws Exception {
        // Initialize the database
        gameInstRepository.saveAndFlush(gameInst);

        // Get all the gameInstList
        restGameInstMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gameInst.getId().intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].gameName").value(hasItem(DEFAULT_GAME_NAME)))
            .andExpect(jsonPath("$.[*].hostUrl").value(hasItem(DEFAULT_HOST_URL)))
            .andExpect(jsonPath("$.[*].passcode").value(hasItem(DEFAULT_PASSCODE)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].started").value(hasItem(sameInstant(DEFAULT_STARTED))))
            .andExpect(jsonPath("$.[*].finished").value(hasItem(sameInstant(DEFAULT_FINISHED))))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(sameInstant(DEFAULT_UPDATED))))
            .andExpect(jsonPath("$.[*].scoreA").value(hasItem(DEFAULT_SCORE_A)))
            .andExpect(jsonPath("$.[*].scoreB").value(hasItem(DEFAULT_SCORE_B)))
            .andExpect(jsonPath("$.[*].ticks").value(hasItem(DEFAULT_TICKS)));
    }

    @Test
    @Transactional
    void getGameInst() throws Exception {
        // Initialize the database
        gameInstRepository.saveAndFlush(gameInst);

        // Get the gameInst
        restGameInstMockMvc
            .perform(get(ENTITY_API_URL_ID, gameInst.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gameInst.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.gameName").value(DEFAULT_GAME_NAME))
            .andExpect(jsonPath("$.hostUrl").value(DEFAULT_HOST_URL))
            .andExpect(jsonPath("$.passcode").value(DEFAULT_PASSCODE))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)))
            .andExpect(jsonPath("$.started").value(sameInstant(DEFAULT_STARTED)))
            .andExpect(jsonPath("$.finished").value(sameInstant(DEFAULT_FINISHED)))
            .andExpect(jsonPath("$.updated").value(sameInstant(DEFAULT_UPDATED)))
            .andExpect(jsonPath("$.scoreA").value(DEFAULT_SCORE_A))
            .andExpect(jsonPath("$.scoreB").value(DEFAULT_SCORE_B))
            .andExpect(jsonPath("$.ticks").value(DEFAULT_TICKS));
    }

    @Test
    @Transactional
    void getNonExistingGameInst() throws Exception {
        // Get the gameInst
        restGameInstMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGameInst() throws Exception {
        // Initialize the database
        gameInstRepository.saveAndFlush(gameInst);

        int databaseSizeBeforeUpdate = gameInstRepository.findAll().size();

        // Update the gameInst
        GameInst updatedGameInst = gameInstRepository.findById(gameInst.getId()).get();
        // Disconnect from session so that the updates on updatedGameInst are not directly saved in db
        em.detach(updatedGameInst);
        updatedGameInst
            .version(UPDATED_VERSION)
            .gameName(UPDATED_GAME_NAME)
            .hostUrl(UPDATED_HOST_URL)
            .passcode(UPDATED_PASSCODE)
            .created(UPDATED_CREATED)
            .started(UPDATED_STARTED)
            .finished(UPDATED_FINISHED)
            .updated(UPDATED_UPDATED)
            .scoreA(UPDATED_SCORE_A)
            .scoreB(UPDATED_SCORE_B)
            .ticks(UPDATED_TICKS);
        GameInstDTO gameInstDTO = gameInstMapper.toDto(updatedGameInst);

        restGameInstMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gameInstDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gameInstDTO))
            )
            .andExpect(status().isOk());

        // Validate the GameInst in the database
        List<GameInst> gameInstList = gameInstRepository.findAll();
        assertThat(gameInstList).hasSize(databaseSizeBeforeUpdate);
        GameInst testGameInst = gameInstList.get(gameInstList.size() - 1);
        assertThat(testGameInst.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testGameInst.getGameName()).isEqualTo(UPDATED_GAME_NAME);
        assertThat(testGameInst.getHostUrl()).isEqualTo(UPDATED_HOST_URL);
        assertThat(testGameInst.getPasscode()).isEqualTo(UPDATED_PASSCODE);
        assertThat(testGameInst.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testGameInst.getStarted()).isEqualTo(UPDATED_STARTED);
        assertThat(testGameInst.getFinished()).isEqualTo(UPDATED_FINISHED);
        assertThat(testGameInst.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testGameInst.getScoreA()).isEqualTo(UPDATED_SCORE_A);
        assertThat(testGameInst.getScoreB()).isEqualTo(UPDATED_SCORE_B);
        assertThat(testGameInst.getTicks()).isEqualTo(UPDATED_TICKS);
    }

    @Test
    @Transactional
    void putNonExistingGameInst() throws Exception {
        int databaseSizeBeforeUpdate = gameInstRepository.findAll().size();
        gameInst.setId(count.incrementAndGet());

        // Create the GameInst
        GameInstDTO gameInstDTO = gameInstMapper.toDto(gameInst);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameInstMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gameInstDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gameInstDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameInst in the database
        List<GameInst> gameInstList = gameInstRepository.findAll();
        assertThat(gameInstList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGameInst() throws Exception {
        int databaseSizeBeforeUpdate = gameInstRepository.findAll().size();
        gameInst.setId(count.incrementAndGet());

        // Create the GameInst
        GameInstDTO gameInstDTO = gameInstMapper.toDto(gameInst);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameInstMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gameInstDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameInst in the database
        List<GameInst> gameInstList = gameInstRepository.findAll();
        assertThat(gameInstList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGameInst() throws Exception {
        int databaseSizeBeforeUpdate = gameInstRepository.findAll().size();
        gameInst.setId(count.incrementAndGet());

        // Create the GameInst
        GameInstDTO gameInstDTO = gameInstMapper.toDto(gameInst);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameInstMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gameInstDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GameInst in the database
        List<GameInst> gameInstList = gameInstRepository.findAll();
        assertThat(gameInstList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGameInstWithPatch() throws Exception {
        // Initialize the database
        gameInstRepository.saveAndFlush(gameInst);

        int databaseSizeBeforeUpdate = gameInstRepository.findAll().size();

        // Update the gameInst using partial update
        GameInst partialUpdatedGameInst = new GameInst();
        partialUpdatedGameInst.setId(gameInst.getId());

        partialUpdatedGameInst
            .version(UPDATED_VERSION)
            .started(UPDATED_STARTED)
            .updated(UPDATED_UPDATED)
            .scoreB(UPDATED_SCORE_B)
            .ticks(UPDATED_TICKS);

        restGameInstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGameInst.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGameInst))
            )
            .andExpect(status().isOk());

        // Validate the GameInst in the database
        List<GameInst> gameInstList = gameInstRepository.findAll();
        assertThat(gameInstList).hasSize(databaseSizeBeforeUpdate);
        GameInst testGameInst = gameInstList.get(gameInstList.size() - 1);
        assertThat(testGameInst.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testGameInst.getGameName()).isEqualTo(DEFAULT_GAME_NAME);
        assertThat(testGameInst.getHostUrl()).isEqualTo(DEFAULT_HOST_URL);
        assertThat(testGameInst.getPasscode()).isEqualTo(DEFAULT_PASSCODE);
        assertThat(testGameInst.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testGameInst.getStarted()).isEqualTo(UPDATED_STARTED);
        assertThat(testGameInst.getFinished()).isEqualTo(DEFAULT_FINISHED);
        assertThat(testGameInst.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testGameInst.getScoreA()).isEqualTo(DEFAULT_SCORE_A);
        assertThat(testGameInst.getScoreB()).isEqualTo(UPDATED_SCORE_B);
        assertThat(testGameInst.getTicks()).isEqualTo(UPDATED_TICKS);
    }

    @Test
    @Transactional
    void fullUpdateGameInstWithPatch() throws Exception {
        // Initialize the database
        gameInstRepository.saveAndFlush(gameInst);

        int databaseSizeBeforeUpdate = gameInstRepository.findAll().size();

        // Update the gameInst using partial update
        GameInst partialUpdatedGameInst = new GameInst();
        partialUpdatedGameInst.setId(gameInst.getId());

        partialUpdatedGameInst
            .version(UPDATED_VERSION)
            .gameName(UPDATED_GAME_NAME)
            .hostUrl(UPDATED_HOST_URL)
            .passcode(UPDATED_PASSCODE)
            .created(UPDATED_CREATED)
            .started(UPDATED_STARTED)
            .finished(UPDATED_FINISHED)
            .updated(UPDATED_UPDATED)
            .scoreA(UPDATED_SCORE_A)
            .scoreB(UPDATED_SCORE_B)
            .ticks(UPDATED_TICKS);

        restGameInstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGameInst.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGameInst))
            )
            .andExpect(status().isOk());

        // Validate the GameInst in the database
        List<GameInst> gameInstList = gameInstRepository.findAll();
        assertThat(gameInstList).hasSize(databaseSizeBeforeUpdate);
        GameInst testGameInst = gameInstList.get(gameInstList.size() - 1);
        assertThat(testGameInst.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testGameInst.getGameName()).isEqualTo(UPDATED_GAME_NAME);
        assertThat(testGameInst.getHostUrl()).isEqualTo(UPDATED_HOST_URL);
        assertThat(testGameInst.getPasscode()).isEqualTo(UPDATED_PASSCODE);
        assertThat(testGameInst.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testGameInst.getStarted()).isEqualTo(UPDATED_STARTED);
        assertThat(testGameInst.getFinished()).isEqualTo(UPDATED_FINISHED);
        assertThat(testGameInst.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testGameInst.getScoreA()).isEqualTo(UPDATED_SCORE_A);
        assertThat(testGameInst.getScoreB()).isEqualTo(UPDATED_SCORE_B);
        assertThat(testGameInst.getTicks()).isEqualTo(UPDATED_TICKS);
    }

    @Test
    @Transactional
    void patchNonExistingGameInst() throws Exception {
        int databaseSizeBeforeUpdate = gameInstRepository.findAll().size();
        gameInst.setId(count.incrementAndGet());

        // Create the GameInst
        GameInstDTO gameInstDTO = gameInstMapper.toDto(gameInst);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameInstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gameInstDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gameInstDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameInst in the database
        List<GameInst> gameInstList = gameInstRepository.findAll();
        assertThat(gameInstList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGameInst() throws Exception {
        int databaseSizeBeforeUpdate = gameInstRepository.findAll().size();
        gameInst.setId(count.incrementAndGet());

        // Create the GameInst
        GameInstDTO gameInstDTO = gameInstMapper.toDto(gameInst);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameInstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gameInstDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameInst in the database
        List<GameInst> gameInstList = gameInstRepository.findAll();
        assertThat(gameInstList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGameInst() throws Exception {
        int databaseSizeBeforeUpdate = gameInstRepository.findAll().size();
        gameInst.setId(count.incrementAndGet());

        // Create the GameInst
        GameInstDTO gameInstDTO = gameInstMapper.toDto(gameInst);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameInstMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gameInstDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GameInst in the database
        List<GameInst> gameInstList = gameInstRepository.findAll();
        assertThat(gameInstList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGameInst() throws Exception {
        // Initialize the database
        gameInstRepository.saveAndFlush(gameInst);

        int databaseSizeBeforeDelete = gameInstRepository.findAll().size();

        // Delete the gameInst
        restGameInstMockMvc
            .perform(delete(ENTITY_API_URL_ID, gameInst.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GameInst> gameInstList = gameInstRepository.findAll();
        assertThat(gameInstList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
