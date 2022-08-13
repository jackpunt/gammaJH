package com.thegraid.gamma.web.rest;

import com.thegraid.gamma.domain.GameClass;
import com.thegraid.gamma.repository.GameClassRepository;
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
 * REST controller for managing {@link com.thegraid.gamma.domain.GameClass}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GameClassResource {

    private final Logger log = LoggerFactory.getLogger(GameClassResource.class);

    private static final String ENTITY_NAME = "gameClass";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GameClassRepository gameClassRepository;

    public GameClassResource(GameClassRepository gameClassRepository) {
        this.gameClassRepository = gameClassRepository;
    }

    /**
     * {@code POST  /game-classes} : Create a new gameClass.
     *
     * @param gameClass the gameClass to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gameClass, or with status {@code 400 (Bad Request)} if the gameClass has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/game-classes")
    public ResponseEntity<GameClass> createGameClass(@RequestBody GameClass gameClass) throws URISyntaxException {
        log.debug("REST request to save GameClass : {}", gameClass);
        if (gameClass.getId() != null) {
            throw new BadRequestAlertException("A new gameClass cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GameClass result = gameClassRepository.save(gameClass);
        return ResponseEntity
            .created(new URI("/api/game-classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /game-classes/:id} : Updates an existing gameClass.
     *
     * @param id the id of the gameClass to save.
     * @param gameClass the gameClass to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameClass,
     * or with status {@code 400 (Bad Request)} if the gameClass is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gameClass couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/game-classes/{id}")
    public ResponseEntity<GameClass> updateGameClass(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GameClass gameClass
    ) throws URISyntaxException {
        log.debug("REST request to update GameClass : {}, {}", id, gameClass);
        if (gameClass.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gameClass.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gameClassRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GameClass result = gameClassRepository.save(gameClass);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gameClass.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /game-classes/:id} : Partial updates given fields of an existing gameClass, field will ignore if it is null
     *
     * @param id the id of the gameClass to save.
     * @param gameClass the gameClass to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameClass,
     * or with status {@code 400 (Bad Request)} if the gameClass is not valid,
     * or with status {@code 404 (Not Found)} if the gameClass is not found,
     * or with status {@code 500 (Internal Server Error)} if the gameClass couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/game-classes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GameClass> partialUpdateGameClass(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GameClass gameClass
    ) throws URISyntaxException {
        log.debug("REST request to partial update GameClass partially : {}, {}", id, gameClass);
        if (gameClass.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gameClass.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gameClassRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GameClass> result = gameClassRepository
            .findById(gameClass.getId())
            .map(existingGameClass -> {
                if (gameClass.getVersion() != null) {
                    existingGameClass.setVersion(gameClass.getVersion());
                }
                if (gameClass.getName() != null) {
                    existingGameClass.setName(gameClass.getName());
                }
                if (gameClass.getRevision() != null) {
                    existingGameClass.setRevision(gameClass.getRevision());
                }
                if (gameClass.getLauncherPath() != null) {
                    existingGameClass.setLauncherPath(gameClass.getLauncherPath());
                }
                if (gameClass.getGamePath() != null) {
                    existingGameClass.setGamePath(gameClass.getGamePath());
                }
                if (gameClass.getDocsPath() != null) {
                    existingGameClass.setDocsPath(gameClass.getDocsPath());
                }
                if (gameClass.getPropsNames() != null) {
                    existingGameClass.setPropsNames(gameClass.getPropsNames());
                }
                if (gameClass.getUpdated() != null) {
                    existingGameClass.setUpdated(gameClass.getUpdated());
                }

                return existingGameClass;
            })
            .map(gameClassRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gameClass.getId().toString())
        );
    }

    /**
     * {@code GET  /game-classes} : get all the gameClasses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gameClasses in body.
     */
    @GetMapping("/game-classes")
    public List<GameClass> getAllGameClasses() {
        log.debug("REST request to get all GameClasses");
        return gameClassRepository.findAll();
    }

    /**
     * {@code GET  /game-classes/:id} : get the "id" gameClass.
     *
     * @param id the id of the gameClass to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gameClass, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/game-classes/{id}")
    public ResponseEntity<GameClass> getGameClass(@PathVariable Long id) {
        log.debug("REST request to get GameClass : {}", id);
        Optional<GameClass> gameClass = gameClassRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gameClass);
    }

    /**
     * {@code DELETE  /game-classes/:id} : delete the "id" gameClass.
     *
     * @param id the id of the gameClass to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/game-classes/{id}")
    public ResponseEntity<Void> deleteGameClass(@PathVariable Long id) {
        log.debug("REST request to delete GameClass : {}", id);
        gameClassRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
