package utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Random;

import org.skyscreamer.jsonassert.JSONAssert;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import cucumber.api.testng.TestNGCucumberRunner;
import static io.restassured.module.jsv.JsonSchemaValidatorSettings.settings;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

//import junit.framework.Assert;

import static io.restassured.config.EncoderConfig.encoderConfig;

/***
 * author : Subodh M This is created for Everon Test
 * This BaseTest Class use for common utils
 */

public class BaseTest {

	private final RestAssuredConfig restAssuredConfig = RestAssured.config()
			.encoderConfig(encoderConfig().defaultContentCharset("UTF-8"));
	protected TestNGCucumberRunner testNGCucumberRunner;
	public Response response;
	public RequestSpecification request;
	String cwd = System.getProperty("user.dir");

	public static String baseURL;
	public String URLParam = "";
	public String jsonRequestBody;
	public String endPoint;
	public String queryParam = "";

	// Sets base path
	public void setBasePath(String basePathTerm) {
		RestAssured.basePath = basePathTerm;
	}

	public String generateStringFromResource(String path) throws PetException {
		try {
			return new String(Files.readAllBytes(Paths.get(path)));
		} catch (Exception e) {
			e.printStackTrace();
			throw new PetException("Generate String from File" + e.getMessage());
		}
	}

	public void setURLParam(String URLParam) {
		this.URLParam = URLParam;
	}

	public String getURLParam() {
		return URLParam;
	}

	public void setqueryParam(Map<String, String> queryParameter) {
		for (Map.Entry<String, String> qParam : queryParameter.entrySet()) {
			queryParam = "?" + qParam.getKey() + "=" + qParam.getValue();
		}
	}

	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		BaseTest.baseURL = baseURL;
	}

	public String getqueryParam() {
		return queryParam;
	}

	// Created search query path
	public String getEndPoint() {
		endPoint = baseURL + getURLParam() + getqueryParam();
		return endPoint;
	}

	public String setRequestBody(String requestBodyFile) throws PetException {
		return jsonRequestBody = generateStringFromResource("./RequestBody/" + requestBodyFile + ".json");
	}

	@BeforeClass
	@Parameters({ "baseURL" })
	public void setUpClass(String baseURL) {

		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
		if (System.getProperty("env.baseURL") != null) {
			setBaseURL(System.getProperty("env.baseURL"));
		} else {
			setBaseURL(baseURL);
		}

	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() throws Exception {
		testNGCucumberRunner.finish();
	}

	// Returns response
	public Response getResponse(String httpType) throws PetException {
		// System.out.print("path: " + path +"\n");
		System.out.println(getEndPoint());
		request = RestAssured.given();
		switch (httpType.toLowerCase()) {
		case "get":
			response = request.contentType(ContentType.JSON).get(getEndPoint());
			break;
		case "post":
			response = request.config(restAssuredConfig).contentType(ContentType.JSON).body(jsonRequestBody).when()
					.post(getEndPoint());
			break;
		case "put":
			response = request.config(restAssuredConfig).contentType(ContentType.JSON).body(jsonRequestBody).when()
					.put(getEndPoint());
			break;
		case "delete":
			response = request.contentType(ContentType.JSON).delete(getEndPoint());
			break;
		default:
			throw new PetException("Wrong Http method");
		}
		return response;

	}

	// Returns JsonPath object
	public JsonPath getJsonPath(Response res) {
		String json = response.asString();
		System.out.print("returned json: " + json + "\n");
		return new JsonPath(json);
	}

	public int getRandom() {
		Random random = new Random();
		String value = String.format("%04d", random.nextInt(10000));
		return Integer.parseInt(value);
	}

	public String getRandomStatus(String[] array) {
		int rnd = new Random().nextInt(array.length);
		return array[rnd];
	}

	public void validateStatusCode(String expectedStatusCode) {
		int actualStatusCode = response.getStatusCode();
		Assert.assertEquals(actualStatusCode, Integer.parseInt(expectedStatusCode));
	}

	public void validateJsonSchemaValidator(String schemaFile) {
		JsonSchemaFactory factory = JsonSchemaFactory.newBuilder()
				.setValidationConfiguration(
						ValidationConfiguration.newBuilder().setDefaultVersion(SchemaVersion.DRAFTV4).freeze())
				.freeze();
		JsonSchemaValidator.settings = settings().with().jsonSchemaFactory(factory).and().with()
				.checkedValidation(true);
		String path = cwd + "/schema/" + schemaFile + ".json";
		File f = new File(path);
		response.then().assertThat().body(matchesJsonSchema(f));
	}

	public void validateExpectedJson(String expectedFileName) throws PetException {
		String expectedJson = generateStringFromResource(cwd + "/ExpectedResponseJson/" + expectedFileName + ".json");
		JSONAssert.assertEquals(expectedJson, response.getBody().asString(), true);

	}

}
