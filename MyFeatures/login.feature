 Feature: login
 
Scenario: valid login
Given I am not in system
When set username "tasneem" and pass "12345"
Then login succeed

Scenario: invalid user name 
Given I am not in system
When set innalid username "tasneemm" and pass "12345"
Then login failed