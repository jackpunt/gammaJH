package com.thegraid.gamma.repository;

import com.thegraid.gamma.domain.MmemberGameProps;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MmemberGameProps entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MmemberGamePropsRepository extends JpaRepository<MmemberGameProps, Long> {}
