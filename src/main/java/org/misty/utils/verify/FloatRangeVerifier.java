package org.misty.utils.verify;

public class FloatRangeVerifier extends FloatRangeVerifierBase<IllegalArgumentException> {

    public FloatRangeVerifier(String title, float min, float max) throws IllegalArgumentException {
        super(title, min, max, Verifier.INSTANCE);
    }

}
