# Telecom Provider Test

## Overview
This project provides a simple implementations of a restful API for a telecom provider.

This new API provides methods for reading and storing phone numbers associated to customers ( 1 customer : N phone numbers ).

This API has just three endpoints:
* get all phone numbers
* get all phone numbers of a single customer
* activate a phone number

## The Design
This solution is implemented in **Java** using the framework **Spring Boot** 2.1.1.RELEASE and the database **h2**.

To run the application you need:
* jdk >=1.8
* maven >= 3.6.0    

## How to run the tests
The application has been developed using a **TDD** approach and so there are both *JUnit* and *Integration* tests.

To start the tests run the command below from the root of the project. 

```bash
mvn test
```

## How to run the application
To start the application run the command below from the root of the project. This will start the rest server. 

To actually use the application you can use any http client. I suggest you to use *Postman*.

```bash
mvn spring-boot:run
```

### How to call the API

#### Activate a phone number

Call the `/phonenumbers` endpoint using the **POST** verb to activate a new phone number.

##### API endpoint

```javascript
POST	http://localhost:8080/phonenumbers

body
{ "number": "the_phone_number",
	"customerId": "the_customer_id"
}

Status code: 201
```

##### API example

```javascript
POST	http://localhost:8080/phonenumbers
{"number":"11112222","customerId":"10"}
Status code: 201

POST	http://localhost:8080/phonenumbers
{"number":"33334444","customerId":"20"}
Status code: 201

POST	http://localhost:8080/phonenumbers
{"number":"55556666","customerId":"20"}
Status code: 201
```

#### Get all phone numbers

Call the `/phonenumbers` endpoint using the **GET** verb to read all the saved phone numbers.

##### API endpoint
```javascript
GET		http://localhost:8080/phonenumbers
Status code: 200
```

Field | Description
------|------------
**id** | The phonenumber's unique id
number | The phone number as a string
customerId | The id of the customer as a number

##### API example

```javascript
GET		http://localhost:8080/phonenumbers

[
    {
        "id": 1,
        "number": "11112222",
        "customerId": 10
    },
    {
        "id": 2,
        "number": "33334444",
        "customerId": 20
    },
    {
        "id": 3,
        "number": "55556666",
        "customerId": 20
    }
]

Status code: 200
```

#### Get all phone numbers of a single customer

Call the **/phonenumbers** endpoint using the **GET** verb and passing the _customerId_ param to read all the saved phone numbers of a single customer. 

##### API endpoint
```javascript
GET		http://localhost:8080/phonenumbers?customerId={customer_id}

Status code: 200
```


##### API example

The Customer 1 has no phone numbers. You will receive a 404 response. 

```javascript
GET		http://localhost:8080/phonenumbers?customerId=1

Status code: 404
```

The Customer 10 has one phone number. You will receive his phone number.


```javascript
GET		http://localhost:8080/phonenumbers?customerId=10

[
    {
        "id": 1,
        "number": "11112222",
        "customerId": 10
    }
]

Status code: 200
```

The Customer 20 has two phone numbers. You will receive his phone numbers.


```javascript
GET		http://localhost:8080/phonenumbers?customerId=20

[
    {
        "id": 2,
        "number": "33334444",
        "customerId": 20
    },
    {
        "id": 3,
        "number": "55556666",
        "customerId": 20
    }
]

Status code: 200 
```

## What's next

- Handling of activation of duplicate phone numbers
- Hide the id field when a phone number is returned
- Handling of large amount of data using pagination or streaming
