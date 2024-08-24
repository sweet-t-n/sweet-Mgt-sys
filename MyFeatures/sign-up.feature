Feature: Sign up

Scenario: The sign up is successful
Given that the user "nonexistent" is not signed up
And they do not have an account in the system
When the user enters a username "nonexistent" and password "password123"
Then the sign up succeeds
And the user is redirected to the login page
And the account is saved in the system
  

Scenario: The sign up is unsuccessful
Given that the user "shahd" is not signed up
And they already have an account in the system
When the user enters a username "shahd" and password "password123"
Then the sign up fails
And the user is prompted to try again
And the account is not saved in the system
  