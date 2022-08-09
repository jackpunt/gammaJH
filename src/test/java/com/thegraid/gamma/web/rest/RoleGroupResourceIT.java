package com.thegraid.gamma.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.thegraid.gamma.IntegrationTest;
import com.thegraid.gamma.domain.RoleGroup;
import com.thegraid.gamma.repository.RoleGroupRepository;
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
 * Integration tests for the {@link RoleGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RoleGroupResourceIT {

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/role-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RoleGroupRepository roleGroupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoleGroupMockMvc;

    private RoleGroup roleGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoleGroup createEntity(EntityManager em) {
        RoleGroup roleGroup = new RoleGroup().version(DEFAULT_VERSION).groupName(DEFAULT_GROUP_NAME);
        return roleGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoleGroup createUpdatedEntity(EntityManager em) {
        RoleGroup roleGroup = new RoleGroup().version(UPDATED_VERSION).groupName(UPDATED_GROUP_NAME);
        return roleGroup;
    }

    @BeforeEach
    public void initTest() {
        roleGroup = createEntity(em);
    }

    @Test
    @Transactional
    void createRoleGroup() throws Exception {
        int databaseSizeBeforeCreate = roleGroupRepository.findAll().size();
        // Create the RoleGroup
        restRoleGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleGroup))
            )
            .andExpect(status().isCreated());

        // Validate the RoleGroup in the database
        List<RoleGroup> roleGroupList = roleGroupRepository.findAll();
        assertThat(roleGroupList).hasSize(databaseSizeBeforeCreate + 1);
        RoleGroup testRoleGroup = roleGroupList.get(roleGroupList.size() - 1);
        assertThat(testRoleGroup.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testRoleGroup.getGroupName()).isEqualTo(DEFAULT_GROUP_NAME);
    }

    @Test
    @Transactional
    void createRoleGroupWithExistingId() throws Exception {
        // Create the RoleGroup with an existing ID
        roleGroup.setId(1L);

        int databaseSizeBeforeCreate = roleGroupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoleGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleGroup in the database
        List<RoleGroup> roleGroupList = roleGroupRepository.findAll();
        assertThat(roleGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRoleGroups() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get all the roleGroupList
        restRoleGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roleGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].groupName").value(hasItem(DEFAULT_GROUP_NAME)));
    }

    @Test
    @Transactional
    void getRoleGroup() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        // Get the roleGroup
        restRoleGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, roleGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(roleGroup.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.groupName").value(DEFAULT_GROUP_NAME));
    }

    @Test
    @Transactional
    void getNonExistingRoleGroup() throws Exception {
        // Get the roleGroup
        restRoleGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRoleGroup() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        int databaseSizeBeforeUpdate = roleGroupRepository.findAll().size();

        // Update the roleGroup
        RoleGroup updatedRoleGroup = roleGroupRepository.findById(roleGroup.getId()).get();
        // Disconnect from session so that the updates on updatedRoleGroup are not directly saved in db
        em.detach(updatedRoleGroup);
        updatedRoleGroup.version(UPDATED_VERSION).groupName(UPDATED_GROUP_NAME);

        restRoleGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRoleGroup.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRoleGroup))
            )
            .andExpect(status().isOk());

        // Validate the RoleGroup in the database
        List<RoleGroup> roleGroupList = roleGroupRepository.findAll();
        assertThat(roleGroupList).hasSize(databaseSizeBeforeUpdate);
        RoleGroup testRoleGroup = roleGroupList.get(roleGroupList.size() - 1);
        assertThat(testRoleGroup.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testRoleGroup.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    void putNonExistingRoleGroup() throws Exception {
        int databaseSizeBeforeUpdate = roleGroupRepository.findAll().size();
        roleGroup.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roleGroup.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleGroup in the database
        List<RoleGroup> roleGroupList = roleGroupRepository.findAll();
        assertThat(roleGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoleGroup() throws Exception {
        int databaseSizeBeforeUpdate = roleGroupRepository.findAll().size();
        roleGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleGroup in the database
        List<RoleGroup> roleGroupList = roleGroupRepository.findAll();
        assertThat(roleGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoleGroup() throws Exception {
        int databaseSizeBeforeUpdate = roleGroupRepository.findAll().size();
        roleGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleGroupMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleGroup))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoleGroup in the database
        List<RoleGroup> roleGroupList = roleGroupRepository.findAll();
        assertThat(roleGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoleGroupWithPatch() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        int databaseSizeBeforeUpdate = roleGroupRepository.findAll().size();

        // Update the roleGroup using partial update
        RoleGroup partialUpdatedRoleGroup = new RoleGroup();
        partialUpdatedRoleGroup.setId(roleGroup.getId());

        partialUpdatedRoleGroup.version(UPDATED_VERSION).groupName(UPDATED_GROUP_NAME);

        restRoleGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoleGroup.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoleGroup))
            )
            .andExpect(status().isOk());

        // Validate the RoleGroup in the database
        List<RoleGroup> roleGroupList = roleGroupRepository.findAll();
        assertThat(roleGroupList).hasSize(databaseSizeBeforeUpdate);
        RoleGroup testRoleGroup = roleGroupList.get(roleGroupList.size() - 1);
        assertThat(testRoleGroup.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testRoleGroup.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    void fullUpdateRoleGroupWithPatch() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        int databaseSizeBeforeUpdate = roleGroupRepository.findAll().size();

        // Update the roleGroup using partial update
        RoleGroup partialUpdatedRoleGroup = new RoleGroup();
        partialUpdatedRoleGroup.setId(roleGroup.getId());

        partialUpdatedRoleGroup.version(UPDATED_VERSION).groupName(UPDATED_GROUP_NAME);

        restRoleGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoleGroup.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoleGroup))
            )
            .andExpect(status().isOk());

        // Validate the RoleGroup in the database
        List<RoleGroup> roleGroupList = roleGroupRepository.findAll();
        assertThat(roleGroupList).hasSize(databaseSizeBeforeUpdate);
        RoleGroup testRoleGroup = roleGroupList.get(roleGroupList.size() - 1);
        assertThat(testRoleGroup.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testRoleGroup.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingRoleGroup() throws Exception {
        int databaseSizeBeforeUpdate = roleGroupRepository.findAll().size();
        roleGroup.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, roleGroup.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roleGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleGroup in the database
        List<RoleGroup> roleGroupList = roleGroupRepository.findAll();
        assertThat(roleGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoleGroup() throws Exception {
        int databaseSizeBeforeUpdate = roleGroupRepository.findAll().size();
        roleGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roleGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleGroup in the database
        List<RoleGroup> roleGroupList = roleGroupRepository.findAll();
        assertThat(roleGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoleGroup() throws Exception {
        int databaseSizeBeforeUpdate = roleGroupRepository.findAll().size();
        roleGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleGroupMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roleGroup))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoleGroup in the database
        List<RoleGroup> roleGroupList = roleGroupRepository.findAll();
        assertThat(roleGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoleGroup() throws Exception {
        // Initialize the database
        roleGroupRepository.saveAndFlush(roleGroup);

        int databaseSizeBeforeDelete = roleGroupRepository.findAll().size();

        // Delete the roleGroup
        restRoleGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, roleGroup.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RoleGroup> roleGroupList = roleGroupRepository.findAll();
        assertThat(roleGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
