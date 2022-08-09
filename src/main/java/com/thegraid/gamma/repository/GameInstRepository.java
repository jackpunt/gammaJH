package com.thegraid.gamma.repository;

import com.thegraid.gamma.domain.GameInst;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GameInst entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameInstRepository extends JpaRepository<GameInst, Long> {}
