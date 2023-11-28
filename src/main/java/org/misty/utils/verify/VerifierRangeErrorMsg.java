package org.misty.utils.verify;

public class VerifierRangeErrorMsg<TargetType extends Number> extends VerifierErrorMsg<TargetType> {

    private final TargetType min;

    private final TargetType max;

    public VerifierRangeErrorMsg(String targetTerm, TargetType target, TargetType min, TargetType max, String errorMsg) {
        super(targetTerm, target, errorMsg);
        this.min = min;
        this.max = max;
    }

    @Override
    public VerifierRangeErrorMsg<TargetType> copy(String errorMsg) {
        return new VerifierRangeErrorMsg<>(getTargetTerm(), getTarget(), getMin(), getMax(), errorMsg);
    }

    public TargetType getMin() {
        return min;
    }

    public TargetType getMax() {
        return max;
    }

}
