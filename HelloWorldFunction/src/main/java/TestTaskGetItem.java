import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import dto.TestTaskGetItemRequest;
import dto.TestTaskGetItemResponse;
import dto.TestTaskRecord;
import java.util.Map;

public class TestTaskGetItem
    implements RequestHandler<TestTaskGetItemRequest, TestTaskGetItemResponse> {

  private String DYNAMODB_TABLE_NAME = "test-task-table";
  private AmazonDynamoDB amazonDynamoDB =
      AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();

  @Override
  public TestTaskGetItemResponse handleRequest(
      TestTaskGetItemRequest itemRequest, Context context) {
    return new TestTaskGetItemResponse(getTestTaskItem(itemRequest));
  }

  public TestTaskRecord getTestTaskItem(TestTaskGetItemRequest itemRequest) {
    GetItemRequest request =
        new GetItemRequest()
            .withTableName(DYNAMODB_TABLE_NAME)
            .addKeyEntry("recordId", new AttributeValue(itemRequest.getRecordId()));
    Map<String, AttributeValue> map = amazonDynamoDB.getItem(request).getItem();
    return new TestTaskRecord(
        map.get("recordId").getS(), map.get("name").getS(), map.get("orgId").getS());
  }
}
