Feature: EVERON - PET CRUD 

@smoke
Scenario: Test_001 - Get Pet Details
	Given user set param /pet/9870
	When user make GET call 
	Then validate the status code as 200 
	And validate json schema 
		|jsonSchemaValue|
		|Pet/schemas/pet|
	And validate expected response 
		|expectedResponse	|
		|Pet/getPetExpected	|
		
@smoke
Scenario: Test_002 - Create New Pet
	Given user set param /pet 
	And user set the request body with Dyanmic values in resquest body and expected response 
		|RequestBody  |id	 |status	  |expectedResponse|
		|Pet/petDetail|random|randomStatus|Pet/createPetExpected| 
	When user make POST call 
	Then validate the status code as 200 
	And validate json schema 
		|jsonSchemaValue|
		|Pet/schemas/pet|
	And validate response 
		|id		 |status  |Category,name|
		|Existing|Existing|automation	|
	And validate expected response 
		|expectedResponse	  |
		|Pet/createPetExpected|
		
@smoke
Scenario: Test_003 - Update existing Pet
	Update and Get the pet details
	Given user set param /pet 
	And user set the request body with Dyanmic values in resquest body and expected response 
		|RequestBody  			|id	   |status	    |expectedResponse|
		|Pet/createPetExpected	|random|randomStatus|Pet/UpdatePetExpected| 
	When user make PUT call 
	Then validate the status code as 200 
	And validate json schema 
		|jsonSchemaValue|
		|Pet/schemas/pet|
	And validate response 
		|id		 |status  |Category,name|
		|Existing|Existing|automation	|
	And validate expected response 
		|expectedResponse	  |
		|Pet/UpdatePetExpected|
	Given user set param /pet/ExistingID 
	When user make GET call 
	Then validate the status code as 200 
	And validate json schema 
		|jsonSchemaValue|
		|Pet/schemas/pet|
	And validate expected response 
		|expectedResponse	|
		|Pet/UpdatePetExpected|	
		
@smoke		
Scenario: Test_004 - Create & Delete Pet details
	Given user set param /pet 
	And user set the request body with Dyanmic values in resquest body and expected response 
		|RequestBody  |id	 |status	  |expectedResponse|
		|Pet/petDetail|random|randomStatus|Pet/createPetExpected| 
	When user make POST call 
	Then validate the status code as 200 
	And validate json schema 
		|jsonSchemaValue|
		|Pet/schemas/pet|
	And validate response 
		|id		 |status  |Category,name|
		|Existing|Existing|automation	|
	And validate expected response 
		|expectedResponse	  |
		|Pet/createPetExpected|
	Given user set param /pet/ExistingID 
	When user make DELETE call 
	Then validate the status code as 200
	
@smoke
Scenario: Test_005 - verify Error JSON for Pet Not Found
	Given user set param /pet/000080010100101 
	When user make GET call 
	Then validate the status code as 404 
	And validate json schema 
		|jsonSchemaValue|
		|Pet/schemas/noPetFound|
	And validate error response
		|message|
		|Pet not found|
	Then validate expected response 
		|expectedResponse|
		|Pet/notPetFound |	 

	