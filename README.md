# Shopping Cart Service

Shopping Cart is responsible for:
- Add item to cart
- Update item in a cart
- Delete item from cart

Shopping Cart Service is built on Spring.

- Spring Boot
- Spring Cloud
- Liquibase for Database Migrations
- Junit
- Mockito
- Jacoco Code Coverage 

Integration with Travic CI for continuous integration.

[![Build Status](https://travis-ci.org/haighis/shoppingcart-service.svg?branch=master)](https://travis-ci.org/haighis/shoppingcart-service)

## Usage
What can I do with Shopping Cart Service? You can use it to add/update/delete a cart item that are accessible via REST POST/PUT/DELETE with JSON content.

## Running

./gradlew bootRun

## Viewing the API

With Netflix Eureka + Swagger a self documenting API will demonstrate API Verbs available

## Tests

In memory tests with Mockito for OrderServiceImpl and OrderResource API.  

./gradlew test

To view pass/fail for unit tests there is a report located at 'build/reports/tests/index.html'

## Test Code Coverage

./gradlew test jacocoTestReport

Jacoco Code Coverage Report is located at 'build/reports/coverage/index.html'