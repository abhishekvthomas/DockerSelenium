package dockerProject;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class SetupDockerGrid {
	
	@BeforeSuite
	public void beforeSuite() throws Exception {
		Runtime.getRuntime().exec("cmd /c start start_docker.bat");
		Thread.sleep(35000);
	}
	
	@AfterSuite(alwaysRun = true)
	public void afterSuite() throws Exception {
		Runtime.getRuntime().exec("cmd /c start stop_docker.bat");
		Thread.sleep(5000);
		
		Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
	}

}
