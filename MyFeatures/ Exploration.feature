Feature: Dessert Recipes and Purchase

  Scenario: Search for desserts and apply filters
    Given the user is on the dessert recipes page
    When the user searches for "chocolate"
    Then the user should see a list of desserts related to "chocolate"
    When the user filters recipes by "gluten-free"
    Then the user should see only gluten-free desserts
    When the user filters recipes by "nut-free"
    Then the user should see only nut-free desserts

  Scenario: Purchase a dessert
    Given the user is on the dessert details page
    And the user has selected a dessert
    When the user clicks "Purchase"
    Then the user should be directed to the checkout page
    And the purchase should be confirmed with a success message
