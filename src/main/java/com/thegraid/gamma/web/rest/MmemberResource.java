package com.thegraid.gamma.web.rest;

import com.thegraid.gamma.domain.Mmember;
import com.thegraid.gamma.repository.MmemberRepository;
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
 * REST controller for managing {@link com.thegraid.gamma.domain.Mmember}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MmemberResource {

    private final Logger log = LoggerFactory.getLogger(MmemberResource.class);

    private static final String ENTITY_NAME = "mmember";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MmemberRepository mmemberRepository;

    public MmemberResource(MmemberRepository mmemberRepository) {
        this.mmemberRepository = mmemberRepository;
    }

    /**
     * {@code POST  /mmembers} : Create a new mmember.
     *
     * @param mmember the mmember to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mmember, or with status {@code 400 (Bad Request)} if the mmember has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mmembers")
    public ResponseEntity<Mmember> createMmember(@RequestBody Mmember mmember) throws URISyntaxException {
        log.debug("REST request to save Mmember : {}", mmember);
        if (mmember.getId() != null) {
            throw new BadRequestAlertException("A new mmember cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mmember result = mmemberRepository.save(mmember);
        return ResponseEntity
            .created(new URI("/api/mmembers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mmembers/:id} : Updates an existing mmember.
     *
     * @param id the id of the mmember to save.
     * @param mmember the mmember to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mmember,
     * or with status {@code 400 (Bad Request)} if the mmember is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mmember couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mmembers/{id}")
    public ResponseEntity<Mmember> updateMmember(@PathVariable(value = "id", required = false) final Long id, @RequestBody Mmember mmember)
        throws URISyntaxException {
        log.debug("REST request to update Mmember : {}, {}", id, mmember);
        if (mmember.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mmember.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mmemberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Mmember result = mmemberRepository.save(mmember);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mmember.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mmembers/:id} : Partial updates given fields of an existing mmember, field will ignore if it is null
     *
     * @param id the id of the mmember to save.
     * @param mmember the mmember to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mmember,
     * or with status {@code 400 (Bad Request)} if the mmember is not valid,
     * or with status {@code 404 (Not Found)} if the mmember is not found,
     * or with status {@code 500 (Internal Server Error)} if the mmember couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mmembers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Mmember> partialUpdateMmember(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Mmember mmember
    ) throws URISyntaxException {
        log.debug("REST request to partial update Mmember partially : {}, {}", id, mmember);
        if (mmember.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mmember.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mmemberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Mmember> result = mmemberRepository
            .findById(mmember.getId())
            .map(existingMmember -> {
                if (mmember.getVersion() != null) {
                    existingMmember.setVersion(mmember.getVersion());
                }

                return existingMmember;
            })
            .map(mmemberRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mmember.getId().toString())
        );
    }

    /**
     * {@code GET  /mmembers} : get all the mmembers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mmembers in body.
     */
    @GetMapping("/mmembers")
    public List<Mmember> getAllMmembers() {
        log.debug("REST request to get all Mmembers");
        return mmemberRepository.findAll();
    }

    /**
     * {@code GET  /mmembers/:id} : get the "id" mmember.
     *
     * @param id the id of the mmember to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mmember, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mmembers/{id}")
    public ResponseEntity<Mmember> getMmember(@PathVariable Long id) {
        log.debug("REST request to get Mmember : {}", id);
        Optional<Mmember> mmember = mmemberRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mmember);
    }

    /**
     * {@code DELETE  /mmembers/:id} : delete the "id" mmember.
     *
     * @param id the id of the mmember to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mmembers/{id}")
    public ResponseEntity<Void> deleteMmember(@PathVariable Long id) {
        log.debug("REST request to delete Mmember : {}", id);
        mmemberRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
