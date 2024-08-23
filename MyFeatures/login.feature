Feature: Login Functionality

  Scenario: User can login successfully
    Given "tasneem" is not logged in
    When "tasneem" tries to login with role "user"
    And password is "123456"
    Then "tasneem" login succeeds

  Scenario: User cannot login with incorrect password
    Given "tasneem" is not logged in
    When "tasneem" tries to login with role "user"
    And password is "wrongpassword"
    Then "tasneem" login fails

  Scenario: Admin can login successfully
    Given "nareman" is not logged in
    When "nareman" tries to login with role "admin"
    And password is "654321"
    Then "nareman" login succeeds

  Scenario: Admin cannot login with incorrect password
    Given "nareman" is not logged in
    When "nareman" tries to login with role "admin"
    And password is "wrongpassword"
    Then "nareman" login fails

  Scenario: Owner can login successfully
    Given "masa" is not logged in
    When "masa" tries to login with role "owner"
    And password is "9999"
    Then "masa" login succeeds

  Scenario: Owner cannot login with incorrect password
    Given "masa" is not logged in
    When "masa" tries to login with role "owner"
    And password is "wrongpassword"
    Then "masa" login fails

  Scenario: Supplier can login successfully
    Given "nuha" is not logged in
    When "nuha" tries to login with role "supplier"
    And password is "9876"
    Then "nuha" login succeeds

  Scenario: Supplier cannot login with incorrect password
    Given "nuha" is not logged in
    When "nuha" tries to login with role "supplier"
    And password is "wrongpassword"
    Then "nuha" login fails
