package org.misty.utils;

import java.util.Objects;
import java.util.Optional;

public class Tracked {

    public static Tracked create() {
        return new Tracked(null, "", true);
    }

    public static Tracked create(String name) {
        return new Tracked(null, name, true);
    }

    public static Tracked create(String name, String id) {
        return new Tracked(null, name, id);
    }

    public static String random() {
        String now = Long.toString(System.currentTimeMillis(), Character.MAX_RADIX);
        String random = String.format("%04d", (int) (Math.random() * 1_0000));
        return now + random;
    }

    private final Optional<Tracked> parent;

    private final String id;

    private final String msg;

    private Tracked(Tracked parent, String name, boolean joinIdentifier) {
        this(parent, name, joinIdentifier ? random() : "");
    }

    private Tracked(Tracked parent, String name, String random) {
        this.parent = Optional.ofNullable(parent);

        String id = parent == null ? "" : parent.id + "|";
        id += name == null || name.isEmpty() ? "" : name + (random.isEmpty() ? "" : ":");
        this.id = id + random;

        this.msg = "TRACKED{" + this.id + '}';
    }

    /**
     * similar to {@link String#format(String, Object...)}
     */
    public String say(String format, Object... args) {
        return say(String.format(format, args));
    }

    public String say(String msg) {
        return this.msg + " " + msg;
    }

    public Tracked link(String name) {
        return link(name, "");
    }

    public Tracked linkWithRandom(String name) {
        return link(name, random());
    }

    public Tracked link(String name, String id) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name is null or empty");
        }

        return new Tracked(this, name, id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tracked)) return false;
        Tracked tracked = (Tracked) o;
        return Objects.equals(id, tracked.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return this.msg;
    }

    public Optional<Tracked> getParent() {
        return parent;
    }

    public String getId() {
        return id;
    }

}
