package com.endava.viperdemo.api.response;

public class AddTaskResponse {

  private Boolean success;
  private String failReason;

  public AddTaskResponse(Boolean success, String failReason) {
    this.success = success;
    this.failReason = failReason;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public String getFailReason() {
    return failReason;
  }

  public void setFailReason(String failReason) {
    this.failReason = failReason;
  }
}