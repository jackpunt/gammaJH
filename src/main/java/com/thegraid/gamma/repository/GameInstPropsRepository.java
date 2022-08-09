package com.thegraid.gamma.repository;

import com.thegraid.gamma.domain.GameInstProps;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GameInstProps entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameInstPropsRepository extends JpaRepository<GameInstProps, Long> {}
