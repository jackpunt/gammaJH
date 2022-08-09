package com.thegraid.gamma.web.rest;

import com.thegraid.gamma.domain.RoleGroup;
import com.thegraid.gamma.repository.RoleGroupRepository;
import com.thegraid.gamma.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.thegraid.gamma.domain.RoleGroup}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RoleGroupResource {

    private final Logger log = LoggerFactory.getLogger(RoleGroupResource.class);

    private static final String ENTITY_NAME = "roleGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoleGroupRepository roleGroupRepository;

    public RoleGroupResource(RoleGroupRepository roleGroupRepository) {
        this.roleGroupRepository = roleGroupRepository;
    }

    /**
     * {@code POST  /role-groups} : Create a new roleGroup.
     *
     * @param roleGroup the roleGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roleGroup, or with status {@code 400 (Bad Request)} if the roleGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/role-groups")
    public ResponseEntity<RoleGroup> createRoleGroup(@RequestBody RoleGroup roleGroup) throws URISyntaxException {
        log.debug("REST request to save RoleGroup : {}", roleGroup);
        if (roleGroup.getId() != null) {
            throw new BadRequestAlertException("A new roleGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoleGroup result = roleGroupRepository.save(roleGroup);
        return ResponseEntity
            .created(new URI("/api/role-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /role-groups/:id} : Updates an existing roleGroup.
     *
     * @param id the id of the roleGroup to save.
     * @param roleGroup the roleGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleGroup,
     * or with status {@code 400 (Bad Request)} if the roleGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roleGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/role-groups/{id}")
    public ResponseEntity<RoleGroup> updateRoleGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RoleGroup roleGroup
    ) throws URISyntaxException {
        log.debug("REST request to update RoleGroup : {}, {}", id, roleGroup);
        if (roleGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roleGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roleGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RoleGroup result = roleGroupRepository.save(roleGroup);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roleGroup.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /role-groups/:id} : Partial updates given fields of an existing roleGroup, field will ignore if it is null
     *
     * @param id the id of the roleGroup to save.
     * @param roleGroup the roleGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleGroup,
     * or with status {@code 400 (Bad Request)} if the roleGroup is not valid,
     * or with status {@code 404 (Not Found)} if the roleGroup is not found,
     * or with status {@code 500 (Internal Server Error)} if the roleGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/role-groups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RoleGroup> partialUpdateRoleGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RoleGroup roleGroup
    ) throws URISyntaxException {
        log.debug("REST request to partial update RoleGroup partially : {}, {}", id, roleGroup);
        if (roleGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roleGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roleGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RoleGroup> result = roleGroupRepository
            .findById(roleGroup.getId())
            .map(existingRoleGroup -> {
                if (roleGroup.getVersion() != null) {
                    existingRoleGroup.setVersion(roleGroup.getVersion());
                }
                if (roleGroup.getGroupName() != null) {
                    existingRoleGroup.setGroupName(roleGroup.getGroupName());
                }

                return existingRoleGroup;
            })
            .map(roleGroupRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roleGroup.getId().toString())
        );
    }

    /**
     * {@code GET  /role-groups} : get all the roleGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roleGroups in body.
     */
    @GetMapping("/role-groups")
    public List<RoleGroup> getAllRoleGroups() {
        log.debug("REST request to get all RoleGroups");
        return roleGroupRepository.findAll();
    }

    /**
     * {@code GET  /role-groups/:id} : get the "id" roleGroup.
     *
     * @param id the id of the roleGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roleGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/role-groups/{id}")
    public ResponseEntity<RoleGroup> getRoleGroup(@PathVariable Long id) {
        log.debug("REST request to get RoleGroup : {}", id);
        Optional<RoleGroup> roleGroup = roleGroupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(roleGroup);
    }

    /**
     * {@code DELETE  /role-groups/:id} : delete the "id" roleGroup.
     *
     * @param id the id of the roleGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/role-groups/{id}")
    public ResponseEntity<Void> deleteRoleGroup(@PathVariable Long id) {
        log.debug("REST request to delete RoleGroup : {}", id);
        roleGroupRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
