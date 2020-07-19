/**
 * @author Subodh M
 */
package MyRunner;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class FailListener extends TestListenerAdapter {
	@Override
	public void onTestSkipped(ITestResult tr) {
		tr.setStatus(ITestResult.FAILURE);
	}
}