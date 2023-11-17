package org.misty.utils.verify;

public class LongRangeVerifier extends LongRangeVerifierBase<IllegalArgumentException> {

    public LongRangeVerifier(long min, long max) throws IllegalArgumentException {
        super(min, max, Verifier.INSTANCE);
    }

}
