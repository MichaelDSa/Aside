package com.github.michaeldsa.aside.AsidePathElement;

import java.util.Objects;

public class Author {
    private final String delimiter = "\u2591"; // (light shade character) looks like: ░
    private final String placeMarker = "\u220e"; // (tombstone character) looks like: ∎

    private boolean configFormatOK = true;

    private String lastName;
    private String prefix;
    private String firstName;
    private String middleNames;
    private String initials;
    private String postNominals;

    public Author(String lastName, String prefix, String firstName, String middleNames, String initials, String postNominals) {
        this.lastName = lastName.isBlank() ? placeMarker : lastName;
        this.prefix = prefix.isBlank() ? placeMarker : prefix;
        this.firstName = firstName.isBlank() ? placeMarker : firstName;
        this.middleNames = middleNames.isBlank() ? placeMarker : middleNames;
        this.initials  = initials.isBlank() ? placeMarker : initials;
        this.postNominals = postNominals.isBlank() ? placeMarker : postNominals;
    }
    public Author(String configFormattedAuthorString) {
        // formatted string must have 5 delimiter characters.
        if (configFormattedAuthorString.length() - configFormattedAuthorString.replace(delimiter, "").length() == 5) {
            String[] fields = configFormattedAuthorString.split(delimiter);
            this.lastName = fields[0];
            this.prefix = fields[1];
            this.firstName = fields[2];
            this.middleNames = fields[3];
            this.initials = fields[4];
            this.postNominals = fields[5];
        } else {
            /* If we have the wrong number of delimiters, it means
            the user may have modified the properties file, so
            preserve the mod. */
            configFormatOK = false;
            this.lastName = configFormattedAuthorString;
            this.prefix = placeMarker;
            this.firstName = placeMarker;
            this.middleNames = placeMarker;
            this.initials = placeMarker;
            this.postNominals = placeMarker;
        }
    }

    public String getLastName() { return lastName;}
    public String getPrefix() { return prefix; }
    public String getFirstName() { return firstName; }
    public String getMiddleNames() { return middleNames; }
    public String getInitials() { return initials; }
    public String getPostNominals() { return postNominals;}

    public void setLastName(String lastName) {
        if (!lastName.isBlank()) {
            this.lastName = lastName;
        }
    }
    public void setPrefix(String prefix) {
        if (!prefix.isBlank()) {
            this.prefix = prefix;
        }
    }
    public void setFirstName(String firstName) {
        if (!firstName.isBlank()) {
            this.firstName = firstName;
        }
    }
    public void setMiddleNames(String middleNames) {
        if (!middleNames.isBlank()) {
            this.middleNames = middleNames;
        }
    }
    public void setInitials(String initials) {
        if (!initials.isBlank()) {
            this.initials = initials;
        }
    }
    public void setPostNominals(String postNominals) {
        if (!postNominals.isBlank()) {
            this.postNominals = postNominals;
        }
    }

    public String getDisplayName() {
        String last = lastName.equals(placeMarker) ? "" : lastName; // comma goes here
        String honorific = prefix.equals(placeMarker) ? "" : prefix + " ";
        String first = firstName.equals(placeMarker) ? "" : firstName + " ";
        String middle = middleNames.equals(placeMarker) ? "" : middleNames + " ";
        String initial = initials.equals(placeMarker) ? "" : initials + " ";
        String suffix = postNominals.equals(placeMarker) ? "" : postNominals;

        String comma = honorific.isBlank() && first.isBlank() && middle.isBlank() && initial.isBlank() && suffix.isBlank() ? " " : ", ";

        String result = last + comma + honorific + first + middle + initial + suffix;

        return result;
    }

    public String getConfigFormattedString() {
        /* if propFormatStringOK is false, it means user may have
        modified properties file, so preserve the mod. */
        return configFormatOK
                ?
                lastName + delimiter +
                prefix + delimiter +
                firstName + delimiter +
                middleNames + delimiter +
                initials + delimiter +
                postNominals
                :
                lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Author author)) return false;
        return Objects.equals(lastName, author.lastName) && Objects.equals(prefix, author.prefix) && Objects.equals(firstName, author.firstName) && Objects.equals(middleNames, author.middleNames) && Objects.equals(initials, author.initials) && Objects.equals(postNominals, author.postNominals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(delimiter, placeMarker, lastName, prefix, firstName, middleNames, initials, postNominals);
    }

    @Override
    public String toString() {
        return "Author{" +
                "lastName='" + lastName + '\'' +
                ", prefix='" + prefix + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleNames='" + middleNames + '\'' +
                ", initials='" + initials + '\'' +
                ", postNominals='" + postNominals + '\'' +
                '}';
    }

}
