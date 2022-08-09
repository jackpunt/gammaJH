package com.thegraid.gamma.web.rest;

import com.thegraid.gamma.domain.GamePlayer;
import com.thegraid.gamma.repository.GamePlayerRepository;
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
 * REST controller for managing {@link com.thegraid.gamma.domain.GamePlayer}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GamePlayerResource {

    private final Logger log = LoggerFactory.getLogger(GamePlayerResource.class);

    private static final String ENTITY_NAME = "gamePlayer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GamePlayerRepository gamePlayerRepository;

    public GamePlayerResource(GamePlayerRepository gamePlayerRepository) {
        this.gamePlayerRepository = gamePlayerRepository;
    }

    /**
     * {@code POST  /game-players} : Create a new gamePlayer.
     *
     * @param gamePlayer the gamePlayer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gamePlayer, or with status {@code 400 (Bad Request)} if the gamePlayer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/game-players")
    public ResponseEntity<GamePlayer> createGamePlayer(@RequestBody GamePlayer gamePlayer) throws URISyntaxException {
        log.debug("REST request to save GamePlayer : {}", gamePlayer);
        if (gamePlayer.getId() != null) {
            throw new BadRequestAlertException("A new gamePlayer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GamePlayer result = gamePlayerRepository.save(gamePlayer);
        return ResponseEntity
            .created(new URI("/api/game-players/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /game-players/:id} : Updates an existing gamePlayer.
     *
     * @param id the id of the gamePlayer to save.
     * @param gamePlayer the gamePlayer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gamePlayer,
     * or with status {@code 400 (Bad Request)} if the gamePlayer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gamePlayer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/game-players/{id}")
    public ResponseEntity<GamePlayer> updateGamePlayer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GamePlayer gamePlayer
    ) throws URISyntaxException {
        log.debug("REST request to update GamePlayer : {}, {}", id, gamePlayer);
        if (gamePlayer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gamePlayer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gamePlayerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GamePlayer result = gamePlayerRepository.save(gamePlayer);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gamePlayer.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /game-players/:id} : Partial updates given fields of an existing gamePlayer, field will ignore if it is null
     *
     * @param id the id of the gamePlayer to save.
     * @param gamePlayer the gamePlayer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gamePlayer,
     * or with status {@code 400 (Bad Request)} if the gamePlayer is not valid,
     * or with status {@code 404 (Not Found)} if the gamePlayer is not found,
     * or with status {@code 500 (Internal Server Error)} if the gamePlayer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/game-players/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GamePlayer> partialUpdateGamePlayer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GamePlayer gamePlayer
    ) throws URISyntaxException {
        log.debug("REST request to partial update GamePlayer partially : {}, {}", id, gamePlayer);
        if (gamePlayer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gamePlayer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gamePlayerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GamePlayer> result = gamePlayerRepository
            .findById(gamePlayer.getId())
            .map(existingGamePlayer -> {
                if (gamePlayer.getVersion() != null) {
                    existingGamePlayer.setVersion(gamePlayer.getVersion());
                }
                if (gamePlayer.getRole() != null) {
                    existingGamePlayer.setRole(gamePlayer.getRole());
                }
                if (gamePlayer.getReady() != null) {
                    existingGamePlayer.setReady(gamePlayer.getReady());
                }

                return existingGamePlayer;
            })
            .map(gamePlayerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gamePlayer.getId().toString())
        );
    }

    /**
     * {@code GET  /game-players} : get all the gamePlayers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gamePlayers in body.
     */
    @GetMapping("/game-players")
    public List<GamePlayer> getAllGamePlayers() {
        log.debug("REST request to get all GamePlayers");
        return gamePlayerRepository.findAll();
    }

    /**
     * {@code GET  /game-players/:id} : get the "id" gamePlayer.
     *
     * @param id the id of the gamePlayer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gamePlayer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/game-players/{id}")
    public ResponseEntity<GamePlayer> getGamePlayer(@PathVariable Long id) {
        log.debug("REST request to get GamePlayer : {}", id);
        Optional<GamePlayer> gamePlayer = gamePlayerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gamePlayer);
    }

    /**
     * {@code DELETE  /game-players/:id} : delete the "id" gamePlayer.
     *
     * @param id the id of the gamePlayer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/game-players/{id}")
    public ResponseEntity<Void> deleteGamePlayer(@PathVariable Long id) {
        log.debug("REST request to delete GamePlayer : {}", id);
        gamePlayerRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
