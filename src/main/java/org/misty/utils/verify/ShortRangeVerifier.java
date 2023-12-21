package org.misty.utils.verify;

public class ShortRangeVerifier extends ShortRangeVerifierBase<IllegalArgumentException> {

    public ShortRangeVerifier(String title, short min, short max) throws IllegalArgumentException {
        super(title, min, max, Verifier.INSTANCE);
    }

}
