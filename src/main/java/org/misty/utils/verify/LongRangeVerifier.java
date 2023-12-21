package org.misty.utils.verify;

public class LongRangeVerifier extends LongRangeVerifierBase<IllegalArgumentException> {

    public LongRangeVerifier(String title, long min, long max) throws IllegalArgumentException {
        super(title, min, max, Verifier.INSTANCE);
    }

}
