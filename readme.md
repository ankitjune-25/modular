
> **Prerequisite**
> 1. Java8 or above should have installed.
> 2. Maven should have installed.
> 3. Docker should have installed, UP and RUNNING.

> Make sure port 8080, 5432, 5672 and 15672 should be be free not acquired by other process.

> **Steps:**
> 
> Step 1: 
> From the project directory run command,
> `mvn clean install`
> This command will build java project and load into the docker container.
>
> Step 2:
> Now run command
> `docker-compose up`
> This will create docker environment and resolve all the dependency also run into a container.
> 
> Step 3: Open REST tool like 'Postman'. 

> **Sample Example**
> 
> For Creating Account:
> Send POST request to http://localhost:8080/modular-service/v1/account
>
> INPUT:
`{
"customerId" : "1",
"country":"india",
"currencies":[
"EUR", "SEK", "GBP", "USD"
]
}`
>
> OUTPUT:
`{
"accountId": 1,
"customerId": "1",
"country": "india",
"balances": [
{
"currency": "EUR",
"ammount": 0
},
{
"currency": "SEK",
"ammount": 0
},
{
"currency": "GBP",
"ammount": 0
},
{
"currency": "USD",
"ammount": 0
}
]
}`

>For Get Account:
>Send GET request to http://localhost:8080/modular-service/v1/account/1
>
>OUTPUT:
`{
"accountId": 1,
"customerId": "1",
"country": "india",
"balances": [
{
"currency": "EUR",
"ammount": 0.00
},
{
"currency": "SEK",
"ammount": 0.00
},
{
"currency": "GBP",
"ammount": 0.00
},
{
"currency": "USD",
"ammount": 0.00
}
]
}`

>For Creating Transaction:
>Send POST request to http://localhost:8080/modular-service/v1/transaction
>
>INPUT: For credit
`{
"accountId" : 1,
"ammount" : 1000,
"currency" : "USD",
"direction": "IN",
"description" : "Account credited."
}`
>
>OUTPUT:
`{
"accountId": 1,
"transactionId": 1,
"direction": "IN",
"amount": 1000,
"balance": 1000.00,
"currency": "USD",
"description": "Account credited."
}`
>
>INPUT: For debit
`{
"accountId" : 1,
"ammount" : 100,
"currency" : "USD",
"direction": "OUT",
"description" : "Account debited."
}`
>
>OUTPUT:
`{
"accountId": 1,
"transactionId": 2,
"direction": "OUT",
"amount": 100,
"balance": 900.00,
"currency": "USD",
"description": "Account debited."
}`

>For Get Transaction:
>Send GET request http://localhost:8080/modular-service/v1/transaction/1
>
>OUTPUT:
`[
{
"accountId": 1,
"transactionId": 1,
"direction": "IN",
"amount": 1000.00,
"balance": 900.00,
"currency": "USD",
"description": "Account credited."
},
{
"accountId": 1,
"transactionId": 2,
"direction": "OUT",
"amount": 100.00,
"balance": 900.00,
"currency": "USD",
"description": "Account debited."
}
]`
