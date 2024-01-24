package org.misty.utils;

import org.misty.utils.verify.Verifier;

import java.util.Objects;
import java.util.Optional;

public class Tracked {

    public static Tracked create() {
        return new Tracked(null, "TRACKED", random());
    }

    public static Tracked create(Class<?> clazz) {
        return new Tracked(null, clazz.getSimpleName(), random());
    }

    public static Tracked create(Class<?> clazz, String id) {
        return new Tracked(null, clazz.getSimpleName(), id);
    }

    public static Tracked create(String title) {
        return new Tracked(null, title, random());
    }

    public static Tracked create(String title, String id) {
        return new Tracked(null, title, id);
    }

    public static String random() {
        String now = Long.toString(System.currentTimeMillis(), Character.MAX_RADIX);
        String random = String.format("%04d", (int) (Math.random() * 1_0000));
        return now + random;
    }

    private final Tracked parent;

    private final String title;

    private final String id;

    private final String msg;

    private Tracked(Tracked parent, String title, String id) {
        Verifier.refuseNullOrEmpty("title", title);
        Verifier.refuseNull("id", id);

        this.parent = parent;
        this.title = title;
        this.id = id;
        this.msg = mixMsg("{", "}");
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

    public Tracked link(Class<?> clazz) {
        return link(clazz.getSimpleName());
    }

    public Tracked link(String title) {
        return link(title, "");
    }

    public Tracked linkWithRandomId(String title) {
        return link(title, random());
    }

    public Tracked link(String title, String id) {
        Verifier.refuseNullOrEmpty("title", title);
        return new Tracked(this, title, id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tracked)) return false;
        Tracked tracked = (Tracked) o;
        return Objects.equals(this.msg, tracked.msg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msg);
    }

    @Override
    public String toString() {
        return this.msg;
    }

    private String mixMsg(String prefix, String suffix) {
        if (this.parent == null) {
            return this.title + prefix + this.id + suffix;
        } else {
            return this.parent.mixMsg(prefix, "|")
                    + this.title
                    + (this.id.isEmpty() ? "" : ":" + this.id)
                    + suffix;
        }
    }

    public Optional<Tracked> getParent() {
        return Optional.ofNullable(parent);
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

}
