package com.thegraid.gamma.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.thegraid.gamma.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GroupAuthorityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupAuthority.class);
        GroupAuthority groupAuthority1 = new GroupAuthority();
        groupAuthority1.setId(1L);
        GroupAuthority groupAuthority2 = new GroupAuthority();
        groupAuthority2.setId(groupAuthority1.getId());
        assertThat(groupAuthority1).isEqualTo(groupAuthority2);
        groupAuthority2.setId(2L);
        assertThat(groupAuthority1).isNotEqualTo(groupAuthority2);
        groupAuthority1.setId(null);
        assertThat(groupAuthority1).isNotEqualTo(groupAuthority2);
    }
}
