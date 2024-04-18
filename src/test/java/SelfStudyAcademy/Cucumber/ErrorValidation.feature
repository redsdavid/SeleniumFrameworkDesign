@tag
Feature: Error Validation
  I want to use this template for my feature file

  @Regression
  Scenario Outline: Show error message when bad credentials
    Given I landed on Ecommerce Page
    When Logged in with username <name> and password <password>
    Then "Incorrect email or password." message is displayed

    Examples:
		| name         					  | password 		 	| 
		| davidVelez93@gmail.com	| davidVelez9		|
