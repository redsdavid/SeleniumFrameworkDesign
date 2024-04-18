
@tag
Feature: Purchase the order from ecommerce website
	I want to use this template for my feature file
	
	Background:
	Given I landed on Ecommerce Page

	@Regression
	Scenario Outline: Positive Test for Submitting the order
		Given Logged in with username <name> and password <password>
		When I add a product <productName> from Cart
		And Checkout <productName> and submit the order
		Then "THANKYOU FOR THE ORDER." message is displayed on ConfirmationPage 
		
		Examples:
		| name         					| password 				| productName 		|
		|davidVelez93@gmail.com	| davidVelez93		| ZARA COAT 3			|
		