Feature: Book Management API

  Scenario: Create a new book
    Given a book with title "The Hobbit", author "J.R.R. Tolkien", and a valid published date
    When I send a POST request to "/books"
    Then the response status should be 201
    And the response should contain "The Hobbit"

  Scenario: Create a book with invalid data
    Given I have the following invalid book details:
      | title | author | published_date |
      |       |        | invalid-date   |
    When I send a POST request to "/books" with the invalid book details
    Then the response status code should be 400
    And the response should contain error messages