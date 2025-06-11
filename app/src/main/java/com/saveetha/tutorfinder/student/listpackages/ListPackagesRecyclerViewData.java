package com.saveetha.tutorfinder.student.listpackages;

public class ListPackagesRecyclerViewData {
    private String listPackageId;
    private String packageName;
    private String credits;
    private String packageCost;

    public ListPackagesRecyclerViewData(String listPackageId, String packageName, String credits, String packageCost) {
        this.listPackageId = listPackageId;
        this.packageName = packageName;
        this.credits = credits;
        this.packageCost = packageCost;
    }

    public String getListPackageId() {
        return listPackageId;
    }

    public void setListPackageId(String listPackageId) {
        this.listPackageId = listPackageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getPackageCost() {
        return packageCost;
    }

    public void setPackageCost(String packageCost) {
        this.packageCost = packageCost;
    }
}
