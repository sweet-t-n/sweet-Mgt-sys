Feature: Sign up

Scenario: The sign up is successful
  Given that the user "nonexistent" is not sign up
  And they do not have an account in the system
  When the user enters a username "nonexistent" and password "password123"
  Then the sign up succeeds
  And the user is redirected to the login page

Scenario: The sign up is unsuccessful
  Given that the user "tasneem" is not sign up
  And they do have an account in the system
  When the user enters a username "tasneem" and password "password123"
  Then the sign up fails
  And the user is prompted to try again
