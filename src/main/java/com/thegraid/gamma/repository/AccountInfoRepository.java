package com.thegraid.gamma.repository;

import com.thegraid.gamma.domain.AccountInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AccountInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountInfoRepository extends JpaRepository<AccountInfo, Long> {}
