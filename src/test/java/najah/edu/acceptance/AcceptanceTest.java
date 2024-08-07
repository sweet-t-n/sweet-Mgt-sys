package najah.edu.acceptance;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)

@CucumberOptions(features="MyFeatures" ,
plugin= {"html:target/cucumber/report.html"},
monochrome=true,
snippets=SnippetType.CAMELCASE,
 glue={"najah.edu.acceptance"})




public class AcceptanceTest{
	
	
}

