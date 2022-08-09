package com.thegraid.gamma.web.rest;

import com.thegraid.gamma.domain.MmemberGameProps;
import com.thegraid.gamma.repository.MmemberGamePropsRepository;
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
 * REST controller for managing {@link com.thegraid.gamma.domain.MmemberGameProps}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MmemberGamePropsResource {

    private final Logger log = LoggerFactory.getLogger(MmemberGamePropsResource.class);

    private static final String ENTITY_NAME = "mmemberGameProps";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MmemberGamePropsRepository mmemberGamePropsRepository;

    public MmemberGamePropsResource(MmemberGamePropsRepository mmemberGamePropsRepository) {
        this.mmemberGamePropsRepository = mmemberGamePropsRepository;
    }

    /**
     * {@code POST  /mmember-game-props} : Create a new mmemberGameProps.
     *
     * @param mmemberGameProps the mmemberGameProps to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mmemberGameProps, or with status {@code 400 (Bad Request)} if the mmemberGameProps has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mmember-game-props")
    public ResponseEntity<MmemberGameProps> createMmemberGameProps(@RequestBody MmemberGameProps mmemberGameProps)
        throws URISyntaxException {
        log.debug("REST request to save MmemberGameProps : {}", mmemberGameProps);
        if (mmemberGameProps.getId() != null) {
            throw new BadRequestAlertException("A new mmemberGameProps cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MmemberGameProps result = mmemberGamePropsRepository.save(mmemberGameProps);
        return ResponseEntity
            .created(new URI("/api/mmember-game-props/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mmember-game-props/:id} : Updates an existing mmemberGameProps.
     *
     * @param id the id of the mmemberGameProps to save.
     * @param mmemberGameProps the mmemberGameProps to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mmemberGameProps,
     * or with status {@code 400 (Bad Request)} if the mmemberGameProps is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mmemberGameProps couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mmember-game-props/{id}")
    public ResponseEntity<MmemberGameProps> updateMmemberGameProps(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MmemberGameProps mmemberGameProps
    ) throws URISyntaxException {
        log.debug("REST request to update MmemberGameProps : {}, {}", id, mmemberGameProps);
        if (mmemberGameProps.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mmemberGameProps.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mmemberGamePropsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MmemberGameProps result = mmemberGamePropsRepository.save(mmemberGameProps);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mmemberGameProps.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mmember-game-props/:id} : Partial updates given fields of an existing mmemberGameProps, field will ignore if it is null
     *
     * @param id the id of the mmemberGameProps to save.
     * @param mmemberGameProps the mmemberGameProps to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mmemberGameProps,
     * or with status {@code 400 (Bad Request)} if the mmemberGameProps is not valid,
     * or with status {@code 404 (Not Found)} if the mmemberGameProps is not found,
     * or with status {@code 500 (Internal Server Error)} if the mmemberGameProps couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mmember-game-props/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MmemberGameProps> partialUpdateMmemberGameProps(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MmemberGameProps mmemberGameProps
    ) throws URISyntaxException {
        log.debug("REST request to partial update MmemberGameProps partially : {}, {}", id, mmemberGameProps);
        if (mmemberGameProps.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mmemberGameProps.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mmemberGamePropsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MmemberGameProps> result = mmemberGamePropsRepository
            .findById(mmemberGameProps.getId())
            .map(existingMmemberGameProps -> {
                if (mmemberGameProps.getVersion() != null) {
                    existingMmemberGameProps.setVersion(mmemberGameProps.getVersion());
                }
                if (mmemberGameProps.getSeed() != null) {
                    existingMmemberGameProps.setSeed(mmemberGameProps.getSeed());
                }
                if (mmemberGameProps.getMapName() != null) {
                    existingMmemberGameProps.setMapName(mmemberGameProps.getMapName());
                }
                if (mmemberGameProps.getMapSize() != null) {
                    existingMmemberGameProps.setMapSize(mmemberGameProps.getMapSize());
                }
                if (mmemberGameProps.getNpcCount() != null) {
                    existingMmemberGameProps.setNpcCount(mmemberGameProps.getNpcCount());
                }
                if (mmemberGameProps.getJsonProps() != null) {
                    existingMmemberGameProps.setJsonProps(mmemberGameProps.getJsonProps());
                }
                if (mmemberGameProps.getConfigName() != null) {
                    existingMmemberGameProps.setConfigName(mmemberGameProps.getConfigName());
                }

                return existingMmemberGameProps;
            })
            .map(mmemberGamePropsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mmemberGameProps.getId().toString())
        );
    }

    /**
     * {@code GET  /mmember-game-props} : get all the mmemberGameProps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mmemberGameProps in body.
     */
    @GetMapping("/mmember-game-props")
    public List<MmemberGameProps> getAllMmemberGameProps() {
        log.debug("REST request to get all MmemberGameProps");
        return mmemberGamePropsRepository.findAll();
    }

    /**
     * {@code GET  /mmember-game-props/:id} : get the "id" mmemberGameProps.
     *
     * @param id the id of the mmemberGameProps to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mmemberGameProps, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mmember-game-props/{id}")
    public ResponseEntity<MmemberGameProps> getMmemberGameProps(@PathVariable Long id) {
        log.debug("REST request to get MmemberGameProps : {}", id);
        Optional<MmemberGameProps> mmemberGameProps = mmemberGamePropsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mmemberGameProps);
    }

    /**
     * {@code DELETE  /mmember-game-props/:id} : delete the "id" mmemberGameProps.
     *
     * @param id the id of the mmemberGameProps to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mmember-game-props/{id}")
    public ResponseEntity<Void> deleteMmemberGameProps(@PathVariable Long id) {
        log.debug("REST request to delete MmemberGameProps : {}", id);
        mmemberGamePropsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
