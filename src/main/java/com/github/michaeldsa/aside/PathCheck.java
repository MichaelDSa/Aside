package com.github.michaeldsa.aside;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathCheck {
    private final PathKeeper pk;

    public PathCheck() {
        pk = PathKeeper.INSTANCE;
    }

    public Path currentConvert(Path candidate) {

        // in case candidate begins with '/':
        if(candidate.toString().startsWith("/")) {
            candidate = Paths.get(candidate.toString().substring(1));
        }

        // All categories must start with either current or home:
        if(!candidate.startsWith(pk.getHome())) {
            candidate = pk.getCurrent().resolve(candidate);
        }

        // remove any newfound redundancy from candidate:
        candidate = candidate.normalize();

        // element [0] must be home. No other element may be home
        return validHomeUsage(candidate) ? candidate : null;
    }

    // returns true unless candidate is null, does not start with
    // home, or contains home in an element other than [0].
    private boolean validHomeUsage(Path candidate) {
        if(candidate == null || !candidate.startsWith(pk.getHome()) ) {
            return false;
        }

        if(candidate.getNameCount() > 1) {
            Path subpath = candidate.subpath(1, candidate.getNameCount());
            for(Path p : subpath) {
                if(p.toString().equals(pk.getHome().toString())) {
                    return false;
                }
            }
        }
        return true;
    }
}
