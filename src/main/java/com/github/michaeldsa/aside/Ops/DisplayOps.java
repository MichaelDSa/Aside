package com.github.michaeldsa.aside.Ops;

import java.util.Objects;

public interface DisplayOps<T,R> extends Ops<T,R> {

    @Override
    R execute(T t);

    default <V> DisplayOps<T,V> andThen(DisplayOps<? super R,? extends V> after) {
        Objects.requireNonNull(after);
        return t -> after.execute(execute(t));
    }

    default <V> DisplayOps<V,R> compose(DisplayOps<? super V,? extends T> before) {
        Objects.requireNonNull(before);
        return t -> execute(before.execute(t));
    }

    default DisplayOps<T,T> identity() {
        return t -> t;
    }
}
