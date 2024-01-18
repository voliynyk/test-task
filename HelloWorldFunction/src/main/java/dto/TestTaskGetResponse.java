package dto;

import java.util.List;

public class TestTaskGetResponse {

  private List<TestTaskRecord> records;

  public TestTaskGetResponse(List<TestTaskRecord> records) {
    this.records = records;
  }

  public List<TestTaskRecord> getRecords() {
    return records;
  }

  public void setRecords(List<TestTaskRecord> records) {
    this.records = records;
  }
}
