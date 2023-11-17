package org.misty.utils.verify;

public class IntRangeVerifier extends IntRangeVerifierBase<IllegalArgumentException> {

    public IntRangeVerifier(int min, int max) {
        super(min, max, Verifier.INSTANCE);
    }

}
