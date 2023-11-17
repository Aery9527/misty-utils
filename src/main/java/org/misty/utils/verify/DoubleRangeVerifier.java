package org.misty.utils.verify;

public class DoubleRangeVerifier extends DoubleRangeVerifierBase<IllegalArgumentException> {

    public DoubleRangeVerifier(double min, double max) throws IllegalArgumentException {
        super(min, max, Verifier.INSTANCE);
    }

}
