package com.github.michaeldsa.aside.Ops;

import java.util.Objects;

public interface CrudOps<T,R> extends Ops<T,R> {

    @Override
    R execute(T t);

    default <V> CrudOps<T,V> andThen(CrudOps<? super R,? extends V> after) {
        Objects.requireNonNull(after);
        return t -> after.execute(execute(t));
    }

    default <V> CrudOps<V,R> compose(CrudOps<? super V,? extends T> before) {
        Objects.requireNonNull(before);
        return t -> execute(before.execute(t));
    }

    default CrudOps<T,T> identity() {
        return t -> t;
    }

}
