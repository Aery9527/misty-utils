package org.misty.utils.verify;

public class ShortRangeVerifier extends ShortRangeVerifierBase<IllegalArgumentException> {

    public ShortRangeVerifier(short min, short max) throws IllegalArgumentException {
        super(min, max, Verifier.INSTANCE);
    }

}
