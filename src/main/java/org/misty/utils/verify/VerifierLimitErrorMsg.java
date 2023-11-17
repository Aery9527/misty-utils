package org.misty.utils.verify;

public class VerifierLimitErrorMsg<TargetType> extends VerifierErrorMsg<TargetType> {

    private final String limitTerm;

    private final TargetType limit;

    public VerifierLimitErrorMsg(String targetTerm, TargetType target, String limitTerm, TargetType limit, String errorFormat) {
        super(targetTerm, target, String.format(errorFormat, targetTerm, target, limitTerm, limit));
        this.limitTerm = limitTerm;
        this.limit = limit;
    }

    public String getLimitTerm() {
        return limitTerm;
    }

    public TargetType getLimit() {
        return limit;
    }

}
