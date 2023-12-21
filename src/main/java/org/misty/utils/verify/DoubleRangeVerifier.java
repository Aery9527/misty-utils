package org.misty.utils.verify;

public class DoubleRangeVerifier extends DoubleRangeVerifierBase<IllegalArgumentException> {

    public DoubleRangeVerifier(String title, double min, double max) throws IllegalArgumentException {
        super(title, min, max, Verifier.INSTANCE);
    }

}
