package dto;

public class TestTaskGetItemResponse {

  private TestTaskRecord record;

  public TestTaskGetItemResponse(TestTaskRecord record) {
    this.record = record;
  }

  public TestTaskRecord getRecord() {
    return record;
  }

  public void setRecord(TestTaskRecord record) {
    this.record = record;
  }
}
