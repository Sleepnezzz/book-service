package com.example.book.cucumber

import com.example.book.BookServiceApplication
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.cucumber.spring.CucumberContextConfiguration
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["classpath:features"], // Path to the feature file
    glue = ["com.example.book.cucumber.steps"], // Path to the step definition package
    plugin = ["pretty", "html:build/reports/cucumber.html", "json:build/reports/cucumber.json"]
)
@CucumberContextConfiguration
@SpringBootTest(classes = [BookServiceApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CucumberIntegrationTest
