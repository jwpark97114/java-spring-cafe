package com.codesquad.manager;

import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;

public class PagingManagerTest {


    private final int GROUP_SIZE = 5;

    @Test
    @DisplayName("Page 1 in 10 should show its group from 1 to 5")
    void testGroupingFromPage1(){
        PagingManager manager = new PagingManager(1, 10, GROUP_SIZE);

        assertThat(manager.getStartPage()).isEqualTo(1);
        assertThat(manager.getEndPage()).isEqualTo(5);
        assertThat(manager.isHasNextGroup()).isEqualTo(true);
        assertThat(manager.isHasPreviousGroup()).isEqualTo(false);
    }

    @Test
    @DisplayName("Page 6 from 10 should show its group from 6 to 10")
    void testGroupingFromPage6(){
        PagingManager manager = new PagingManager(6, 10, GROUP_SIZE);

        assertThat(manager.getStartPage()).isEqualTo(6);
        assertThat(manager.getEndPage()).isEqualTo(10);
        assertThat(manager.isHasPreviousGroup()).isEqualTo(true);
        assertThat(manager.isHasNextGroup()).isEqualTo(false);

    }

    @Test
    @DisplayName("Page 8 from 8 should show its group from 6 to 8")
    void testGroupingFromPage8(){
        PagingManager manager = new PagingManager(8, 8, GROUP_SIZE);

        assertThat(manager.getStartPage()).isEqualTo(6);
        assertThat(manager.getEndPage()).isEqualTo(8);
        assertThat(manager.isHasPreviousGroup()).isEqualTo(true);
        assertThat(manager.isHasNextGroup()).isEqualTo(false);

    }

}
