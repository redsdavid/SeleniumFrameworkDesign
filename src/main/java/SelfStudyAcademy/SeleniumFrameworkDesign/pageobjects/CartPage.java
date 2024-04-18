package SelfStudyAcademy.SeleniumFrameworkDesign.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent{
	
	WebDriver driver;
	
	public CartPage(WebDriver driver) {
		
		super(driver);
		this.driver = driver;
		
		//Initialize the elements below
		PageFactory.initElements(driver, this);
	}
	
	//using PageFactory:
			@FindBy(css=".totalRow button")
			WebElement checkoutElement;
			
			@FindBy(css=".cartSection h3")
			private List <WebElement> cartProducts;
	
	public Boolean verifyProductDisplayed(String productName) {
		Boolean match = cartProducts.stream().anyMatch(product ->
		product.getText().equalsIgnoreCase(productName));
		return match;
	}
	
	public CheckoutPage goToCheckout() {
		checkoutElement.click();
		return new CheckoutPage(driver);
	}
	
	

}
