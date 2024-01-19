import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import dto.TestTaskGetRequest;
import dto.TestTaskGetResponse;
import dto.TestTaskRecord;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

public class TestTaskGetRecords implements RequestHandler<TestTaskGetRequest, TestTaskGetResponse> {

  private static final String DYNAMODB_TABLE_NAME = "test-task-table";
  private static final String ORG_ID_INDEX_NAME = "orgIdIndex";
  private static final String ORG_ID = "orgId";

  private DynamoDbClient dynamoDB = DynamoDbClient.builder().region(Region.EU_CENTRAL_1).build();

  @Override
  public TestTaskGetResponse handleRequest(TestTaskGetRequest getRequest, Context context) {
    return buildResponse(getTestTaskRecords(getRequest));
  }

  public QueryResponse getTestTaskRecords(TestTaskGetRequest getRequest) {
    Map<String, String> expressionAttributesNames = new HashMap<>();
    expressionAttributesNames.put("#" + ORG_ID, ORG_ID);
    Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
    expressionAttributeValues.put(
        ":" + ORG_ID, AttributeValue.builder().s(getRequest.getOrgId()).build());

    QueryRequest request =
        QueryRequest.builder()
            .tableName(DYNAMODB_TABLE_NAME)
            .indexName(ORG_ID_INDEX_NAME)
            .keyConditionExpression(String.format("#%s = :%s", ORG_ID, ORG_ID))
            .expressionAttributeNames(expressionAttributesNames)
            .expressionAttributeValues(expressionAttributeValues)
            .build();

    return dynamoDB.query(request);
  }

  private TestTaskGetResponse buildResponse(QueryResponse response) {
    List<TestTaskRecord> records = Collections.EMPTY_LIST;
    if (response != null) {
      records = response.items().stream().map(this::buildRecord).collect(Collectors.toList());
    }
    return new TestTaskGetResponse(records);
  }

  private TestTaskRecord buildRecord(Map<String, AttributeValue> map) {
    return new TestTaskRecord(map.get("recordId").s(), map.get("name").s(), map.get(ORG_ID).s());
  }
}
