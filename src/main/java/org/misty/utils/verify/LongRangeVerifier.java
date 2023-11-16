package org.misty.utils.verify;

public class LongRangeVerifier extends Verifier {

    private final long min;

    private final long max;

    public LongRangeVerifier(long min, long max) {
        Verifier.requireLongLessThanInclusive("min", min, "max", max);
        this.min = min;
        this.max = max;
    }

    public void requireInclusive(String term, long target) throws IllegalArgumentException {
        requireInclusive(term, target, Verifier.INSTANCE.defaultThrown());
    }

    public <ExceptionType extends Exception> void requireInclusive(
            String term,
            long target,
            VerifierThrown<Long, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < this.min || target > this.max) {
            thrown.thrown(term, target, String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE, term, target, this.min, this.max));
        }
    }

    public void requireExclusive(String term, long target) throws IllegalArgumentException {
        requireExclusive(term, target, Verifier.INSTANCE.defaultThrown());
    }

    public <ExceptionType extends Exception> void requireExclusive(
            String term,
            long target,
            VerifierThrown<Long, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= this.min || target >= this.max) {
            thrown.thrown(term, target, String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE, term, target, this.min, this.max));
        }
    }

    public void refuseInclusive(String term, long target) throws IllegalArgumentException {
        refuseInclusive(term, target, Verifier.INSTANCE.defaultThrown());
    }

    public <ExceptionType extends Exception> void refuseInclusive(
            String term,
            long target,
            VerifierThrown<Long, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= this.min && target <= this.max) {
            thrown.thrown(term, target, String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE, term, target, this.min, this.max));
        }
    }

    public void refuseExclusive(String term, long target) throws IllegalArgumentException {
        refuseExclusive(term, target, Verifier.INSTANCE.defaultThrown());
    }

    public <ExceptionType extends Exception> void refuseExclusive(
            String term,
            long target,
            VerifierThrown<Long, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > this.min && target < this.max) {
            thrown.thrown(term, target, String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE, term, target, this.min, this.max));
        }
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }

}
