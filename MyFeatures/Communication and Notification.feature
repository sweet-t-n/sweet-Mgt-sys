Feature: Communication and Notification

  Scenario: Send and receive a message
    When they choose to send a message to the user "user1"
    And they enter the message "Shipment details are confirmed"
    And they send the message
    Then the message should be sent successfully
    And they should see the message "Shipment details are confirmed"

  Scenario: Check message sent confirmation
    When they choose to send a message to the user "user1"
    And they enter the message "Hello User1"
    And they send the message
    Then a confirmation should be displayed

  Scenario: Mark message as read
    When they choose to send a message to the user "user1"
    And they enter the message "Shipment details are confirmed"
    And they send the message
    And they receive a new message from "user1"
    Then the message should be marked as read
