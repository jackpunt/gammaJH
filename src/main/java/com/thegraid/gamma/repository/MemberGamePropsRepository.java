package com.thegraid.gamma.repository;

import com.thegraid.gamma.domain.MemberGameProps;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MemberGameProps entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemberGamePropsRepository extends JpaRepository<MemberGameProps, Long> {
    @Query("select memberGameProps from MemberGameProps memberGameProps where memberGameProps.user.login = ?#{principal.username}")
    List<MemberGameProps> findByUserIsCurrentUser();
}
