package com.thegraid.gamma.repository;

import com.thegraid.gamma.domain.RoleGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RoleGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleGroupRepository extends JpaRepository<RoleGroup, Long> {}
