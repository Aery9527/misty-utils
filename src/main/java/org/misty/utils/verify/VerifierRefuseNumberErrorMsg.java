package org.misty.utils.verify;

public class VerifierRefuseNumberErrorMsg<TargetType extends Number> extends VerifierErrorMsg<TargetType> {

    private final String refuseTerm;

    private final TargetType refuseValue;

    public VerifierRefuseNumberErrorMsg(String targetTerm, TargetType target, String refuseTerm, TargetType refuseValue, String errorMsg) {
        super(targetTerm, target, errorMsg);
        this.refuseTerm = refuseTerm;
        this.refuseValue = refuseValue;
    }

    @Override
    public VerifierErrorMsg<TargetType> copy(String errorMsg) {
        return new VerifierRefuseNumberErrorMsg<>(getTargetTerm(), getTarget(), getRefuseTerm(), getRefuseValue(), errorMsg);
    }

    public String getRefuseTerm() {
        return refuseTerm;
    }

    public TargetType getRefuseValue() {
        return refuseValue;
    }

}
