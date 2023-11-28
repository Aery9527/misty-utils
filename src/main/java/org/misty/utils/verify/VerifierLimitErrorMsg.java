package org.misty.utils.verify;

public class VerifierLimitErrorMsg<TargetType extends Number> extends VerifierErrorMsg<TargetType> {

    private final String limitTerm;

    private final TargetType limit;

    public VerifierLimitErrorMsg(String targetTerm, TargetType target, String limitTerm, TargetType limit, String errorMsg) {
        super(targetTerm, target, errorMsg);
        this.limitTerm = limitTerm;
        this.limit = limit;
    }

    @Override
    public VerifierErrorMsg<TargetType> copy(String errorMsg) {
        return new VerifierLimitErrorMsg<>(getTargetTerm(), getTarget(), getLimitTerm(), getLimit(), errorMsg);
    }

    public String getLimitTerm() {
        return limitTerm;
    }

    public TargetType getLimit() {
        return limit;
    }

}
