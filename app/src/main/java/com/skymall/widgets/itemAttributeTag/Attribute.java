package com.skymall.widgets.itemAttributeTag;

import java.util.ArrayList;
import java.util.List;

public class Attribute {
    //For available items
    public List<String> aliasName = new ArrayList<>();
    //For unavailable items
    public List<String> FailureAliasName = new ArrayList<>();

    public List<String> getAliasName() {
        return aliasName;
    }

    public void setAliasName(List<String> aliasName) {
        this.aliasName = aliasName;
    }

    public List<String> getFailureAliasName() {
        return FailureAliasName;
    }

    public void setFailureAliasName(List<String> failureAliasName) {
        FailureAliasName = failureAliasName;

    }
}
