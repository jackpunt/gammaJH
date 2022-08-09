package com.thegraid.gamma.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.thegraid.gamma.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MmemberTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mmember.class);
        Mmember mmember1 = new Mmember();
        mmember1.setId(1L);
        Mmember mmember2 = new Mmember();
        mmember2.setId(mmember1.getId());
        assertThat(mmember1).isEqualTo(mmember2);
        mmember2.setId(2L);
        assertThat(mmember1).isNotEqualTo(mmember2);
        mmember1.setId(null);
        assertThat(mmember1).isNotEqualTo(mmember2);
    }
}
