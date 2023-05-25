package com.exist.project_plan.model.enums;

public enum ResponseStatus {
    Success("Success"),
    Failed("Failed");
    private String status;
    ResponseStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return this.status;
    }
}
