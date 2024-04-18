package SelfStudyAcademy.SeleniumFrameworkDesign;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import SelfStudyAcademy.SeleniumFrameworkDesign.pageobjects.CartPage;
import SelfStudyAcademy.SeleniumFrameworkDesign.pageobjects.ProductCatalogue;
import SelfStudyAcademy.TestComponents.BaseTest;
import SelfStudyAcademy.TestComponents.Retry;

public class ErrorValidationsTest extends BaseTest{
	
	
	@Test(groups= {"ErrorHandling"}, retryAnalyzer = Retry.class)
	public void failedLogin() throws InterruptedException, IOException {
		
		landingPage.loginApplication("davidVelez93@gmail.com", "david");
		String errorMessage = landingPage.getErrorMessage();
		Assert.assertEquals("Incorrect email or password.", errorMessage);
	}
	
	@Test
	public void FailedOrder() throws InterruptedException, IOException {
		
		String productName = "ZARA COAT 3";
		
		ProductCatalogue productCatalogue = landingPage.loginApplication("davidVelez93@gmail.com", "davidVelez93");
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();
		
		Boolean match = cartPage.verifyProductDisplayed("ZARA COAT 4");
		Assert.assertFalse(match);

	}

}