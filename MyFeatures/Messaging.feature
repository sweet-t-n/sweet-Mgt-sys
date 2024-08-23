Feature: Messaging

  Scenario: Sending a message
    Given they are on the messaging page
    When they send the message
    Then the message should be sent successfully
    And they should see the message "Message sent"
