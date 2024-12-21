package com.saveetha.tutorfinder.model.listpackages;

public class ListPackageInner {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getPackage_cost() {
        return package_cost;
    }

    public void setPackage_cost(String package_cost) {
        this.package_cost = package_cost;
    }

    public String id;
    public String package_name;
    public String description;
    public String credits;
    public String package_cost;
}
