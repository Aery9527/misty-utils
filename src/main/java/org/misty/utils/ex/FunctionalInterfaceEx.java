package org.misty.utils.ex;

/**
 * This series is designed to store interfaces that enhance the functionality of Functional Interfaces (FIs).
 * Since actions within FIs often throw exceptions, the compiler may prompt you to use try-catch.
 * However, in many cases, we prefer to propagate internal errors directly without adding another layer of try-catch.
 * Therefore, the FIs in this package are intended for directly throwing internal errors without the need for an additional try-catch layer.
 */
@SuppressWarnings("unchecked")
public interface FunctionalInterfaceEx {

    static <ExceptionType extends Exception> void wrap(RunnableEx<ExceptionType> runnableEx) throws ExceptionType {
        try {
            runnableEx.handle();
        } catch (Exception t) {
            throw (ExceptionType) t;
        }
    }

    static <ReturnType, ExceptionType extends Exception> ReturnType wrap(SupplierEx<ReturnType, ExceptionType> supplierEx)
            throws ExceptionType {
        try {
            return supplierEx.handle();
        } catch (Exception t) {
            throw (ExceptionType) t;
        }
    }

}
