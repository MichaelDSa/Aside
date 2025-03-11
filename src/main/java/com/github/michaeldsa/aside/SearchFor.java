package com.github.michaeldsa.aside;

public interface SearchFor {

    static Search userHomeDirectories() {
        return new UserHomeDirectories();
    }

    static Search mockHomeUserDirectories() {
        return new MockHomeUserDirectories();
    }

}
