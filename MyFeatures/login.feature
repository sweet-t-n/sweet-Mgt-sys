Feature: Login

  Scenario: user login
    Given that the user "tasneem" is not logged in
    When user tries to login
    And username is "tasneem" and password is "123456"
    Then the user login succeeds
    And the user is logged in

  Scenario: user cannot login
    Given that the user "tasneem" is not logged in
    When user tries to login
    And username is "tasneem" and password is "wrongpassword"
    Then the user login fails
    And the user is not logged in

  Scenario: admin can login
    Given that the admin "nareman" is not logged in
    When admin tries to login
    And username is "nareman" and password is "654321"
    Then the admin login succeeds
    And the admin is logged in

  Scenario: admin cannot login
    Given that the admin "nareman" is not logged in
    When admin tries to login
    And username is "nareman" and password is "wrongpassword"
    Then the admin login fails
    And the admin is not logged in

  Scenario: owner can login
    Given that the owner "masa" is not logged in
    When owner tries to login
    And username is "masa" and password is "9999"
    Then the owner login succeeds
    And the owner is logged in

  Scenario: owner cannot login
    Given that the owner "masa" is not logged in
    When owner tries to login
    And username is "masa" and password is "wrongpassword"
    Then the owner login fails
    And the owner is not logged in

  Scenario: supplier can login
    Given that the supplier "shahed" is not logged in
    When supplier tries to login
    And username is "shahed" and password is "9876"
    Then the supplier login succeeds
    And the supplier is logged in

  Scenario: supplier cannot login
    Given that the supplier "shahed" is not logged in
    When supplier tries to login
    And username is "shahed" and password is "wrongpassword"
    Then the supplier login fails
    And the supplier is not logged in
