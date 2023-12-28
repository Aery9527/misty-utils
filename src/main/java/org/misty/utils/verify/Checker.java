package org.misty.utils.verify;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

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

    public static void isNullOrEmpty(Object o, Runnable action) {
        if (isNullOrEmpty(o)) {
            action.run();
        }
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static void isNullOrEmpty(String s, Runnable action) {
        if (isNullOrEmpty(s)) {
            action.run();
        }
    }

    public static boolean isNullOrEmpty(Collection<?> c) {
        return c == null || c.isEmpty();
    }

    public static void isNullOrEmpty(Collection<?> c, Runnable action) {
        if (isNullOrEmpty(c)) {
            action.run();
        }
    }

    public static boolean isNullOrEmpty(Map<?, ?> m) {
        return m == null || m.isEmpty();
    }

    public static void isNullOrEmpty(Map<?, ?> m, Runnable action) {
        if (isNullOrEmpty(m)) {
            action.run();
        }
    }

    public static boolean isNullOrEmpty(short[] a) {
        return a == null || a.length == 0;
    }

    public static void isNullOrEmpty(short[] a, Runnable action) {
        if (isNullOrEmpty(a)) {
            action.run();
        }
    }

    public static boolean isNullOrEmpty(int[] a) {
        return a == null || a.length == 0;
    }

    public static void isNullOrEmpty(int[] a, Runnable action) {
        if (isNullOrEmpty(a)) {
            action.run();
        }
    }

    public static boolean isNullOrEmpty(long[] a) {
        return a == null || a.length == 0;
    }

    public static void isNullOrEmpty(long[] a, Runnable action) {
        if (isNullOrEmpty(a)) {
            action.run();
        }
    }

    public static boolean isNullOrEmpty(float[] a) {
        return a == null || a.length == 0;
    }

    public static void isNullOrEmpty(float[] a, Runnable action) {
        if (isNullOrEmpty(a)) {
            action.run();
        }
    }

    public static boolean isNullOrEmpty(double[] a) {
        return a == null || a.length == 0;
    }

    public static void isNullOrEmpty(double[] a, Runnable action) {
        if (isNullOrEmpty(a)) {
            action.run();
        }
    }

    public static boolean isNullOrEmpty(boolean[] a) {
        return a == null || a.length == 0;
    }

    public static void isNullOrEmpty(boolean[] a, Runnable action) {
        if (isNullOrEmpty(a)) {
            action.run();
        }
    }

    public static boolean isNullOrEmpty(char[] a) {
        return a == null || a.length == 0;
    }

    public static void isNullOrEmpty(char[] a, Runnable action) {
        if (isNullOrEmpty(a)) {
            action.run();
        }
    }

    public static boolean isNullOrEmpty(byte[] a) {
        return a == null || a.length == 0;
    }

    public static void isNullOrEmpty(byte[] a, Runnable action) {
        if (isNullOrEmpty(a)) {
            action.run();
        }
    }

    public static boolean isNullOrEmpty(Object[] a) {
        return a == null || a.length == 0;
    }

    public static void isNullOrEmpty(Object[] a, Runnable action) {
        if (isNullOrEmpty(a)) {
            action.run();
        }
    }

    @SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "OptionalAssignedToNull"})
    public static boolean isNullOrEmpty(Optional<?> o) {
        if (o == null) {
            return true;
        }

        if (!o.isPresent()) {
            return true;
        }

        Object v = o.get();
        return isNullOrEmpty(v);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static void isNullOrEmpty(Optional<?> o, Runnable action) {
        if (isNullOrEmpty(o)) {
            action.run();
        }
    }

    public static boolean notNullAndEmpty(Object o) {
        return !isNullOrEmpty(o);
    }

    public static void notNullAndEmpty(Object o, Runnable action) {
        if (notNullAndEmpty(o)) {
            action.run();
        }
    }

    public static boolean notNullAndEmpty(String s) {
        return !isNullOrEmpty(s);
    }

    public static void notNullAndEmpty(String s, Runnable action) {
        if (notNullAndEmpty(s)) {
            action.run();
        }
    }

    public static boolean notNullAndEmpty(Collection<?> c) {
        return !isNullOrEmpty(c);
    }

    public static void notNullAndEmpty(Collection<?> c, Runnable action) {
        if (notNullAndEmpty(c)) {
            action.run();
        }
    }

    public static boolean notNullAndEmpty(Map<?, ?> m) {
        return !isNullOrEmpty(m);
    }

    public static void notNullAndEmpty(Map<?, ?> m, Runnable action) {
        if (notNullAndEmpty(m)) {
            action.run();
        }
    }

    public static boolean notNullAndEmpty(short[] a) {
        return !isNullOrEmpty(a);
    }

    public static void notNullAndEmpty(short[] a, Runnable action) {
        if (notNullAndEmpty(a)) {
            action.run();
        }
    }

    public static boolean notNullAndEmpty(int[] a) {
        return !isNullOrEmpty(a);
    }

    public static void notNullAndEmpty(int[] a, Runnable action) {
        if (notNullAndEmpty(a)) {
            action.run();
        }
    }

    public static boolean notNullAndEmpty(long[] a) {
        return !isNullOrEmpty(a);
    }

    public static void notNullAndEmpty(long[] a, Runnable action) {
        if (notNullAndEmpty(a)) {
            action.run();
        }
    }

    public static boolean notNullAndEmpty(float[] a) {
        return !isNullOrEmpty(a);
    }

    public static void notNullAndEmpty(float[] a, Runnable action) {
        if (notNullAndEmpty(a)) {
            action.run();
        }
    }

    public static boolean notNullAndEmpty(double[] a) {
        return !isNullOrEmpty(a);
    }

    public static void notNullAndEmpty(double[] a, Runnable action) {
        if (notNullAndEmpty(a)) {
            action.run();
        }
    }

    public static boolean notNullAndEmpty(boolean[] a) {
        return !isNullOrEmpty(a);
    }

    public static void notNullAndEmpty(boolean[] a, Runnable action) {
        if (notNullAndEmpty(a)) {
            action.run();
        }
    }

    public static boolean notNullAndEmpty(char[] a) {
        return !isNullOrEmpty(a);
    }

    public static void notNullAndEmpty(char[] a, Runnable action) {
        if (notNullAndEmpty(a)) {
            action.run();
        }
    }

    public static boolean notNullAndEmpty(byte[] a) {
        return !isNullOrEmpty(a);
    }

    public static void notNullAndEmpty(byte[] a, Runnable action) {
        if (notNullAndEmpty(a)) {
            action.run();
        }
    }

    public static boolean notNullAndEmpty(Object[] a) {
        return !isNullOrEmpty(a);
    }

    public static void notNullAndEmpty(Object[] a, Runnable action) {
        if (notNullAndEmpty(a)) {
            action.run();
        }
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static boolean notNullAndEmpty(Optional<?> o) {
        return !isNullOrEmpty(o);
    }

    public static void notNullAndEmpty(Optional<?> o, Runnable action) {
        if (notNullAndEmpty(o)) {
            action.run();
        }
    }

    public static <TargetType> void notNullAndEmpty(Optional<TargetType> o, Consumer<TargetType> action) {
        if (notNullAndEmpty(o)) {
            action.accept(o.get());
        }
    }

}
