package com.thegraid.gamma.web.rest;

import com.thegraid.gamma.domain.GroupAuthority;
import com.thegraid.gamma.repository.GroupAuthorityRepository;
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
 * REST controller for managing {@link com.thegraid.gamma.domain.GroupAuthority}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GroupAuthorityResource {

    private final Logger log = LoggerFactory.getLogger(GroupAuthorityResource.class);

    private static final String ENTITY_NAME = "groupAuthority";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupAuthorityRepository groupAuthorityRepository;

    public GroupAuthorityResource(GroupAuthorityRepository groupAuthorityRepository) {
        this.groupAuthorityRepository = groupAuthorityRepository;
    }

    /**
     * {@code POST  /group-authorities} : Create a new groupAuthority.
     *
     * @param groupAuthority the groupAuthority to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupAuthority, or with status {@code 400 (Bad Request)} if the groupAuthority has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/group-authorities")
    public ResponseEntity<GroupAuthority> createGroupAuthority(@RequestBody GroupAuthority groupAuthority) throws URISyntaxException {
        log.debug("REST request to save GroupAuthority : {}", groupAuthority);
        if (groupAuthority.getId() != null) {
            throw new BadRequestAlertException("A new groupAuthority cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupAuthority result = groupAuthorityRepository.save(groupAuthority);
        return ResponseEntity
            .created(new URI("/api/group-authorities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /group-authorities/:id} : Updates an existing groupAuthority.
     *
     * @param id the id of the groupAuthority to save.
     * @param groupAuthority the groupAuthority to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupAuthority,
     * or with status {@code 400 (Bad Request)} if the groupAuthority is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupAuthority couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/group-authorities/{id}")
    public ResponseEntity<GroupAuthority> updateGroupAuthority(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GroupAuthority groupAuthority
    ) throws URISyntaxException {
        log.debug("REST request to update GroupAuthority : {}, {}", id, groupAuthority);
        if (groupAuthority.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, groupAuthority.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!groupAuthorityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GroupAuthority result = groupAuthorityRepository.save(groupAuthority);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groupAuthority.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /group-authorities/:id} : Partial updates given fields of an existing groupAuthority, field will ignore if it is null
     *
     * @param id the id of the groupAuthority to save.
     * @param groupAuthority the groupAuthority to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupAuthority,
     * or with status {@code 400 (Bad Request)} if the groupAuthority is not valid,
     * or with status {@code 404 (Not Found)} if the groupAuthority is not found,
     * or with status {@code 500 (Internal Server Error)} if the groupAuthority couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/group-authorities/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GroupAuthority> partialUpdateGroupAuthority(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GroupAuthority groupAuthority
    ) throws URISyntaxException {
        log.debug("REST request to partial update GroupAuthority partially : {}, {}", id, groupAuthority);
        if (groupAuthority.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, groupAuthority.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!groupAuthorityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GroupAuthority> result = groupAuthorityRepository
            .findById(groupAuthority.getId())
            .map(existingGroupAuthority -> {
                if (groupAuthority.getVersion() != null) {
                    existingGroupAuthority.setVersion(groupAuthority.getVersion());
                }
                if (groupAuthority.getAuthority() != null) {
                    existingGroupAuthority.setAuthority(groupAuthority.getAuthority());
                }

                return existingGroupAuthority;
            })
            .map(groupAuthorityRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groupAuthority.getId().toString())
        );
    }

    /**
     * {@code GET  /group-authorities} : get all the groupAuthorities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groupAuthorities in body.
     */
    @GetMapping("/group-authorities")
    public List<GroupAuthority> getAllGroupAuthorities() {
        log.debug("REST request to get all GroupAuthorities");
        return groupAuthorityRepository.findAll();
    }

    /**
     * {@code GET  /group-authorities/:id} : get the "id" groupAuthority.
     *
     * @param id the id of the groupAuthority to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupAuthority, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/group-authorities/{id}")
    public ResponseEntity<GroupAuthority> getGroupAuthority(@PathVariable Long id) {
        log.debug("REST request to get GroupAuthority : {}", id);
        Optional<GroupAuthority> groupAuthority = groupAuthorityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(groupAuthority);
    }

    /**
     * {@code DELETE  /group-authorities/:id} : delete the "id" groupAuthority.
     *
     * @param id the id of the groupAuthority to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/group-authorities/{id}")
    public ResponseEntity<Void> deleteGroupAuthority(@PathVariable Long id) {
        log.debug("REST request to delete GroupAuthority : {}", id);
        groupAuthorityRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
