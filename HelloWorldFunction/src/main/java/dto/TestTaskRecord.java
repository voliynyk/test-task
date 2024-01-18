package dto;

public class TestTaskRecord {

  private String recordId;
  private String name;
  private String orgId;

  public TestTaskRecord(String recordId, String name, String orgId) {
    this.recordId = recordId;
    this.name = name;
    this.orgId = orgId;
  }

  public String getRecordId() {
    return recordId;
  }

  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getOrgId() {
    return orgId;
  }

  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }
}
