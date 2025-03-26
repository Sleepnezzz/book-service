Feature: [handle something] Book Management API

  Scenario: Retrieve books by author
    Given books exist in the database for author "J.R.R. Tolkien"
    When I send a GET request to "/books?author=J.R.R. Tolkien"
    Then the response should contain books for "J.R.R. Tolkien"

  Scenario: Retrieve books for a non-existent author
    Given books Not found in the database for author "Khun Unknow"
    When I attempt to fetch books from "/books?author=Khun Unknow"
    Then the response should return empty books