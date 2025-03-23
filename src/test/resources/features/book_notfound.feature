Feature: [handle something] Book Management API

  Scenario: Retrieve books for a non-existent author
    Given books Not found in the database for author "Khun Unknow"
    When I attempt to fetch books from "/books?author=Khun Unknow"
    Then the response should return empty books