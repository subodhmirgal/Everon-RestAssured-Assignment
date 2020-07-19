/***
 * author : Subodh M
 * This is created for Everon Test
 */

package MyRunner;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.PickleEventWrapper;
import utils.ComonServiceLib;

@CucumberOptions(features = "src/test/java/Features", strict = true, glue = { "stepDefinitions" }, 
tags = {"@smoke" }, 
plugin = { "pretty", "html:target/cucumber-reports/cucumber-pretty" })
@Listeners({ FailListener.class })
public class TestRunner extends ComonServiceLib {

	@Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
	public void RunScenario(PickleEventWrapper pickleWrapper, CucumberFeatureWrapper featureWrapper) throws Throwable {
		testNGCucumberRunner.runScenario(pickleWrapper.getPickleEvent());

	}

	@DataProvider
	public Object[][] scenarios() {
		return testNGCucumberRunner.provideScenarios();

	}

}