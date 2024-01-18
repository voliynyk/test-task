import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import dto.TestTaskGetRequest;
import dto.TestTaskGetResponse;
import dto.TestTaskRecord;
import java.util.List;
import java.util.stream.Collectors;

public class TestTaskGetRecords implements RequestHandler<TestTaskGetRequest, TestTaskGetResponse> {

  private String DYNAMODB_TABLE_NAME = "test-task-table";
  private AmazonDynamoDB amazonDynamoDB =
      AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();

  @Override
  public TestTaskGetResponse handleRequest(TestTaskGetRequest getRequest, Context context) {
    return new TestTaskGetResponse(getTestTaskRecords(getRequest));
  }

  public List<TestTaskRecord> getTestTaskRecords(TestTaskGetRequest getRequest) {
    ScanRequest scanRequest = new ScanRequest().withTableName(DYNAMODB_TABLE_NAME);

    ScanResult scanResponse = amazonDynamoDB.scan(scanRequest);
    return scanResponse.getItems().stream()
        .map(
            map ->
                new TestTaskRecord(
                    map.get("recordId").getS(), map.get("name").getS(), map.get("orgId").getS()))
        .collect(Collectors.toList());
  }
}
