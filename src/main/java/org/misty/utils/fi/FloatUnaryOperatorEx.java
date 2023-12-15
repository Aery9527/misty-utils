package org.misty.utils.fi;

public interface FloatUnaryOperatorEx extends FunctionalInterfaceEx {

    float handle(float arg) throws Exception;

    default float execute(float arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
