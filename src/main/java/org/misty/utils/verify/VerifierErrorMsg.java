package org.misty.utils.verify;

public class VerifierErrorMsg<TargetType> {

    private final String targetTerm;

    private final TargetType target;

    private final String errorMsg;

    public VerifierErrorMsg(String targetTerm, TargetType target, String errorMsg) {
        this.targetTerm = targetTerm;
        this.target = target;
        this.errorMsg = errorMsg;
    }

    public String getTargetTerm() {
        return targetTerm;
    }

    public TargetType getTarget() {
        return target;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
