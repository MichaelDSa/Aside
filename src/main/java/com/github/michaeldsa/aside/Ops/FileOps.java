package com.github.michaeldsa.aside.Ops;

import java.util.Objects;

public interface FileOps<T,R> extends Ops<T,R> {

    @Override
    R execute(T t);

    default <V> FileOps<T,V> andThen(FileOps<? super R,? extends V> after) {
        Objects.requireNonNull(after);
        return t -> after.execute(execute(t));
    }

    default <V> FileOps<V,R> compose(FileOps<? super V,? extends T> before) {
        Objects.requireNonNull(before);
        return t -> execute(before.execute(t));
    }

    default FileOps<T,T> identity() {
        return t -> t;
    }

}
