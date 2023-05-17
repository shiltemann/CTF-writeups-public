package org.apache.http.util;

import java.util.Collection;

public class Args {
    public static void check(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void check(boolean expression, String message, Object... args) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, args));
        }
    }

    public static void check(boolean expression, String message, Object arg) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, new Object[]{arg}));
        }
    }

    public static <T> T notNull(T argument, String name) {
        if (argument != null) {
            return argument;
        }
        throw new IllegalArgumentException(name + " may not be null");
    }

    public static <T extends CharSequence> T notEmpty(T argument, String name) {
        if (argument == null) {
            throw new IllegalArgumentException(name + " may not be null");
        } else if (!TextUtils.isEmpty(argument)) {
            return argument;
        } else {
            throw new IllegalArgumentException(name + " may not be empty");
        }
    }

    public static <T extends CharSequence> T notBlank(T argument, String name) {
        if (argument == null) {
            throw new IllegalArgumentException(name + " may not be null");
        } else if (!TextUtils.isBlank(argument)) {
            return argument;
        } else {
            throw new IllegalArgumentException(name + " may not be blank");
        }
    }

    public static <T extends CharSequence> T containsNoBlanks(T argument, String name) {
        if (argument == null) {
            throw new IllegalArgumentException(name + " may not be null");
        } else if (!TextUtils.containsBlanks(argument)) {
            return argument;
        } else {
            throw new IllegalArgumentException(name + " may not contain blanks");
        }
    }

    public static <E, T extends Collection<E>> T notEmpty(T argument, String name) {
        if (argument == null) {
            throw new IllegalArgumentException(name + " may not be null");
        } else if (!argument.isEmpty()) {
            return argument;
        } else {
            throw new IllegalArgumentException(name + " may not be empty");
        }
    }

    public static int positive(int n, String name) {
        if (n > 0) {
            return n;
        }
        throw new IllegalArgumentException(name + " may not be negative or zero");
    }

    public static long positive(long n, String name) {
        if (n > 0) {
            return n;
        }
        throw new IllegalArgumentException(name + " may not be negative or zero");
    }

    public static int notNegative(int n, String name) {
        if (n >= 0) {
            return n;
        }
        throw new IllegalArgumentException(name + " may not be negative");
    }

    public static long notNegative(long n, String name) {
        if (n >= 0) {
            return n;
        }
        throw new IllegalArgumentException(name + " may not be negative");
    }
}
