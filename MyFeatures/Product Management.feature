Feature: Product Management

  Scenario: Add New Product
    Given the store owner or supplier is signed in
    When they add a new product with name "Chocolate Cake", description "Delicious homemade chocolate cake", and price "20"
    Then the product should be added successfully
    And a confirmation message should be displayed

  Scenario: Update Existing Product
    Given the store owner or supplier is signed in and has an existing product
    When they update the product with name "Chocolate Cake" to "Vegan Chocolate Cake" and change the price to "25"
    Then the product details should be updated successfully
    And a confirmation message should be displayed

  Scenario: Remove Product
    Given the store owner or supplier is signed in and has an existing product
    When they remove the product with name "Chocolate Cake"
    Then the product should be removed successfully
    And a confirmation message should be displayed

  Scenario: Monitor Sales and Profits
    Given the store owner or supplier is signed in
    When they view the sales and profits report
    Then the report should display total sales and profits
    And a breakdown of sales per product

  Scenario: Identify Best-Selling Products
    Given the store owner or supplier is signed in
    When they view the best-selling products report
    Then the report should display a list of best-selling products
    And their respective sales figures

  Scenario: Implement Dynamic Discounts
    Given the store owner or supplier is signed in
    When they apply a dynamic discount to a product with name "Chocolate Cake" and discount "10%"
    Then the discount should be applied successfully
    And the new price should reflect the discount
    And a confirmation message should be displayed