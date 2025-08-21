package com.github.michaeldsa.aside;

public interface OpsCommand<T,U,V> {
    V execute(T operation, U argument);
}
