Feature: User Management

  Scenario: Admin creates a new store owner account
    Given I am logged in as an admin
    When I create a new user account with the role "Store Owner"
    Then the user account should be successfully created
    And I should see the user in the list of store owners

  Scenario: Admin edits a raw material supplier account
    Given I am logged in as an admin
    And a user with the role "Raw Material Supplier" exists
    When I edit the user's details
    Then the user's details should be successfully updated
    And I should see the updated details in the user list

  Scenario: Admin deletes a store owner account
    Given I am logged in as an admin
    And a user with the role "Store Owner" exists
    When I delete the user account
    Then the user account should be successfully deleted
    And the user should no longer appear in the list of store owners