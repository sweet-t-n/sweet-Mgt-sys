
Feature: login

Actor: tasneem 
 
Scenario: valid login
Given I am not in system
When set username "tasneem" and pass "12345"
Then login succeed
And welcome message will be appeared

Scenario: invalid user name 
Given I am not in system
When set innalid username "tasneemm" and pass "12345"
Then user is now out of system 
And  failed ligin message will be appeared
