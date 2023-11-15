package org.misty.utils.verify;

public class ShortRangeVerifier extends Verifier {

    private final short min;

    private final short max;

    public ShortRangeVerifier(short min, short max) {
        Verifier.requireShortLessThanInclusive("min", min, max);
        this.min = min;
        this.max = max;
    }

    public void requireInclusive(String term, short target) throws IllegalArgumentException {
        requireInclusive(term, target, Verifier.INSTANCE.defaultThrown());
    }

    public <ExceptionType extends Exception> void requireInclusive(
            String term,
            short target,
            VerifierThrown<Short, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < this.min || target > this.max) {
            thrown.thrown(term, target, String.format("\"%s(%d)\" must be in range [%d, %d]", term, target, this.min, this.max));
        }
    }

    public void requireExclusive(String term, short target) throws IllegalArgumentException {
        requireExclusive(term, target, Verifier.INSTANCE.defaultThrown());
    }

    public <ExceptionType extends Exception> void requireExclusive(
            String term,
            short target,
            VerifierThrown<Short, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= this.min || target >= this.max) {
            thrown.thrown(term, target, String.format("\"%s(%d)\" must be in range (%d, %d)", term, target, this.min, this.max));
        }
    }

    public void refuseInclusive(String term, short target) throws IllegalArgumentException {
        refuseInclusive(term, target, Verifier.INSTANCE.defaultThrown());
    }

    public <ExceptionType extends Exception> void refuseInclusive(
            String term,
            short target,
            VerifierThrown<Short, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= this.min && target <= this.max) {
            thrown.thrown(term, target, String.format("\"%s(%d)\" can't be in range [%d, %d]", term, target, this.min, this.max));
        }
    }

    public void refuseExclusive(String term, short target) throws IllegalArgumentException {
        refuseExclusive(term, target, Verifier.INSTANCE.defaultThrown());
    }

    public <ExceptionType extends Exception> void refuseExclusive(
            String term,
            short target,
            VerifierThrown<Short, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > this.min && target < this.max) {
            thrown.thrown(term, target, String.format("\"%s(%d)\" can't be in range (%d, %d)", term, target, this.min, this.max));
        }
    }

    public short getMin() {
        return min;
    }

    public short getMax() {
        return max;
    }

}
