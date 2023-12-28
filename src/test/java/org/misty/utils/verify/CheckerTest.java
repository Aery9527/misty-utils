package org.misty.utils.verify;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class CheckerTest {

    @Test
    public void test_Object() {
        check((Object) null, Checker::isNullOrEmpty, Checker::isNullOrEmpty, true);
        check((Object) null, Checker::notNullAndEmpty, Checker::notNullAndEmpty, false);
        check(new CheckerTest(), Checker::isNullOrEmpty, Checker::isNullOrEmpty, false);
        check(new CheckerTest(), Checker::notNullAndEmpty, Checker::notNullAndEmpty, true);

        check((Object) Optional.empty(), Checker::isNullOrEmpty, Checker::isNullOrEmpty, true);
        check((Object) Optional.empty(), Checker::notNullAndEmpty, Checker::notNullAndEmpty, false);

        check((Object) new short[]{}, Checker::isNullOrEmpty, Checker::isNullOrEmpty, true);
        check((Object) new short[]{}, Checker::notNullAndEmpty, Checker::notNullAndEmpty, false);
        check((Object) new short[]{1}, Checker::isNullOrEmpty, Checker::isNullOrEmpty, false);
        check((Object) new short[]{1}, Checker::notNullAndEmpty, Checker::notNullAndEmpty, true);

        check((Object) new int[]{}, Checker::isNullOrEmpty, Checker::isNullOrEmpty, true);
        check((Object) new int[]{}, Checker::notNullAndEmpty, Checker::notNullAndEmpty, false);
        check((Object) new int[]{1}, Checker::isNullOrEmpty, Checker::isNullOrEmpty, false);
        check((Object) new int[]{1}, Checker::notNullAndEmpty, Checker::notNullAndEmpty, true);

        check((Object) new long[]{}, Checker::isNullOrEmpty, Checker::isNullOrEmpty, true);
        check((Object) new long[]{}, Checker::notNullAndEmpty, Checker::notNullAndEmpty, false);
        check((Object) new long[]{1}, Checker::isNullOrEmpty, Checker::isNullOrEmpty, false);
        check((Object) new long[]{1}, Checker::notNullAndEmpty, Checker::notNullAndEmpty, true);

        check((Object) new float[]{}, Checker::isNullOrEmpty, Checker::isNullOrEmpty, true);
        check((Object) new float[]{}, Checker::notNullAndEmpty, Checker::notNullAndEmpty, false);
        check((Object) new float[]{1}, Checker::isNullOrEmpty, Checker::isNullOrEmpty, false);
        check((Object) new float[]{1}, Checker::notNullAndEmpty, Checker::notNullAndEmpty, true);

        check((Object) new double[]{}, Checker::isNullOrEmpty, Checker::isNullOrEmpty, true);
        check((Object) new double[]{}, Checker::notNullAndEmpty, Checker::notNullAndEmpty, false);
        check((Object) new double[]{1}, Checker::isNullOrEmpty, Checker::isNullOrEmpty, false);
        check((Object) new double[]{1}, Checker::notNullAndEmpty, Checker::notNullAndEmpty, true);

        check((Object) new boolean[]{}, Checker::isNullOrEmpty, Checker::isNullOrEmpty, true);
        check((Object) new boolean[]{}, Checker::notNullAndEmpty, Checker::notNullAndEmpty, false);
        check((Object) new boolean[]{true}, Checker::isNullOrEmpty, Checker::isNullOrEmpty, false);
        check((Object) new boolean[]{false}, Checker::notNullAndEmpty, Checker::notNullAndEmpty, true);

        check((Object) new char[]{}, Checker::isNullOrEmpty, Checker::isNullOrEmpty, true);
        check((Object) new char[]{}, Checker::notNullAndEmpty, Checker::notNullAndEmpty, false);
        check((Object) new char[]{1}, Checker::isNullOrEmpty, Checker::isNullOrEmpty, false);
        check((Object) new char[]{1}, Checker::notNullAndEmpty, Checker::notNullAndEmpty, true);

        check((Object) new byte[]{}, Checker::isNullOrEmpty, Checker::isNullOrEmpty, true);
        check((Object) new byte[]{}, Checker::notNullAndEmpty, Checker::notNullAndEmpty, false);
        check((Object) new byte[]{1}, Checker::isNullOrEmpty, Checker::isNullOrEmpty, false);
        check((Object) new byte[]{1}, Checker::notNullAndEmpty, Checker::notNullAndEmpty, true);
    }

    @Test
    public void test_String() {
        BiConsumer<String, Boolean> test = (target, expectedNullOrEmpty) -> {
            check(target, Checker::isNullOrEmpty, Checker::isNullOrEmpty, expectedNullOrEmpty);
            check(target, Checker::notNullAndEmpty, Checker::notNullAndEmpty, !expectedNullOrEmpty);
            check((Object) target, Checker::isNullOrEmpty, Checker::isNullOrEmpty, expectedNullOrEmpty);
            check((Object) target, Checker::notNullAndEmpty, Checker::notNullAndEmpty, !expectedNullOrEmpty);
        };
        test.accept(null, true);
        test.accept("", true);
        test.accept("123", false);
    }

    @Test
    public void test_Collection() {
        BiConsumer<Collection, Boolean> test = (target, expectedNullOrEmpty) -> {
            check(target, Checker::isNullOrEmpty, Checker::isNullOrEmpty, expectedNullOrEmpty);
            check(target, Checker::notNullAndEmpty, Checker::notNullAndEmpty, !expectedNullOrEmpty);
            check((Object) target, Checker::isNullOrEmpty, Checker::isNullOrEmpty, expectedNullOrEmpty);
            check((Object) target, Checker::notNullAndEmpty, Checker::notNullAndEmpty, !expectedNullOrEmpty);
        };
        test.accept(null, true);
        test.accept(Collections.emptyList(), true);
        test.accept(Collections.singleton(""), false);
    }

    @Test
    public void test_Map() {
        BiConsumer<Map, Boolean> test = (target, expectedNullOrEmpty) -> {
            check(target, Checker::isNullOrEmpty, Checker::isNullOrEmpty, expectedNullOrEmpty);
            check(target, Checker::notNullAndEmpty, Checker::notNullAndEmpty, !expectedNullOrEmpty);
            check((Object) target, Checker::isNullOrEmpty, Checker::isNullOrEmpty, expectedNullOrEmpty);
            check((Object) target, Checker::notNullAndEmpty, Checker::notNullAndEmpty, !expectedNullOrEmpty);
        };
        test.accept(null, true);
        test.accept(Collections.emptyMap(), true);
        test.accept(Collections.singletonMap("", ""), false);
    }

    @Test
    public void test_Array() {
        BiConsumer<Object[], Boolean> test_objectArray = (target, expectedNullOrEmpty) -> {
            check(target, Checker::isNullOrEmpty, Checker::isNullOrEmpty, expectedNullOrEmpty);
            check(target, Checker::notNullAndEmpty, Checker::notNullAndEmpty, !expectedNullOrEmpty);
            check((Object) target, Checker::isNullOrEmpty, Checker::isNullOrEmpty, expectedNullOrEmpty);
            check((Object) target, Checker::notNullAndEmpty, Checker::notNullAndEmpty, !expectedNullOrEmpty);
        };
        test_objectArray.accept(null, true);
        test_objectArray.accept(new String[]{}, true);
        test_objectArray.accept(new String[]{""}, false);

        BiConsumer<short[], Boolean> test_shortArray = (target, expectedNullOrEmpty) -> {
            check(target, Checker::isNullOrEmpty, Checker::isNullOrEmpty, expectedNullOrEmpty);
            check(target, Checker::notNullAndEmpty, Checker::notNullAndEmpty, !expectedNullOrEmpty);
            check((Object) target, Checker::isNullOrEmpty, Checker::isNullOrEmpty, expectedNullOrEmpty);
            check((Object) target, Checker::notNullAndEmpty, Checker::notNullAndEmpty, !expectedNullOrEmpty);
        };
        test_shortArray.accept(new short[]{}, true);
        test_shortArray.accept(new short[]{1}, false);

        BiConsumer<int[], Boolean> test_intArray = (target, expectedNullOrEmpty) -> {
            check(target, Checker::isNullOrEmpty, Checker::isNullOrEmpty, expectedNullOrEmpty);
            check(target, Checker::notNullAndEmpty, Checker::notNullAndEmpty, !expectedNullOrEmpty);
            check((Object) target, Checker::isNullOrEmpty, Checker::isNullOrEmpty, expectedNullOrEmpty);
            check((Object) target, Checker::notNullAndEmpty, Checker::notNullAndEmpty, !expectedNullOrEmpty);
        };
        test_intArray.accept(new int[]{}, true);
        test_intArray.accept(new int[]{1}, false);

        BiConsumer<long[], Boolean> test_longArray = (target, expectedNullOrEmpty) -> {
            check(target, Checker::isNullOrEmpty, Checker::isNullOrEmpty, expectedNullOrEmpty);
            check(target, Checker::notNullAndEmpty, Checker::notNullAndEmpty, !expectedNullOrEmpty);
            check((Object) target, Checker::isNullOrEmpty, Checker::isNullOrEmpty, expectedNullOrEmpty);
            check((Object) target, Checker::notNullAndEmpty, Checker::notNullAndEmpty, !expectedNullOrEmpty);
        };
        test_longArray.accept(new long[]{}, true);
        test_longArray.accept(new long[]{1}, false);

        BiConsumer<float[], Boolean> test_floatArray = (target, expectedNullOrEmpty) -> {
            check(target, Checker::isNullOrEmpty, Checker::isNullOrEmpty, expectedNullOrEmpty);
            check(target, Checker::notNullAndEmpty, Checker::notNullAndEmpty, !expectedNullOrEmpty);
            check((Object) target, Checker::isNullOrEmpty, Checker::isNullOrEmpty, expectedNullOrEmpty);
            check((Object) target, Checker::notNullAndEmpty, Checker::notNullAndEmpty, !expectedNullOrEmpty);
        };
        test_floatArray.accept(new float[]{}, true);
        test_floatArray.accept(new float[]{1}, false);

        BiConsumer<double[], Boolean> test_doubleArray = (target, expectedNullOrEmpty) -> {
            check(target, Checker::isNullOrEmpty, Checker::isNullOrEmpty, expectedNullOrEmpty);
            check(target, Checker::notNullAndEmpty, Checker::notNullAndEmpty, !expectedNullOrEmpty);
            check((Object) target, Checker::isNullOrEmpty, Checker::isNullOrEmpty, expectedNullOrEmpty);
            check((Object) target, Checker::notNullAndEmpty, Checker::notNullAndEmpty, !expectedNullOrEmpty);
        };
        test_doubleArray.accept(new double[]{}, true);
        test_doubleArray.accept(new double[]{1}, false);

        BiConsumer<boolean[], Boolean> test_booleanArray = (target, expectedNullOrEmpty) -> {
            check(target, Checker::isNullOrEmpty, Checker::isNullOrEmpty, expectedNullOrEmpty);
            check(target, Checker::notNullAndEmpty, Checker::notNullAndEmpty, !expectedNullOrEmpty);
            check((Object) target, Checker::isNullOrEmpty, Checker::isNullOrEmpty, expectedNullOrEmpty);
            check((Object) target, Checker::notNullAndEmpty, Checker::notNullAndEmpty, !expectedNullOrEmpty);
        };
        test_booleanArray.accept(new boolean[]{}, true);
        test_booleanArray.accept(new boolean[]{true}, false);
        Assertions.assertThat(Checker.isNullOrEmpty(new boolean[]{})).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty(new boolean[]{})).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty(new boolean[]{true})).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty(new boolean[]{true})).isEqualTo(true);

        BiConsumer<char[], Boolean> test_charArray = (target, expectedNullOrEmpty) -> {
            check(target, Checker::isNullOrEmpty, Checker::isNullOrEmpty, expectedNullOrEmpty);
            check(target, Checker::notNullAndEmpty, Checker::notNullAndEmpty, !expectedNullOrEmpty);
            check((Object) target, Checker::isNullOrEmpty, Checker::isNullOrEmpty, expectedNullOrEmpty);
            check((Object) target, Checker::notNullAndEmpty, Checker::notNullAndEmpty, !expectedNullOrEmpty);
        };
        test_charArray.accept(new char[]{}, true);
        test_charArray.accept(new char[]{1}, false);
        Assertions.assertThat(Checker.isNullOrEmpty(new char[]{})).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty(new char[]{})).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty(new char[]{1})).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty(new char[]{1})).isEqualTo(true);

        BiConsumer<byte[], Boolean> test_byteArray = (target, expectedNullOrEmpty) -> {
            check(target, Checker::isNullOrEmpty, Checker::isNullOrEmpty, expectedNullOrEmpty);
            check(target, Checker::notNullAndEmpty, Checker::notNullAndEmpty, !expectedNullOrEmpty);
            check((Object) target, Checker::isNullOrEmpty, Checker::isNullOrEmpty, expectedNullOrEmpty);
            check((Object) target, Checker::notNullAndEmpty, Checker::notNullAndEmpty, !expectedNullOrEmpty);
        };
        test_byteArray.accept(new byte[]{}, true);
        test_byteArray.accept(new byte[]{1}, false);
    }

    @Test
    public void test_Optional() {
        BiConsumer<Optional, Boolean> test_byteArray = (target, expectedNullOrEmpty) -> {
            check(target, Checker::isNullOrEmpty, Checker::isNullOrEmpty, expectedNullOrEmpty);
            check(target, Checker::notNullAndEmpty, Checker::notNullAndEmpty, !expectedNullOrEmpty);
            check((Object) target, Checker::isNullOrEmpty, Checker::isNullOrEmpty, expectedNullOrEmpty);
            check((Object) target, Checker::notNullAndEmpty, Checker::notNullAndEmpty, !expectedNullOrEmpty);

            AtomicReference<Object> checkPoint = new AtomicReference<>();
            Checker.notNullAndEmpty(target, checkPoint::set);
            if (expectedNullOrEmpty) {
                Assertions.assertThat(checkPoint.get()).isNull();
            } else {
                Assertions.assertThat(checkPoint.get() == target.get()).isTrue();
            }
        };
        test_byteArray.accept(null, true);
        test_byteArray.accept(Optional.empty(), true);

        test_byteArray.accept(Optional.of(""), true);
        test_byteArray.accept(Optional.of("1"), false);

        test_byteArray.accept(Optional.of(Collections.emptyList()), true);
        test_byteArray.accept(Optional.of(Collections.singleton("")), false);

        test_byteArray.accept(Optional.of(Collections.emptyMap()), true);
        test_byteArray.accept(Optional.of(Collections.singletonMap("", "")), false);

        test_byteArray.accept(Optional.of(new Object[]{}), true);
        test_byteArray.accept(Optional.of(new Object[]{1}), false);
    }

    private <TargetType> void check(TargetType target, Predicate<TargetType> test, BiConsumer<TargetType, Runnable> testWithAction, boolean expected) {
        Assertions.assertThat(test.test(target)).isEqualTo(expected);

        AtomicBoolean checkPoint = new AtomicBoolean(false);
        testWithAction.accept(target, () -> checkPoint.set(true));
        Assertions.assertThat(checkPoint.get()).isEqualTo(expected);
    }

}
