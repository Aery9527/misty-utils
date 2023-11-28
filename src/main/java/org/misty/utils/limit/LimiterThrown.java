package org.misty.utils.limit;

public interface LimiterThrown {

    void thrown(String errorMsg) throws RuntimeException;

}
