package org.misty.utils.verify;

public class IntRangeVerifier extends Verifier {

    private final int min;

    private final int max;

    public IntRangeVerifier(int min, int max) {
        Verifier.requireIntLessThanInclusive("min", min, max);
        this.min = min;
        this.max = max;
    }

    public void requireInclusive(String term, int target) throws IllegalArgumentException {
        requireInclusive(term, target, Verifier.INSTANCE.defaultThrown());
    }

    public <ExceptionType extends Exception> void requireInclusive(
            String term,
            int target,
            VerifierThrown<Integer, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < this.min || target > this.max) {
            thrown.thrown(term, target, String.format("\"%s(%d)\" must be in range [%d, %d]", term, target, this.min, this.max));
        }
    }

    public void requireExclusive(String term, int target) throws IllegalArgumentException {
        requireExclusive(term, target, Verifier.INSTANCE.defaultThrown());
    }

    public <ExceptionType extends Exception> void requireExclusive(
            String term,
            int target,
            VerifierThrown<Integer, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= this.min || target >= this.max) {
            thrown.thrown(term, target, String.format("\"%s(%d)\" must be in range (%d, %d)", term, target, this.min, this.max));
        }
    }

    public void refuseInclusive(String term, int target) throws IllegalArgumentException {
        refuseInclusive(term, target, Verifier.INSTANCE.defaultThrown());
    }

    public <ExceptionType extends Exception> void refuseInclusive(
            String term,
            int target,
            VerifierThrown<Integer, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= this.min && target <= this.max) {
            thrown.thrown(term, target, String.format("\"%s(%d)\" can't be in range [%d, %d]", term, target, this.min, this.max));
        }
    }

    public void refuseExclusive(String term, int target) throws IllegalArgumentException {
        refuseExclusive(term, target, Verifier.INSTANCE.defaultThrown());
    }

    public <ExceptionType extends Exception> void refuseExclusive(
            String term,
            int target,
            VerifierThrown<Integer, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > this.min && target < this.max) {
            thrown.thrown(term, target, String.format("\"%s(%d)\" can't be in range (%d, %d)", term, target, this.min, this.max));
        }
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

}
