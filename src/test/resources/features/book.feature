Feature: Book Management API

  Scenario: Create a new book
    Given a book with title "The Hobbit", author "J.R.R. Tolkien", and a valid published date
    When I send a POST request to "/books"
    Then the response status should be 201
    And the response should contain "The Hobbit"

  Scenario: Retrieve books by author
    Given books exist in the database for author "J.R.R. Tolkien"
    When I send a GET request to "/books?author=J.R.R. Tolkien"
    Then the response should contain books for "J.R.R. Tolkien"