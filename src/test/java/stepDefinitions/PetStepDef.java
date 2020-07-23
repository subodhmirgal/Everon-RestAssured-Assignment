package stepDefinitions;

import java.util.List;
import java.util.Map;

import org.testng.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import model.Pet;
import utils.BaseTest;
import utils.PetException;

/***
 * author : Subodh M This is created for Everon Test
 * All Stepdefinitions are written here
 */

public class PetStepDef extends BaseTest {

	String[] status = { "sold", "available", "pending" };
	Pet pet;

	@Given("^user set param (.*)")
	public void userSetContentJson(String param) {
		if (param.contains("ExistingID")) {
			setURLParam(param.replaceAll("ExistingID", Integer.toString(pet.getId())));
		} else {
			setURLParam(param);

		}
	}

	@Given("^user set the request body with Dyanmic values in resquest body and expected response$")
	public void user_set_the_request_body(DataTable request_Body) throws PetException {

		List<Map<String, String>> requestbody = request_Body.asMaps(String.class, String.class);
		for (Map<String, String> requestBody : requestbody) {
			pet.setRequestBodyAndExpectedResponse(requestBody, pet.getId(), pet.getStatus());
			setRequestBody(requestBody.get("RequestBody"));
		}
	}

	@When("^user make (.*) call$")
	public void whenuserMakehttpMethod(String httpMethod) throws PetException {
		response = getResponse(httpMethod);
	}

	@Then("^validate the status code as (.*)$")
	public void validate_the_status_code(String statusCode) {
		validateStatusCode(statusCode);
	}

	@Then("^validate json schema$")
	public void validate_json_schema(DataTable json_Schema) {
		List<Map<String, String>> jsonschema = json_Schema.asMaps(String.class, String.class);
		for (Map<String, String> jsonSchema : jsonschema) {
			validateJsonSchemaValidator(jsonSchema.get("jsonSchemaValue"));
		}
	}

	@Then("^validate response$")
	public void validate_response(DataTable responseJsons) {
		List<Map<String, String>> responseJsonss = responseJsons.asMaps(String.class, String.class);
		for (Map<String, String> responseJson : responseJsonss) {
			pet.validateResponse(responseJson, response);
		}
	}

	@Then("^validate error response$")
	public void validate_error_response(DataTable responseJsons) {
		List<Map<String, String>> responseJsonss = responseJsons.asMaps(String.class, String.class);
		for (Map<String, String> responseJson : responseJsonss) {
			pet.validateErrResponse(responseJson, response);
		}
	}

	@Then("^validate expected response$")
	public void validate_expected_response(DataTable expected) throws PetException {
		List<Map<String, String>> jsonschema = expected.asMaps(String.class, String.class);
		for (Map<String, String> expecteds : jsonschema) {
			validateExpectedJson(expecteds.get("expectedResponse"));
		}
	}

	@Then("^validate expected response is empty$")
	public void validate_expected_response() {
		Assert.assertEquals(response.getBody().asString(), "");
	}

	public PetStepDef() {
		pet = new Pet(getRandom(), getRandomStatus(status));
	}

}
