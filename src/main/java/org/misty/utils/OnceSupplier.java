package org.misty.utils;

import org.misty.utils.fi.SupplierEx;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class OnceSupplier<Target> implements Supplier<Target>, SupplierEx<Target> {

    private final AtomicReference<Supplier<Target>> supplier = new AtomicReference<>();

    public OnceSupplier(SupplierEx<Target> firstSupplier) {
        final AtomicBoolean setup = new AtomicBoolean(false);
        this.supplier.set(() -> {
            synchronized (setup) {
                if (!setup.get()) {
                    Target target = firstSupplier.execute();
                    this.supplier.set(() -> target);

                    setup.set(true);
                }
            }

            return this.supplier.get().get();
        });
    }

    @Override
    public String toString() {
        return get().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        Target target1 = get();

        if (o instanceof OnceSupplier<?> that) {
            Object target2 = that.get();
            return target1.equals(target2);
        } else {
            return target1.equals(o);
        }
    }

    @Override
    public int hashCode() {
        Target target1 = get();
        return target1.hashCode();
    }

    @Override
    public Target handle() throws Exception {
        return this.supplier.get().get();
    }

    @Override
    public Target get() {
        return SupplierEx.super.execute();
    }

}
