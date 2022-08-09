package com.thegraid.gamma.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.thegraid.gamma.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MmemberGamePropsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MmemberGameProps.class);
        MmemberGameProps mmemberGameProps1 = new MmemberGameProps();
        mmemberGameProps1.setId(1L);
        MmemberGameProps mmemberGameProps2 = new MmemberGameProps();
        mmemberGameProps2.setId(mmemberGameProps1.getId());
        assertThat(mmemberGameProps1).isEqualTo(mmemberGameProps2);
        mmemberGameProps2.setId(2L);
        assertThat(mmemberGameProps1).isNotEqualTo(mmemberGameProps2);
        mmemberGameProps1.setId(null);
        assertThat(mmemberGameProps1).isNotEqualTo(mmemberGameProps2);
    }
}
