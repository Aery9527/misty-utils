package org.misty.utils.fi;

public interface FloatBinaryOperatorEx extends FunctionalInterfaceEx {

    float handle(float arg1, float arg2) throws Exception;

    default float execute(float arg1, float arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
