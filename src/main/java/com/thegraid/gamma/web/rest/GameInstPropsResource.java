package com.thegraid.gamma.web.rest;

import com.thegraid.gamma.domain.GameInstProps;
import com.thegraid.gamma.repository.GameInstPropsRepository;
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
 * REST controller for managing {@link com.thegraid.gamma.domain.GameInstProps}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GameInstPropsResource {

    private final Logger log = LoggerFactory.getLogger(GameInstPropsResource.class);

    private static final String ENTITY_NAME = "gameInstProps";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GameInstPropsRepository gameInstPropsRepository;

    public GameInstPropsResource(GameInstPropsRepository gameInstPropsRepository) {
        this.gameInstPropsRepository = gameInstPropsRepository;
    }

    /**
     * {@code POST  /game-inst-props} : Create a new gameInstProps.
     *
     * @param gameInstProps the gameInstProps to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gameInstProps, or with status {@code 400 (Bad Request)} if the gameInstProps has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/game-inst-props")
    public ResponseEntity<GameInstProps> createGameInstProps(@RequestBody GameInstProps gameInstProps) throws URISyntaxException {
        log.debug("REST request to save GameInstProps : {}", gameInstProps);
        if (gameInstProps.getId() != null) {
            throw new BadRequestAlertException("A new gameInstProps cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GameInstProps result = gameInstPropsRepository.save(gameInstProps);
        return ResponseEntity
            .created(new URI("/api/game-inst-props/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /game-inst-props/:id} : Updates an existing gameInstProps.
     *
     * @param id the id of the gameInstProps to save.
     * @param gameInstProps the gameInstProps to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameInstProps,
     * or with status {@code 400 (Bad Request)} if the gameInstProps is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gameInstProps couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/game-inst-props/{id}")
    public ResponseEntity<GameInstProps> updateGameInstProps(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GameInstProps gameInstProps
    ) throws URISyntaxException {
        log.debug("REST request to update GameInstProps : {}, {}", id, gameInstProps);
        if (gameInstProps.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gameInstProps.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gameInstPropsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GameInstProps result = gameInstPropsRepository.save(gameInstProps);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gameInstProps.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /game-inst-props/:id} : Partial updates given fields of an existing gameInstProps, field will ignore if it is null
     *
     * @param id the id of the gameInstProps to save.
     * @param gameInstProps the gameInstProps to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameInstProps,
     * or with status {@code 400 (Bad Request)} if the gameInstProps is not valid,
     * or with status {@code 404 (Not Found)} if the gameInstProps is not found,
     * or with status {@code 500 (Internal Server Error)} if the gameInstProps couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/game-inst-props/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GameInstProps> partialUpdateGameInstProps(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GameInstProps gameInstProps
    ) throws URISyntaxException {
        log.debug("REST request to partial update GameInstProps partially : {}, {}", id, gameInstProps);
        if (gameInstProps.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gameInstProps.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gameInstPropsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GameInstProps> result = gameInstPropsRepository
            .findById(gameInstProps.getId())
            .map(existingGameInstProps -> {
                if (gameInstProps.getVersion() != null) {
                    existingGameInstProps.setVersion(gameInstProps.getVersion());
                }
                if (gameInstProps.getSeed() != null) {
                    existingGameInstProps.setSeed(gameInstProps.getSeed());
                }
                if (gameInstProps.getMapName() != null) {
                    existingGameInstProps.setMapName(gameInstProps.getMapName());
                }
                if (gameInstProps.getMapSize() != null) {
                    existingGameInstProps.setMapSize(gameInstProps.getMapSize());
                }
                if (gameInstProps.getNpcCount() != null) {
                    existingGameInstProps.setNpcCount(gameInstProps.getNpcCount());
                }
                if (gameInstProps.getJsonProps() != null) {
                    existingGameInstProps.setJsonProps(gameInstProps.getJsonProps());
                }
                if (gameInstProps.getUpdated() != null) {
                    existingGameInstProps.setUpdated(gameInstProps.getUpdated());
                }

                return existingGameInstProps;
            })
            .map(gameInstPropsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gameInstProps.getId().toString())
        );
    }

    /**
     * {@code GET  /game-inst-props} : get all the gameInstProps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gameInstProps in body.
     */
    @GetMapping("/game-inst-props")
    public List<GameInstProps> getAllGameInstProps() {
        log.debug("REST request to get all GameInstProps");
        return gameInstPropsRepository.findAll();
    }

    /**
     * {@code GET  /game-inst-props/:id} : get the "id" gameInstProps.
     *
     * @param id the id of the gameInstProps to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gameInstProps, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/game-inst-props/{id}")
    public ResponseEntity<GameInstProps> getGameInstProps(@PathVariable Long id) {
        log.debug("REST request to get GameInstProps : {}", id);
        Optional<GameInstProps> gameInstProps = gameInstPropsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gameInstProps);
    }

    /**
     * {@code DELETE  /game-inst-props/:id} : delete the "id" gameInstProps.
     *
     * @param id the id of the gameInstProps to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/game-inst-props/{id}")
    public ResponseEntity<Void> deleteGameInstProps(@PathVariable Long id) {
        log.debug("REST request to delete GameInstProps : {}", id);
        gameInstPropsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
