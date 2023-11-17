package org.misty.utils.verify;

public class VerifierRangeErrorMsg<TargetType> extends VerifierErrorMsg<TargetType> {

    private final TargetType min;

    private final TargetType max;

    public VerifierRangeErrorMsg(String targetTerm, TargetType target, TargetType min, TargetType max, String errorFormat) {
        super(targetTerm, target, String.format(errorFormat, targetTerm, target, min, max));
        this.min = min;
        this.max = max;
    }

    public TargetType getMin() {
        return min;
    }

    public TargetType getMax() {
        return max;
    }

}
