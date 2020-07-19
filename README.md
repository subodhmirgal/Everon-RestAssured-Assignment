# RestAssured-PetStore

This project developed using Rest-assured with TestNGCucumber runner.

Use maven to build project and run tests.
 
      mvn -D"cucumber.options=--tags @all" test verify -Denv.baseURL="https://petstore.swagger.io/v2"
        use tags in cucumber options. (for ex: @all - run all the test, @PETCreate - create the pet)
        baseURL - base endpoint

It support testng as well. User can run with testng runner using testng.xml
 
Report: The project build with BDD Cucumber. Cucumber report found in the below directory.
  
    ${project.build.directory}/cucumber-reports/advanced-reports/cucumber-html-report/overview-features.html
