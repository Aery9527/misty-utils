package org.misty.utils.verify;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class Checker {

    @SuppressWarnings("rawtypes")
    public static boolean isNullOrEmpty(Object o) {
        if (o == null) {
            return true;

        } else if (o instanceof String) {
            return isNullOrEmpty((String) o);

        } else if (o instanceof Collection) {
            return isNullOrEmpty((Collection) o);

        } else if (o instanceof Map) {
            return isNullOrEmpty((Map) o);

        } else if (o.getClass().isArray()) {
            if (o instanceof short[]) {
                return isNullOrEmpty((short[]) o);
            } else if (o instanceof int[]) {
                return isNullOrEmpty((int[]) o);
            } else if (o instanceof long[]) {
                return isNullOrEmpty((long[]) o);
            } else if (o instanceof float[]) {
                return isNullOrEmpty((float[]) o);
            } else if (o instanceof double[]) {
                return isNullOrEmpty((double[]) o);
            } else if (o instanceof boolean[]) {
                return isNullOrEmpty((boolean[]) o);
            } else if (o instanceof char[]) {
                return isNullOrEmpty((char[]) o);
            } else if (o instanceof byte[]) {
                return isNullOrEmpty((byte[]) o);
            } else {
                return isNullOrEmpty((Object[]) o);
            }

        } else if (o instanceof Optional) {
            return isNullOrEmpty((Optional) o);

        } else {
            return false;
        }
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static boolean isNullOrEmpty(Collection<?> c) {
        return c == null || c.isEmpty();
    }

    public static boolean isNullOrEmpty(Map<?, ?> m) {
        return m == null || m.isEmpty();
    }

    public static boolean isNullOrEmpty(short[] a) {
        return a == null || a.length == 0;
    }

    public static boolean isNullOrEmpty(int[] a) {
        return a == null || a.length == 0;
    }

    public static boolean isNullOrEmpty(long[] a) {
        return a == null || a.length == 0;
    }

    public static boolean isNullOrEmpty(float[] a) {
        return a == null || a.length == 0;
    }

    public static boolean isNullOrEmpty(double[] a) {
        return a == null || a.length == 0;
    }

    public static boolean isNullOrEmpty(boolean[] a) {
        return a == null || a.length == 0;
    }

    public static boolean isNullOrEmpty(char[] a) {
        return a == null || a.length == 0;
    }

    public static boolean isNullOrEmpty(byte[] a) {
        return a == null || a.length == 0;
    }

    public static boolean isNullOrEmpty(Object[] a) {
        return a == null || a.length == 0;
    }

    @SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "OptionalAssignedToNull"})
    public static boolean isNullOrEmpty(Optional<?> o) {
        if (o == null) {
            return true;
        }

        if (o.isEmpty()) {
            return true;
        }

        Object v = o.get();
        return isNullOrEmpty(v);
    }

    public static boolean notNullAndEmpty(Object o) {
        return !isNullOrEmpty(o);
    }

    public static boolean notNullAndEmpty(String s) {
        return !isNullOrEmpty(s);
    }

    public static boolean notNullAndEmpty(Collection<?> c) {
        return !isNullOrEmpty(c);
    }

    public static boolean notNullAndEmpty(Map<?, ?> m) {
        return !isNullOrEmpty(m);
    }

    public static boolean notNullAndEmpty(short[] a) {
        return !isNullOrEmpty(a);
    }

    public static boolean notNullAndEmpty(int[] a) {
        return !isNullOrEmpty(a);
    }

    public static boolean notNullAndEmpty(long[] a) {
        return !isNullOrEmpty(a);
    }

    public static boolean notNullAndEmpty(float[] a) {
        return !isNullOrEmpty(a);
    }

    public static boolean notNullAndEmpty(double[] a) {
        return !isNullOrEmpty(a);
    }

    public static boolean notNullAndEmpty(boolean[] a) {
        return !isNullOrEmpty(a);
    }

    public static boolean notNullAndEmpty(char[] a) {
        return !isNullOrEmpty(a);
    }

    public static boolean notNullAndEmpty(byte[] a) {
        return !isNullOrEmpty(a);
    }

    public static boolean notNullAndEmpty(Object[] a) {
        return !isNullOrEmpty(a);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static boolean notNullAndEmpty(Optional<?> o) {
        return !isNullOrEmpty(o);
    }

}
