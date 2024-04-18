package SelfStudyAcademy.StepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import SelfStudyAcademy.SeleniumFrameworkDesign.pageobjects.CartPage;
import SelfStudyAcademy.SeleniumFrameworkDesign.pageobjects.CheckoutPage;
import SelfStudyAcademy.SeleniumFrameworkDesign.pageobjects.ConfirmationPage;
import SelfStudyAcademy.SeleniumFrameworkDesign.pageobjects.LandingPage;
import SelfStudyAcademy.SeleniumFrameworkDesign.pageobjects.ProductCatalogue;
import SelfStudyAcademy.TestComponents.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionImpl extends BaseTest{
	
	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public ConfirmationPage confirmationPage;
	
	@Given("I landed on Ecommerce Page")
	public void I_landed_on_ecommerce_page() throws IOException {
		landingPage = launchApplication();
	}
	
	@Given("^Logged in with username (.+) and password (.+)$")
	public void Logged_in_with_username_and_password(String username, String password) {
		productCatalogue = landingPage.loginApplication(username, password);
	}
	
	@When("^I add a product (.+) from Cart$")
	public void I_add_a_product_from_cart(String productName) throws InterruptedException {
		List<WebElement> products = productCatalogue.getProductsList();
		productCatalogue.addProductToCart(productName);
	}
	
	@And("^Checkout (.+) and submit the order$")
	public void Checkout_and_submit_the_order(String productName) {
		
		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.verifyProductDisplayed(productName);
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("Colombia");
		confirmationPage = checkoutPage.submitOrder();
	}
	
	@Then("{string} message is displayed on ConfirmationPage")
	public void message_is_displayed_on_ConfirmationPage(String message) {
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase(message));
		driver.quit();
	}
	
	@Then("{string} message is displayed")
	public void message_is_displayed(String message) {
		String errorMessage = landingPage.getErrorMessage();
		Assert.assertEquals(message, errorMessage);
		driver.quit();
	}

}
