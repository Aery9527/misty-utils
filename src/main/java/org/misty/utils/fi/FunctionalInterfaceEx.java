package org.misty.utils.fi;

/**
 * @see org.misty.utils.fi
 */
@SuppressWarnings("unchecked")
public interface FunctionalInterfaceEx {

    static <ExceptionType extends Exception> void wrap(RunnableEx runnableEx) throws ExceptionType {
        try {
            runnableEx.handle();
        } catch (Exception t) {
            throw (ExceptionType) t;
        }
    }

    static <ReturnType, ExceptionType extends Exception> ReturnType wrap(SupplierEx<ReturnType> supplierEx) throws ExceptionType {
        try {
            return supplierEx.handle();
        } catch (Exception t) {
            throw (ExceptionType) t;
        }
    }

    static <ExceptionType extends Exception> boolean wrap(BooleanSupplierEx booleanSupplierEx) throws ExceptionType {
        try {
            return booleanSupplierEx.handle();
        } catch (Exception t) {
            throw (ExceptionType) t;
        }
    }

    static <ExceptionType extends Exception> double wrap(DoubleSupplierEx doubleSupplierEx) throws ExceptionType {
        try {
            return doubleSupplierEx.handle();
        } catch (Exception t) {
            throw (ExceptionType) t;
        }
    }

    static <ExceptionType extends Exception> int wrap(IntSupplierEx intSupplierEx) throws ExceptionType {
        try {
            return intSupplierEx.handle();
        } catch (Exception t) {
            throw (ExceptionType) t;
        }
    }

    static <ExceptionType extends Exception> long wrap(LongSupplierEx longSupplierEx) throws ExceptionType {
        try {
            return longSupplierEx.handle();
        } catch (Exception t) {
            throw (ExceptionType) t;
        }
    }

}
