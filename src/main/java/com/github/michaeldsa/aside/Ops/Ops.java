package com.github.michaeldsa.aside.Ops;

import com.github.michaeldsa.aside.AsidePath.MetaPath;

import java.util.Objects;

@FunctionalInterface
public interface Ops<T,R> {

    R execute(T t);

    default <V> Ops<T,V> andThen(Ops<? super R,? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.execute(execute(t));
    }

    default <V> Ops<V,R> compose(Ops<? super V,? extends T> before) {
        Objects.requireNonNull(before);
        return (V v) -> execute(before.execute(v));
    }

    static <T> Ops<T,T> identity(){
        return t -> t;
    }

    static Ops<MetaPath,MetaPath> factory(Ops<MetaPath,MetaPath> co) {
        return co;
    }
}
