package SelfStudyAcademy.Cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/java/SelfStudyAcademy/Cucumber", 
glue="SelfStudyAcademy.StepDefinitions",
tags = "@Regression",
monochrome = true, plugin = {"html:target/cucumber.html"})
public class TestNGTestRunner extends AbstractTestNGCucumberTests{
	
}
