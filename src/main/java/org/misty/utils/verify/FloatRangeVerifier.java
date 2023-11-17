package org.misty.utils.verify;

public class FloatRangeVerifier extends FloatRangeVerifierBase<IllegalArgumentException> {

    public FloatRangeVerifier(float min, float max) throws IllegalArgumentException {
        super(min, max, Verifier.INSTANCE);
    }

}
