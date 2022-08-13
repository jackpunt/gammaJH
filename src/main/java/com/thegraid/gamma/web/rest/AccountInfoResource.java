package com.thegraid.gamma.web.rest;

import com.thegraid.gamma.domain.AccountInfo;
import com.thegraid.gamma.repository.AccountInfoRepository;
import com.thegraid.gamma.repository.UserRepository;
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
 * REST controller for managing {@link com.thegraid.gamma.domain.AccountInfo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AccountInfoResource {

    private final Logger log = LoggerFactory.getLogger(AccountInfoResource.class);

    private static final String ENTITY_NAME = "accountInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountInfoRepository accountInfoRepository;

    private final UserRepository userRepository;

    public AccountInfoResource(AccountInfoRepository accountInfoRepository, UserRepository userRepository) {
        this.accountInfoRepository = accountInfoRepository;
        this.userRepository = userRepository;
    }

    /**
     * {@code POST  /account-infos} : Create a new accountInfo.
     *
     * @param accountInfo the accountInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountInfo, or with status {@code 400 (Bad Request)} if the accountInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/account-infos")
    public ResponseEntity<AccountInfo> createAccountInfo(@RequestBody AccountInfo accountInfo) throws URISyntaxException {
        log.debug("REST request to save AccountInfo : {}", accountInfo);
        if (accountInfo.getId() != null) {
            throw new BadRequestAlertException("A new accountInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(accountInfo.getUser())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        Long userId = accountInfo.getUser().getId();
        userRepository.findById(userId).ifPresent(accountInfo::user);
        AccountInfo result = accountInfoRepository.save(accountInfo);
        return ResponseEntity
            .created(new URI("/api/account-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /account-infos/:id} : Updates an existing accountInfo.
     *
     * @param id the id of the accountInfo to save.
     * @param accountInfo the accountInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountInfo,
     * or with status {@code 400 (Bad Request)} if the accountInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/account-infos/{id}")
    public ResponseEntity<AccountInfo> updateAccountInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AccountInfo accountInfo
    ) throws URISyntaxException {
        log.debug("REST request to update AccountInfo : {}, {}", id, accountInfo);
        if (accountInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accountInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accountInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AccountInfo result = accountInfoRepository.save(accountInfo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /account-infos/:id} : Partial updates given fields of an existing accountInfo, field will ignore if it is null
     *
     * @param id the id of the accountInfo to save.
     * @param accountInfo the accountInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountInfo,
     * or with status {@code 400 (Bad Request)} if the accountInfo is not valid,
     * or with status {@code 404 (Not Found)} if the accountInfo is not found,
     * or with status {@code 500 (Internal Server Error)} if the accountInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/account-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AccountInfo> partialUpdateAccountInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AccountInfo accountInfo
    ) throws URISyntaxException {
        log.debug("REST request to partial update AccountInfo partially : {}, {}", id, accountInfo);
        if (accountInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accountInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accountInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AccountInfo> result = accountInfoRepository
            .findById(accountInfo.getId())
            .map(existingAccountInfo -> {
                if (accountInfo.getType() != null) {
                    existingAccountInfo.setType(accountInfo.getType());
                }

                return existingAccountInfo;
            })
            .map(accountInfoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountInfo.getId().toString())
        );
    }

    /**
     * {@code GET  /account-infos} : get all the accountInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountInfos in body.
     */
    @GetMapping("/account-infos")
    @Transactional(readOnly = true)
    public List<AccountInfo> getAllAccountInfos() {
        log.debug("REST request to get all AccountInfos");
        return accountInfoRepository.findAll();
    }

    /**
     * {@code GET  /account-infos/:id} : get the "id" accountInfo.
     *
     * @param id the id of the accountInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/account-infos/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<AccountInfo> getAccountInfo(@PathVariable Long id) {
        log.debug("REST request to get AccountInfo : {}", id);
        Optional<AccountInfo> accountInfo = accountInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(accountInfo);
    }

    /**
     * {@code DELETE  /account-infos/:id} : delete the "id" accountInfo.
     *
     * @param id the id of the accountInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/account-infos/{id}")
    public ResponseEntity<Void> deleteAccountInfo(@PathVariable Long id) {
        log.debug("REST request to delete AccountInfo : {}", id);
        accountInfoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
