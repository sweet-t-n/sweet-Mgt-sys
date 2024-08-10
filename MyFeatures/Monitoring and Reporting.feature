Feature: Monitoring and Reporting

Scenario: Admin monitors profits and generates financial reports
    When I monitor the profits for all stores
    Then I should see a financial report generated for each store

  Scenario: Admin identifies best-selling products in each store
    When I view the best-selling products in each store
    Then I should see a list of best-selling products for each store

  Scenario: Admin gathers and displays statistics on registered users by city
    When I gather statistics on registered users by city
    Then I should see the statistics displayed for each city including Nablus and Jenin