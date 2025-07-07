package com.github.michaeldsa.aside;

public class SearchFor {

    private SearchFor () {}

    static Search userHomeDirectories() {
        return new UserHomeDirectories();
    }

    static Search mockHomeUserDirectories() {
        return new MockHomeUserDirectories();
    }

}
