import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import dto.TestTaskCreateRequest;
import dto.TestTaskCreateResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TestTaskCreateRecord
    implements RequestHandler<TestTaskCreateRequest, TestTaskCreateResponse> {

  private String DYNAMODB_TABLE_NAME = "test-task-table";
  private AmazonDynamoDB amazonDynamoDB =
      AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();

  @Override
  public TestTaskCreateResponse handleRequest(TestTaskCreateRequest createRequest, Context context) {
    System.out.println(createRequest);

    persistData(createRequest);

    return new TestTaskCreateResponse("OK");
  }


  private void persistData(TestTaskCreateRequest createRequest) {

    Map<String, AttributeValue> attributesMap = new HashMap<>();

    attributesMap.put("recordId", new AttributeValue(String.valueOf(UUID.randomUUID())));
    attributesMap.put("name", new AttributeValue(createRequest.getName()));
    attributesMap.put("orgId", new AttributeValue(createRequest.getOrgId()));

    amazonDynamoDB.putItem(DYNAMODB_TABLE_NAME, attributesMap);
  }
}
