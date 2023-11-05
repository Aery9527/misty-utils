package org.misty.utils.sys;

import org.junit.jupiter.api.Test;

class JvmInfoTest {

    @Test
    void getArguments() {
        JvmInfo.getArguments().forEach(System.out::println);
    }

}
