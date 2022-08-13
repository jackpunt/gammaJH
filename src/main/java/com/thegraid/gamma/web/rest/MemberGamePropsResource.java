package com.thegraid.gamma.web.rest;

import com.thegraid.gamma.domain.MemberGameProps;
import com.thegraid.gamma.repository.MemberGamePropsRepository;
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
 * REST controller for managing {@link com.thegraid.gamma.domain.MemberGameProps}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MemberGamePropsResource {

    private final Logger log = LoggerFactory.getLogger(MemberGamePropsResource.class);

    private static final String ENTITY_NAME = "memberGameProps";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MemberGamePropsRepository memberGamePropsRepository;

    public MemberGamePropsResource(MemberGamePropsRepository memberGamePropsRepository) {
        this.memberGamePropsRepository = memberGamePropsRepository;
    }

    /**
     * {@code POST  /member-game-props} : Create a new memberGameProps.
     *
     * @param memberGameProps the memberGameProps to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new memberGameProps, or with status {@code 400 (Bad Request)} if the memberGameProps has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/member-game-props")
    public ResponseEntity<MemberGameProps> createMemberGameProps(@RequestBody MemberGameProps memberGameProps) throws URISyntaxException {
        log.debug("REST request to save MemberGameProps : {}", memberGameProps);
        if (memberGameProps.getId() != null) {
            throw new BadRequestAlertException("A new memberGameProps cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MemberGameProps result = memberGamePropsRepository.save(memberGameProps);
        return ResponseEntity
            .created(new URI("/api/member-game-props/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /member-game-props/:id} : Updates an existing memberGameProps.
     *
     * @param id the id of the memberGameProps to save.
     * @param memberGameProps the memberGameProps to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated memberGameProps,
     * or with status {@code 400 (Bad Request)} if the memberGameProps is not valid,
     * or with status {@code 500 (Internal Server Error)} if the memberGameProps couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/member-game-props/{id}")
    public ResponseEntity<MemberGameProps> updateMemberGameProps(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MemberGameProps memberGameProps
    ) throws URISyntaxException {
        log.debug("REST request to update MemberGameProps : {}, {}", id, memberGameProps);
        if (memberGameProps.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, memberGameProps.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!memberGamePropsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MemberGameProps result = memberGamePropsRepository.save(memberGameProps);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, memberGameProps.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /member-game-props/:id} : Partial updates given fields of an existing memberGameProps, field will ignore if it is null
     *
     * @param id the id of the memberGameProps to save.
     * @param memberGameProps the memberGameProps to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated memberGameProps,
     * or with status {@code 400 (Bad Request)} if the memberGameProps is not valid,
     * or with status {@code 404 (Not Found)} if the memberGameProps is not found,
     * or with status {@code 500 (Internal Server Error)} if the memberGameProps couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/member-game-props/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MemberGameProps> partialUpdateMemberGameProps(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MemberGameProps memberGameProps
    ) throws URISyntaxException {
        log.debug("REST request to partial update MemberGameProps partially : {}, {}", id, memberGameProps);
        if (memberGameProps.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, memberGameProps.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!memberGamePropsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MemberGameProps> result = memberGamePropsRepository
            .findById(memberGameProps.getId())
            .map(existingMemberGameProps -> {
                if (memberGameProps.getVersion() != null) {
                    existingMemberGameProps.setVersion(memberGameProps.getVersion());
                }
                if (memberGameProps.getSeed() != null) {
                    existingMemberGameProps.setSeed(memberGameProps.getSeed());
                }
                if (memberGameProps.getMapName() != null) {
                    existingMemberGameProps.setMapName(memberGameProps.getMapName());
                }
                if (memberGameProps.getMapSize() != null) {
                    existingMemberGameProps.setMapSize(memberGameProps.getMapSize());
                }
                if (memberGameProps.getNpcCount() != null) {
                    existingMemberGameProps.setNpcCount(memberGameProps.getNpcCount());
                }
                if (memberGameProps.getJsonProps() != null) {
                    existingMemberGameProps.setJsonProps(memberGameProps.getJsonProps());
                }
                if (memberGameProps.getConfigName() != null) {
                    existingMemberGameProps.setConfigName(memberGameProps.getConfigName());
                }

                return existingMemberGameProps;
            })
            .map(memberGamePropsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, memberGameProps.getId().toString())
        );
    }

    /**
     * {@code GET  /member-game-props} : get all the memberGameProps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of memberGameProps in body.
     */
    @GetMapping("/member-game-props")
    public List<MemberGameProps> getAllMemberGameProps() {
        log.debug("REST request to get all MemberGameProps");
        return memberGamePropsRepository.findAll();
    }

    /**
     * {@code GET  /member-game-props/:id} : get the "id" memberGameProps.
     *
     * @param id the id of the memberGameProps to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the memberGameProps, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/member-game-props/{id}")
    public ResponseEntity<MemberGameProps> getMemberGameProps(@PathVariable Long id) {
        log.debug("REST request to get MemberGameProps : {}", id);
        Optional<MemberGameProps> memberGameProps = memberGamePropsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(memberGameProps);
    }

    /**
     * {@code DELETE  /member-game-props/:id} : delete the "id" memberGameProps.
     *
     * @param id the id of the memberGameProps to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/member-game-props/{id}")
    public ResponseEntity<Void> deleteMemberGameProps(@PathVariable Long id) {
        log.debug("REST request to delete MemberGameProps : {}", id);
        memberGamePropsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
