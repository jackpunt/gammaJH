package com.thegraid.gamma.repository;

import com.thegraid.gamma.domain.Mmember;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Mmember entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MmemberRepository extends JpaRepository<Mmember, Long> {}
