package com.example.book.cucumber.steps

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["classpath:features"], // Path to the feature file
    glue = ["com.example.book"], // Path to the step definition package
    plugin = ["pretty", "html:build/reports/cucumber.html", "json:build/reports/cucumber.json"]
)
class CucumberIntegrationTest
