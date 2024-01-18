package dto;

public class TestTaskCreateRequest {

  private String name;
  private String orgId;

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

  @Override
  public String toString() {
    return "TestTaskCreateRequest{" + "name='" + name + '\'' + ", orgId='" + orgId + '\'' + '}';
  }
}
