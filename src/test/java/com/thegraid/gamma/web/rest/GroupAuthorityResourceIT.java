package com.thegraid.gamma.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.thegraid.gamma.IntegrationTest;
import com.thegraid.gamma.domain.GroupAuthority;
import com.thegraid.gamma.repository.GroupAuthorityRepository;
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
 * Integration tests for the {@link GroupAuthorityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GroupAuthorityResourceIT {

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_AUTHORITY = "AAAAAAAAAA";
    private static final String UPDATED_AUTHORITY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/group-authorities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GroupAuthorityRepository groupAuthorityRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroupAuthorityMockMvc;

    private GroupAuthority groupAuthority;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupAuthority createEntity(EntityManager em) {
        GroupAuthority groupAuthority = new GroupAuthority().version(DEFAULT_VERSION).authority(DEFAULT_AUTHORITY);
        return groupAuthority;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupAuthority createUpdatedEntity(EntityManager em) {
        GroupAuthority groupAuthority = new GroupAuthority().version(UPDATED_VERSION).authority(UPDATED_AUTHORITY);
        return groupAuthority;
    }

    @BeforeEach
    public void initTest() {
        groupAuthority = createEntity(em);
    }

    @Test
    @Transactional
    void createGroupAuthority() throws Exception {
        int databaseSizeBeforeCreate = groupAuthorityRepository.findAll().size();
        // Create the GroupAuthority
        restGroupAuthorityMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groupAuthority))
            )
            .andExpect(status().isCreated());

        // Validate the GroupAuthority in the database
        List<GroupAuthority> groupAuthorityList = groupAuthorityRepository.findAll();
        assertThat(groupAuthorityList).hasSize(databaseSizeBeforeCreate + 1);
        GroupAuthority testGroupAuthority = groupAuthorityList.get(groupAuthorityList.size() - 1);
        assertThat(testGroupAuthority.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testGroupAuthority.getAuthority()).isEqualTo(DEFAULT_AUTHORITY);
    }

    @Test
    @Transactional
    void createGroupAuthorityWithExistingId() throws Exception {
        // Create the GroupAuthority with an existing ID
        groupAuthority.setId(1L);

        int databaseSizeBeforeCreate = groupAuthorityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupAuthorityMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groupAuthority))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroupAuthority in the database
        List<GroupAuthority> groupAuthorityList = groupAuthorityRepository.findAll();
        assertThat(groupAuthorityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGroupAuthorities() throws Exception {
        // Initialize the database
        groupAuthorityRepository.saveAndFlush(groupAuthority);

        // Get all the groupAuthorityList
        restGroupAuthorityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupAuthority.getId().intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].authority").value(hasItem(DEFAULT_AUTHORITY)));
    }

    @Test
    @Transactional
    void getGroupAuthority() throws Exception {
        // Initialize the database
        groupAuthorityRepository.saveAndFlush(groupAuthority);

        // Get the groupAuthority
        restGroupAuthorityMockMvc
            .perform(get(ENTITY_API_URL_ID, groupAuthority.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groupAuthority.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.authority").value(DEFAULT_AUTHORITY));
    }

    @Test
    @Transactional
    void getNonExistingGroupAuthority() throws Exception {
        // Get the groupAuthority
        restGroupAuthorityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGroupAuthority() throws Exception {
        // Initialize the database
        groupAuthorityRepository.saveAndFlush(groupAuthority);

        int databaseSizeBeforeUpdate = groupAuthorityRepository.findAll().size();

        // Update the groupAuthority
        GroupAuthority updatedGroupAuthority = groupAuthorityRepository.findById(groupAuthority.getId()).get();
        // Disconnect from session so that the updates on updatedGroupAuthority are not directly saved in db
        em.detach(updatedGroupAuthority);
        updatedGroupAuthority.version(UPDATED_VERSION).authority(UPDATED_AUTHORITY);

        restGroupAuthorityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGroupAuthority.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGroupAuthority))
            )
            .andExpect(status().isOk());

        // Validate the GroupAuthority in the database
        List<GroupAuthority> groupAuthorityList = groupAuthorityRepository.findAll();
        assertThat(groupAuthorityList).hasSize(databaseSizeBeforeUpdate);
        GroupAuthority testGroupAuthority = groupAuthorityList.get(groupAuthorityList.size() - 1);
        assertThat(testGroupAuthority.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testGroupAuthority.getAuthority()).isEqualTo(UPDATED_AUTHORITY);
    }

    @Test
    @Transactional
    void putNonExistingGroupAuthority() throws Exception {
        int databaseSizeBeforeUpdate = groupAuthorityRepository.findAll().size();
        groupAuthority.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupAuthorityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, groupAuthority.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groupAuthority))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroupAuthority in the database
        List<GroupAuthority> groupAuthorityList = groupAuthorityRepository.findAll();
        assertThat(groupAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGroupAuthority() throws Exception {
        int databaseSizeBeforeUpdate = groupAuthorityRepository.findAll().size();
        groupAuthority.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupAuthorityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groupAuthority))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroupAuthority in the database
        List<GroupAuthority> groupAuthorityList = groupAuthorityRepository.findAll();
        assertThat(groupAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGroupAuthority() throws Exception {
        int databaseSizeBeforeUpdate = groupAuthorityRepository.findAll().size();
        groupAuthority.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupAuthorityMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groupAuthority))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GroupAuthority in the database
        List<GroupAuthority> groupAuthorityList = groupAuthorityRepository.findAll();
        assertThat(groupAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGroupAuthorityWithPatch() throws Exception {
        // Initialize the database
        groupAuthorityRepository.saveAndFlush(groupAuthority);

        int databaseSizeBeforeUpdate = groupAuthorityRepository.findAll().size();

        // Update the groupAuthority using partial update
        GroupAuthority partialUpdatedGroupAuthority = new GroupAuthority();
        partialUpdatedGroupAuthority.setId(groupAuthority.getId());

        partialUpdatedGroupAuthority.authority(UPDATED_AUTHORITY);

        restGroupAuthorityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGroupAuthority.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGroupAuthority))
            )
            .andExpect(status().isOk());

        // Validate the GroupAuthority in the database
        List<GroupAuthority> groupAuthorityList = groupAuthorityRepository.findAll();
        assertThat(groupAuthorityList).hasSize(databaseSizeBeforeUpdate);
        GroupAuthority testGroupAuthority = groupAuthorityList.get(groupAuthorityList.size() - 1);
        assertThat(testGroupAuthority.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testGroupAuthority.getAuthority()).isEqualTo(UPDATED_AUTHORITY);
    }

    @Test
    @Transactional
    void fullUpdateGroupAuthorityWithPatch() throws Exception {
        // Initialize the database
        groupAuthorityRepository.saveAndFlush(groupAuthority);

        int databaseSizeBeforeUpdate = groupAuthorityRepository.findAll().size();

        // Update the groupAuthority using partial update
        GroupAuthority partialUpdatedGroupAuthority = new GroupAuthority();
        partialUpdatedGroupAuthority.setId(groupAuthority.getId());

        partialUpdatedGroupAuthority.version(UPDATED_VERSION).authority(UPDATED_AUTHORITY);

        restGroupAuthorityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGroupAuthority.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGroupAuthority))
            )
            .andExpect(status().isOk());

        // Validate the GroupAuthority in the database
        List<GroupAuthority> groupAuthorityList = groupAuthorityRepository.findAll();
        assertThat(groupAuthorityList).hasSize(databaseSizeBeforeUpdate);
        GroupAuthority testGroupAuthority = groupAuthorityList.get(groupAuthorityList.size() - 1);
        assertThat(testGroupAuthority.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testGroupAuthority.getAuthority()).isEqualTo(UPDATED_AUTHORITY);
    }

    @Test
    @Transactional
    void patchNonExistingGroupAuthority() throws Exception {
        int databaseSizeBeforeUpdate = groupAuthorityRepository.findAll().size();
        groupAuthority.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupAuthorityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, groupAuthority.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(groupAuthority))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroupAuthority in the database
        List<GroupAuthority> groupAuthorityList = groupAuthorityRepository.findAll();
        assertThat(groupAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGroupAuthority() throws Exception {
        int databaseSizeBeforeUpdate = groupAuthorityRepository.findAll().size();
        groupAuthority.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupAuthorityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(groupAuthority))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroupAuthority in the database
        List<GroupAuthority> groupAuthorityList = groupAuthorityRepository.findAll();
        assertThat(groupAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGroupAuthority() throws Exception {
        int databaseSizeBeforeUpdate = groupAuthorityRepository.findAll().size();
        groupAuthority.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupAuthorityMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(groupAuthority))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GroupAuthority in the database
        List<GroupAuthority> groupAuthorityList = groupAuthorityRepository.findAll();
        assertThat(groupAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGroupAuthority() throws Exception {
        // Initialize the database
        groupAuthorityRepository.saveAndFlush(groupAuthority);

        int databaseSizeBeforeDelete = groupAuthorityRepository.findAll().size();

        // Delete the groupAuthority
        restGroupAuthorityMockMvc
            .perform(delete(ENTITY_API_URL_ID, groupAuthority.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GroupAuthority> groupAuthorityList = groupAuthorityRepository.findAll();
        assertThat(groupAuthorityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
