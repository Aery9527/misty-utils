package org.misty.utils;

import org.junit.jupiter.api.Test;
import org.misty.utils.ex.ThreadEx;

public class IdentityGeneratorTest {

    @Test
    public void genWithTimestamp() {
        for (int i = 0; i < 5; i++) {
            System.out.println(IdentityGenerator.genWithTimestamp());
            ThreadEx.restRandom(10, 20);
        }
    }

    @Test
    public void genWithTimestamp_suffixAppendNumber() {
        for (int i = 0; i < 5; i++) {
            System.out.println(IdentityGenerator.genWithTimestamp(3));
            ThreadEx.restRandom(10, 20);
        }
    }

    @Test
    public void genWithTimestamp_suffixCutNumber_suffixAppendNumber() {
        for (int i = 0; i < 5; i++) {
            System.out.println(IdentityGenerator.genWithTimestamp(6,2));
            ThreadEx.restRandom(10, 20);
        }
    }

}
