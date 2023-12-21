package org.misty.utils.verify;

public class IntRangeVerifier extends IntRangeVerifierBase<IllegalArgumentException> {

    public IntRangeVerifier(String title, int min, int max) {
        super(title, min, max, Verifier.INSTANCE);
    }

}
