package dto;

public class TestTaskCreateResponse {

  private String recordId;

  public TestTaskCreateResponse(String recordId) {
    this.recordId = recordId;
  }

  public String getRecordId() {
    return recordId;
  }

  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }
}
