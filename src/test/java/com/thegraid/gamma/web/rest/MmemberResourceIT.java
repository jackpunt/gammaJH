package com.thegraid.gamma.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.thegraid.gamma.IntegrationTest;
import com.thegraid.gamma.domain.Mmember;
import com.thegraid.gamma.repository.MmemberRepository;
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
 * Integration tests for the {@link MmemberResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MmemberResourceIT {

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String ENTITY_API_URL = "/api/mmembers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MmemberRepository mmemberRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMmemberMockMvc;

    private Mmember mmember;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mmember createEntity(EntityManager em) {
        Mmember mmember = new Mmember().version(DEFAULT_VERSION);
        return mmember;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mmember createUpdatedEntity(EntityManager em) {
        Mmember mmember = new Mmember().version(UPDATED_VERSION);
        return mmember;
    }

    @BeforeEach
    public void initTest() {
        mmember = createEntity(em);
    }

    @Test
    @Transactional
    void createMmember() throws Exception {
        int databaseSizeBeforeCreate = mmemberRepository.findAll().size();
        // Create the Mmember
        restMmemberMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mmember))
            )
            .andExpect(status().isCreated());

        // Validate the Mmember in the database
        List<Mmember> mmemberList = mmemberRepository.findAll();
        assertThat(mmemberList).hasSize(databaseSizeBeforeCreate + 1);
        Mmember testMmember = mmemberList.get(mmemberList.size() - 1);
        assertThat(testMmember.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createMmemberWithExistingId() throws Exception {
        // Create the Mmember with an existing ID
        mmember.setId(1L);

        int databaseSizeBeforeCreate = mmemberRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMmemberMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mmember))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mmember in the database
        List<Mmember> mmemberList = mmemberRepository.findAll();
        assertThat(mmemberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMmembers() throws Exception {
        // Initialize the database
        mmemberRepository.saveAndFlush(mmember);

        // Get all the mmemberList
        restMmemberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mmember.getId().intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())));
    }

    @Test
    @Transactional
    void getMmember() throws Exception {
        // Initialize the database
        mmemberRepository.saveAndFlush(mmember);

        // Get the mmember
        restMmemberMockMvc
            .perform(get(ENTITY_API_URL_ID, mmember.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mmember.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingMmember() throws Exception {
        // Get the mmember
        restMmemberMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMmember() throws Exception {
        // Initialize the database
        mmemberRepository.saveAndFlush(mmember);

        int databaseSizeBeforeUpdate = mmemberRepository.findAll().size();

        // Update the mmember
        Mmember updatedMmember = mmemberRepository.findById(mmember.getId()).get();
        // Disconnect from session so that the updates on updatedMmember are not directly saved in db
        em.detach(updatedMmember);
        updatedMmember.version(UPDATED_VERSION);

        restMmemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMmember.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMmember))
            )
            .andExpect(status().isOk());

        // Validate the Mmember in the database
        List<Mmember> mmemberList = mmemberRepository.findAll();
        assertThat(mmemberList).hasSize(databaseSizeBeforeUpdate);
        Mmember testMmember = mmemberList.get(mmemberList.size() - 1);
        assertThat(testMmember.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingMmember() throws Exception {
        int databaseSizeBeforeUpdate = mmemberRepository.findAll().size();
        mmember.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMmemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mmember.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mmember))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mmember in the database
        List<Mmember> mmemberList = mmemberRepository.findAll();
        assertThat(mmemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMmember() throws Exception {
        int databaseSizeBeforeUpdate = mmemberRepository.findAll().size();
        mmember.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMmemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mmember))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mmember in the database
        List<Mmember> mmemberList = mmemberRepository.findAll();
        assertThat(mmemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMmember() throws Exception {
        int databaseSizeBeforeUpdate = mmemberRepository.findAll().size();
        mmember.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMmemberMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mmember))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mmember in the database
        List<Mmember> mmemberList = mmemberRepository.findAll();
        assertThat(mmemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMmemberWithPatch() throws Exception {
        // Initialize the database
        mmemberRepository.saveAndFlush(mmember);

        int databaseSizeBeforeUpdate = mmemberRepository.findAll().size();

        // Update the mmember using partial update
        Mmember partialUpdatedMmember = new Mmember();
        partialUpdatedMmember.setId(mmember.getId());

        partialUpdatedMmember.version(UPDATED_VERSION);

        restMmemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMmember.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMmember))
            )
            .andExpect(status().isOk());

        // Validate the Mmember in the database
        List<Mmember> mmemberList = mmemberRepository.findAll();
        assertThat(mmemberList).hasSize(databaseSizeBeforeUpdate);
        Mmember testMmember = mmemberList.get(mmemberList.size() - 1);
        assertThat(testMmember.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateMmemberWithPatch() throws Exception {
        // Initialize the database
        mmemberRepository.saveAndFlush(mmember);

        int databaseSizeBeforeUpdate = mmemberRepository.findAll().size();

        // Update the mmember using partial update
        Mmember partialUpdatedMmember = new Mmember();
        partialUpdatedMmember.setId(mmember.getId());

        partialUpdatedMmember.version(UPDATED_VERSION);

        restMmemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMmember.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMmember))
            )
            .andExpect(status().isOk());

        // Validate the Mmember in the database
        List<Mmember> mmemberList = mmemberRepository.findAll();
        assertThat(mmemberList).hasSize(databaseSizeBeforeUpdate);
        Mmember testMmember = mmemberList.get(mmemberList.size() - 1);
        assertThat(testMmember.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingMmember() throws Exception {
        int databaseSizeBeforeUpdate = mmemberRepository.findAll().size();
        mmember.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMmemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mmember.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mmember))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mmember in the database
        List<Mmember> mmemberList = mmemberRepository.findAll();
        assertThat(mmemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMmember() throws Exception {
        int databaseSizeBeforeUpdate = mmemberRepository.findAll().size();
        mmember.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMmemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mmember))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mmember in the database
        List<Mmember> mmemberList = mmemberRepository.findAll();
        assertThat(mmemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMmember() throws Exception {
        int databaseSizeBeforeUpdate = mmemberRepository.findAll().size();
        mmember.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMmemberMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mmember))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mmember in the database
        List<Mmember> mmemberList = mmemberRepository.findAll();
        assertThat(mmemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMmember() throws Exception {
        // Initialize the database
        mmemberRepository.saveAndFlush(mmember);

        int databaseSizeBeforeDelete = mmemberRepository.findAll().size();

        // Delete the mmember
        restMmemberMockMvc
            .perform(delete(ENTITY_API_URL_ID, mmember.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mmember> mmemberList = mmemberRepository.findAll();
        assertThat(mmemberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
