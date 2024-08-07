Feature: Expenses Management

  Scenario: Create a new expense
    Given the application is running
    When the user creates a new expense with the following details
      | title     | description         | amount | currency |
      | śniadanie | zakupy na śniadanie | 40     | PLN      |
    Then the expense should be created successfully

  Scenario: List expenses
    Given the application is running
    When the user lists all expenses
    Then the response should contain the following expenses
      | id | title     | description         | amount | currency |
      | 1  | śniadanie | zakupy na śniadanie | 40     | PLN      |
