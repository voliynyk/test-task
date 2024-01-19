import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import dto.TestTaskGetItemRequest;
import dto.TestTaskGetItemResponse;
import dto.TestTaskRecord;
import java.util.HashMap;
import java.util.Map;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;

public class TestTaskGetItem
    implements RequestHandler<TestTaskGetItemRequest, TestTaskGetItemResponse> {

  private static final String DYNAMODB_TABLE_NAME = "test-task-table";

  private DynamoDbClient dynamoDB = DynamoDbClient.builder().region(Region.EU_CENTRAL_1).build();

  @Override
  public TestTaskGetItemResponse handleRequest(
      TestTaskGetItemRequest itemRequest, Context context) {
    return buildResponse(getTestTaskItem(itemRequest));
  }

  private GetItemResponse getTestTaskItem(TestTaskGetItemRequest itemRequest) {
    HashMap<String, AttributeValue> keyToGet = new HashMap<>();
    keyToGet.put("recordId", AttributeValue.builder().s(itemRequest.getRecordId()).build());
    GetItemRequest request =
        GetItemRequest.builder().key(keyToGet).tableName(DYNAMODB_TABLE_NAME).build();

    try {
      return dynamoDB.getItem(request);
    } catch (Exception ex) {
      System.err.println(ex.getMessage());
      System.exit(1);
    }
    return null;
  }

  private TestTaskGetItemResponse buildResponse(GetItemResponse response) {
    TestTaskRecord record = buildRecord(response);
    return record == null ? null : new TestTaskGetItemResponse(record);
  }

  private TestTaskRecord buildRecord(GetItemResponse response) {
    if (response != null) {
      Map<String, AttributeValue> map = response.item();
      return new TestTaskRecord(map.get("recordId").s(), map.get("name").s(), map.get("orgId").s());
    }
    return null;
  }
}
