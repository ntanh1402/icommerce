# Introduction
icommerce is simple ecommerce system with profile, product, order, payment and audit service.
The example here is only profile service and product service with only a few API.
The example is also following SOLID, DRY, SRP, KISS, ... priciples and apply Builder pattern, Factory method pattern.
The example flow:
1. Users can search products by name, brands, colours or price.
2. Logined user shopping cart can be stored in server side and synchronize with other clients.

The example uses Java 8, Spring Boot, JPA/Hibernate, JUnit, Kafka, JWT.

# Design
1. System design image: `icommerce-system-design.jpg`

# Install Service Common library
1. Open `service-common` folder
2. Run `mvn clean install`

# Run Profile Service
1. Open `profile-service` folder
2. Run `mvn clean test spring-boot:run`
3. `Profile Service` run on port 8000
 
# Run Product Service
1. Open `product-service` folder
2. Run `mvn clean test spring-boot:run`
3. `Product Service` run on port 8001
 
# CURL commands for Profile Service
1. Test `login` API
```
curl --location --request POST 'http://localhost:8000/users/loginByToken' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--data-raw '{
	"type": "FACEBOOK",
	"token": "accessToken"
}'
```
2. Test `verify token` api
```
curl --location --request POST 'http://localhost:8000/users/verifyToken' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--data-raw '{
	"userId": 1,
	"token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJpY29tbWVyY2UiLCJleHAiOjE1OTQ2NDk0NTMsInVzZXJJZCI6MX0.ZSv8n7bs6w3gDDTeK2HW87vA28ntKpXL82W5P88LuiI"
}'
```

# CURL commands for Product Service
1. Test `Search Product` API
```
curl --location --request GET 'http://localhost:8001/products/search?name=boost1&priceFrom=20&brands=adidas' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--data-raw '{
	"type": "FACEBOOK",
	"token": "accessToken"
}'
```
2. Test `Save User Cart ` API
```
curl --location --request POST 'http://localhost:8001/users/1/cart' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJpY29tbWVyY2UiLCJleHAiOjE1OTQ2NDk0NTMsInVzZXJJZCI6MX0.ZSv8n7bs6w3gDDTeK2HW87vA28ntKpXL82W5P88LuiI' \
--data-raw '{
	"products": [
		{
            "productId": 1,
            "name": "ultraboost",
            "sku": "a-u-r",
            "brand": "adidas",
            "colour": "red",
            "price": 120.0
        }
	]
}'
```
3. Test `Load User Cart ` API
```
curl --location --request GET 'http://localhost:8001/users/1/cart' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJpY29tbWVyY2UiLCJleHAiOjE1OTQ2NDk0NTMsInVzZXJJZCI6MX0.ZSv8n7bs6w3gDDTeK2HW87vA28ntKpXL82W5P88LuiI' \
--data-raw ''
```




