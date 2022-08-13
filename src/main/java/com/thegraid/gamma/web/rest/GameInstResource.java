package com.thegraid.gamma.web.rest;

import com.thegraid.gamma.domain.GameInst;
import com.thegraid.gamma.repository.GameInstPropsRepository;
import com.thegraid.gamma.repository.GameInstRepository;
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
 * REST controller for managing {@link com.thegraid.gamma.domain.GameInst}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GameInstResource {

    private final Logger log = LoggerFactory.getLogger(GameInstResource.class);

    private static final String ENTITY_NAME = "gameInst";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GameInstRepository gameInstRepository;

    private final GameInstPropsRepository gameInstPropsRepository;

    public GameInstResource(GameInstRepository gameInstRepository, GameInstPropsRepository gameInstPropsRepository) {
        this.gameInstRepository = gameInstRepository;
        this.gameInstPropsRepository = gameInstPropsRepository;
    }

    /**
     * {@code POST  /game-insts} : Create a new gameInst.
     *
     * @param gameInst the gameInst to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gameInst, or with status {@code 400 (Bad Request)} if the gameInst has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/game-insts")
    public ResponseEntity<GameInst> createGameInst(@RequestBody GameInst gameInst) throws URISyntaxException {
        log.debug("REST request to save GameInst : {}", gameInst);
        if (gameInst.getId() != null) {
            throw new BadRequestAlertException("A new gameInst cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(gameInst.getProps())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        Long gameInstPropsId = gameInst.getProps().getId();
        gameInstPropsRepository.findById(gameInstPropsId).ifPresent(gameInst::gameInstProps);
        GameInst result = gameInstRepository.save(gameInst);
        return ResponseEntity
            .created(new URI("/api/game-insts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /game-insts/:id} : Updates an existing gameInst.
     *
     * @param id the id of the gameInst to save.
     * @param gameInst the gameInst to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameInst,
     * or with status {@code 400 (Bad Request)} if the gameInst is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gameInst couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/game-insts/{id}")
    public ResponseEntity<GameInst> updateGameInst(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GameInst gameInst
    ) throws URISyntaxException {
        log.debug("REST request to update GameInst : {}, {}", id, gameInst);
        if (gameInst.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gameInst.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gameInstRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GameInst result = gameInstRepository.save(gameInst);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gameInst.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /game-insts/:id} : Partial updates given fields of an existing gameInst, field will ignore if it is null
     *
     * @param id the id of the gameInst to save.
     * @param gameInst the gameInst to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameInst,
     * or with status {@code 400 (Bad Request)} if the gameInst is not valid,
     * or with status {@code 404 (Not Found)} if the gameInst is not found,
     * or with status {@code 500 (Internal Server Error)} if the gameInst couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/game-insts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GameInst> partialUpdateGameInst(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GameInst gameInst
    ) throws URISyntaxException {
        log.debug("REST request to partial update GameInst partially : {}, {}", id, gameInst);
        if (gameInst.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gameInst.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gameInstRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GameInst> result = gameInstRepository
            .findById(gameInst.getId())
            .map(existingGameInst -> {
                if (gameInst.getVersion() != null) {
                    existingGameInst.setVersion(gameInst.getVersion());
                }
                if (gameInst.getGameName() != null) {
                    existingGameInst.setGameName(gameInst.getGameName());
                }
                if (gameInst.getHostUrl() != null) {
                    existingGameInst.setHostUrl(gameInst.getHostUrl());
                }
                if (gameInst.getPasscode() != null) {
                    existingGameInst.setPasscode(gameInst.getPasscode());
                }
                if (gameInst.getCreated() != null) {
                    existingGameInst.setCreated(gameInst.getCreated());
                }
                if (gameInst.getStarted() != null) {
                    existingGameInst.setStarted(gameInst.getStarted());
                }
                if (gameInst.getFinished() != null) {
                    existingGameInst.setFinished(gameInst.getFinished());
                }
                if (gameInst.getUpdated() != null) {
                    existingGameInst.setUpdated(gameInst.getUpdated());
                }
                if (gameInst.getScoreA() != null) {
                    existingGameInst.setScoreA(gameInst.getScoreA());
                }
                if (gameInst.getScoreB() != null) {
                    existingGameInst.setScoreB(gameInst.getScoreB());
                }
                if (gameInst.getTicks() != null) {
                    existingGameInst.setTicks(gameInst.getTicks());
                }

                return existingGameInst;
            })
            .map(gameInstRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gameInst.getId().toString())
        );
    }

    /**
     * {@code GET  /game-insts} : get all the gameInsts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gameInsts in body.
     */
    @GetMapping("/game-insts")
    @Transactional(readOnly = true)
    public List<GameInst> getAllGameInsts() {
        log.debug("REST request to get all GameInsts");
        return gameInstRepository.findAll();
    }

    /**
     * {@code GET  /game-insts/:id} : get the "id" gameInst.
     *
     * @param id the id of the gameInst to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gameInst, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/game-insts/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<GameInst> getGameInst(@PathVariable Long id) {
        log.debug("REST request to get GameInst : {}", id);
        Optional<GameInst> gameInst = gameInstRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gameInst);
    }

    /**
     * {@code DELETE  /game-insts/:id} : delete the "id" gameInst.
     *
     * @param id the id of the gameInst to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/game-insts/{id}")
    public ResponseEntity<Void> deleteGameInst(@PathVariable Long id) {
        log.debug("REST request to delete GameInst : {}", id);
        gameInstRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
