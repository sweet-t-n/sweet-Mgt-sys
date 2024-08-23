Feature: User Sign Up

  Scenario: Successful sign up
    Given that the user "nareman" is not signed up
    When the user enters a username "nareman", password "98765", email "nareman@gmail.com", and country "ramallah"
    Then the sign up succeeds

  Scenario: Sign up fails due to existing username
    Given that the user "tasneem" is signed up
    When the user enters a username "tasneem", password "password", email "tasneem@example.com", and country "ExistingCountry"
    Then the sign up fails

  Scenario: User redirected to login page on successful sign up
    Given that the user "redirectUser" is not signed up
    When the user enters a username "redirectUser", password "redirectPassword", email "redirectUser@example.com", and country "RedirectCountry"
    Then the sign up succeeds
    And the user is redirected to the login page

  Scenario: User prompted to try again on failed sign up
    Given that the user "tasneem" is signed up
    When the user enters a username "tasneem", password "wrongPassword", email "tasneem@example.com", and country "WrongCountry"
    Then the user is prompted to try again
