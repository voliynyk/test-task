import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import dto.TestTaskCreateRequest;
import dto.TestTaskCreateResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

public class TestTaskCreateRecord
    implements RequestHandler<TestTaskCreateRequest, TestTaskCreateResponse> {

  private static final String DYNAMODB_TABLE_NAME = "test-task-table";

  private DynamoDbClient dynamoDB = DynamoDbClient.builder().region(Region.EU_CENTRAL_1).build();

  @Override
  public TestTaskCreateResponse handleRequest(
      TestTaskCreateRequest createRequest, Context context) {
    System.out.println(createRequest);

    String response = persistData(createRequest);
    return buildResponse(response);
  }

  private String persistData(TestTaskCreateRequest createRequest) {

    Map<String, AttributeValue> attributesMap = new HashMap<>();

    String recordId = String.valueOf(UUID.randomUUID());

    attributesMap.put(
        "recordId", AttributeValue.builder().s(recordId).build());
    attributesMap.put("name", AttributeValue.builder().s(createRequest.getName()).build());
    attributesMap.put("orgId", AttributeValue.builder().s(createRequest.getOrgId()).build());

    PutItemRequest request =
        PutItemRequest.builder().tableName(DYNAMODB_TABLE_NAME).item(attributesMap).build();
    try {
      dynamoDB.putItem(request);
    } catch (Exception ex) {
      System.err.println(ex.getMessage());
      System.exit(1);
    }
    return recordId;
  }

  private TestTaskCreateResponse buildResponse(String response) {
    return response == null
        ? null
        : new TestTaskCreateResponse(response);
  }
}
