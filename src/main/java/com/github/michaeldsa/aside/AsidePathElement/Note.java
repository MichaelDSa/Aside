package com.github.michaeldsa.aside.AsidePathElement;

import java.util.HashSet;

public abstract class Note extends AsidePathElement {
    protected String title;
    protected HashSet<String> to;
    protected HashSet<String> from;
    protected HashSet<String> tags;
    protected String content;

    public abstract Category getCategory();
    public abstract String getTitle();
    public abstract HashSet<String> getTo();
    public abstract HashSet<String> getFrom();
    public abstract HashSet<String> getTags();
    public abstract String getContent();
    public abstract boolean to_contains(String value);
    public abstract boolean from_contains(String value);
    public abstract boolean tags_contains(String value);




}
