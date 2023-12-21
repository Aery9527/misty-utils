package org.misty.utils.verify;

import java.math.BigDecimal;

public class BigDecimalRangeVerifier extends BigDecimalRangeVerifierBase<IllegalArgumentException> {

    public BigDecimalRangeVerifier(String title, BigDecimal min, BigDecimal max) throws IllegalArgumentException {
        super(title, min, max, Verifier.INSTANCE);
    }

}
