package com.techpick.backend;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TddTest {
    @Test
    @DisplayName("1 더하기 1은 2여야 한다")
    void additionTest() {
        int result = 1+1;
        assertThat(result).isEqualTo(2);
    }
}
