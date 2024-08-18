Feature: Communication and Notification
  As a store owner or supplier
  I want to communicate with users and other suppliers using the messaging system
  So that I can manage communication effectively

  Scenario: Send a message to a user
    Given the store owner or supplier is signed in
    When they choose to send a message to the user "user123"
    And they enter the message "Your order has been shipped"
    And they send the message
    Then the message should be sent successfully
    And a confirmation should be displayed

  Scenario: Receive a message from a user
    Given the store owner or supplier is signed in
    When they receive a new message from "user123"
    Then they should see the message "Can I change the delivery date?"
    And the message should be marked as read

  Scenario: Send a message to another supplier
    Given the store owner or supplier is signed in
    When they choose to send a message to the supplier "supplier456"
    And they enter the message "Let's discuss the next shipment"
    And they send the message
    Then the message should be sent successfully
    And a confirmation should be displayed

  Scenario: Receive a message from another supplier
    Given the store owner or supplier is signed in
    When they receive a new message from "supplier456"
    Then they should see the message "Shipment details are confirmed"
    And the message should be marked as read