package org.misty.utils.verify;

import java.math.BigDecimal;

public class BigDecimalRangeVerifier extends BigDecimalRangeVerifierBase<IllegalArgumentException> {

    public BigDecimalRangeVerifier(BigDecimal min, BigDecimal max) throws IllegalArgumentException {
        super(min, max, Verifier.INSTANCE);
    }

}
