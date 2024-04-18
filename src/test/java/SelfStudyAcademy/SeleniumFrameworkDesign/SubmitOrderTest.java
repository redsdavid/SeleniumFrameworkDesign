package SelfStudyAcademy.SeleniumFrameworkDesign;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import AbstractComponents.OrderPage;
import SelfStudyAcademy.SeleniumFrameworkDesign.pageobjects.CartPage;
import SelfStudyAcademy.SeleniumFrameworkDesign.pageobjects.CheckoutPage;
import SelfStudyAcademy.SeleniumFrameworkDesign.pageobjects.ConfirmationPage;
import SelfStudyAcademy.SeleniumFrameworkDesign.pageobjects.ProductCatalogue;
import SelfStudyAcademy.TestComponents.BaseTest;

public class SubmitOrderTest extends BaseTest{
	
	String userDir = System.getProperty("user.dir");
	
	//String productName = "ZARA COAT 3";
	
	@Test(dataProvider="getData", groups= {"Purchase"})
	public void submitOder(HashMap<String, String> info) throws InterruptedException, IOException {
		
		ProductCatalogue productCatalogue = landingPage.loginApplication(info.get("email"), info.get("password"));
		
		List<WebElement> products = productCatalogue.getProductsList();
		productCatalogue.addProductToCart(info.get("productName"));
		CartPage cartPage = productCatalogue.goToCartPage();
		
		Boolean match = cartPage.verifyProductDisplayed(info.get("productName"));
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("Colombia");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	}
	
	@Test(dependsOnMethods = {"submitOder"})
	public void orderHistory() {
		String productName = "ZARA COAT 3";
		ProductCatalogue productCatalogue = landingPage.loginApplication("davidVelez93@gmail.com", "davidVelez93");
		OrderPage orderPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(orderPage.verifyOrderDisplayed(productName));
	}
	
	
	@DataProvider
	public Object[][] getData() throws IOException {
		List<HashMap<String, String>> data = getJsonDataToMap(userDir + "\\src\\test\\java\\SelfStudyAcademy\\SeleniumFrameworkDesign\\data\\PurchaseOrder.json");
		return new Object[][]  {{data.get(0)},{data.get(1)}};

//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("email", "davidVelez93@gmail.com");
//		map.put("password", "davidVelez93");
//		map.put("productName", "ZARA COAT 3");
//		
//		HashMap<String, String> map1 = new HashMap<String, String>();
//		map.put("email", "davidVelez93@gmail.com");
//		map.put("password", "davidVelez93");
//		map.put("productName", "ADIDAS ORIGINAL");
//		
//		return new Object[][]  {{map},{map1}};
		
//		return new Object[][] {{"davidVelez93@gmail.com", "davidVelez93", "ZARA COAT 3"}, 
//			{"davidVelez93@gmail.com", "davidVelez93", "ADIDAS ORIGINAL"}};
	}

}