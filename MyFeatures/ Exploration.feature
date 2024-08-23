Feature: Exploration and Purchase of Desserts

  Scenario: Browse and search for dessert recipes
    Given the user is on the dessert recipes page
    When the user searches for "chocolate"
    Then the user should see a list of desserts related to "chocolate"

  Scenario: Filter recipes based on dietary needs
    Given the user is on the dessert recipes page
    When the user filters recipes by "gluten-free"
    Then the user should see only gluten-free desserts

  Scenario: Filter recipes based on food allergies
    Given the user is on the dessert recipes page
    When the user filters recipes by "nut-free"
    Then the user should see only nut-free desserts
 
  Scenario: Purchase desserts directly from store owners
    Given the user is on the dessert details page
    And the user has selected a dessert
    When the user clicks "Purchase"
    Then the user should be directed to the checkout page
    And the purchase should be confirmed with a success message