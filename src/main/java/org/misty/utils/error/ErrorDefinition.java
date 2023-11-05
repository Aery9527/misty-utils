package org.misty.utils.error;

public interface ErrorDefinition<ExceptionType extends Exception> {

    class Format {
        public static String TYPE_CODE = "%s-%s";
        public static String DESCRIPTION = "(" + TYPE_CODE + ") %s";
    }

    String getType();

    String getCode();

    default String getTypeCode() {
        return String.format(Format.TYPE_CODE, getType(), getCode());
    }

    default String getDescription() {
        return String.format(Format.DESCRIPTION, getType(), getCode(), this);
    }

    boolean isSame(Exception t);

    default boolean isSame(ErrorDefinition<?> errorDefinition) {
        return getClass().equals(errorDefinition.getClass()) &&
                getTypeCode().equals(errorDefinition.getTypeCode());
    }

    ExceptionType thrown() throws ExceptionType;

    ExceptionType thrown(String msg) throws ExceptionType;

    ExceptionType thrown(Throwable cause) throws ExceptionType;

    ExceptionType thrown(String msg, Throwable cause) throws ExceptionType;

}
