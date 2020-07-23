package model;

import java.io.File;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;

import utils.BaseTest;
import utils.PetException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;

/***
 * author : Subodh M This is created for Everon Test
 */

public class Pet {

	Gson gson;
	String cwd = System.getProperty("user.dir");
	PetResponse resp;
	PetNotFound notFound;
	BaseTest base;
	int id;
	String status;
	Pet pet;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param name the name to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/***
	 * 
	 * @return
	 */
	public PetResponse getPetResponse(Response response) {
		return resp = response.as(PetResponse.class, ObjectMapperType.GSON);
	}

	public PetNotFound getPetNotFound(Response response) {
		return notFound = response.as(PetNotFound.class, ObjectMapperType.GSON);
	}

	/**
	 * 
	 * @param jsnValue
	 * @throws PetException
	 * @throws Exception
	 */
//Set the Random Values for ID,status fields - Request Body and Expected Response
	public void setRequestBodyAndExpectedResponse(Map<String, String> jsnValue, int randomID, String randomStatus)
			throws PetException {
		try {
			File requestBodyFile = new File(cwd + "/RequestBody/" + jsnValue.get("RequestBody") + ".json");
			File expectedJson = new File(cwd + "/ExpectedResponseJson/" + jsnValue.get("expectedResponse") + ".json");

			String jsonString = FileUtils.readFileToString(requestBodyFile);
			JsonElement jelement = new JsonParser().parse(jsonString);
			JsonObject jobject = jelement.getAsJsonObject();
			for (Map.Entry<String, String> entry : jsnValue.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				if (value.equalsIgnoreCase("random")) {
					jobject.addProperty(key, randomID);
				} else if (value.equalsIgnoreCase("randomStatus")) {
					jobject.addProperty(key, status);
				}
			}
			String requestBodyJson = gson.toJson(jelement);
			String expectedJsonValue = gson.toJson(jelement);
			FileUtils.writeStringToFile(requestBodyFile, requestBodyJson);
			FileUtils.writeStringToFile(expectedJson, expectedJsonValue);
		} catch (Exception e) {
			throw new PetException("Set Request Body and Expected Response " + e.getMessage());
		}
	}

	/**
	 * 
	 * @param responseJson
	 * @param response
	 * @throws Exception
	 */
	public void validateResponse(Map<String, String> responseJson, Response response) {
		resp = getPetResponse(response);
		System.out.println(resp.toString());
		// System.out.println(resp.toString());
		Category category = resp.getCategory();

		for (Map.Entry<String, String> entry : responseJson.entrySet()) {
			// Validation of category Name

			if (entry.getKey().equalsIgnoreCase("Category,name")) {
				Assert.assertEquals(category.getName(), entry.getValue());
			} else if (entry.getKey().equalsIgnoreCase("id") && entry.getValue().equalsIgnoreCase("Existing")) {
				Assert.assertEquals(getId(), resp.getId());
			} else if (entry.getKey().equalsIgnoreCase("status") && entry.getValue().equalsIgnoreCase("Existing")) {
				Assert.assertEquals(getStatus(), resp.getStatus());
			} else {
				Assert.fail("Given key value is not found. Please check the feature file datatable");
			}

		}
	}

	public void validateErrResponse(Map<String, String> responseJson, Response response) {
		notFound = getPetNotFound(response);
		for (Map.Entry<String, String> entry : responseJson.entrySet()) {
			if (entry.getKey().equalsIgnoreCase("message")) {
				Assert.assertEquals(entry.getValue(), notFound.getMessage());
			} else {
				Assert.fail("Given key value is not found. Please check the feature file datatable");
			}
		}

	}

	public Pet(int id, String status) {
		this.id = id;
		this.status = status;
		base = new BaseTest();
		resp = new PetResponse();
		gson = new Gson();
	}

}
