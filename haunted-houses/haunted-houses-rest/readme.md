# Haunted Houses REST API
Tested on Windows using Postman. Using working code produced by Postman with [Windows curl](https://curl.haxx.se/windows/) resulted in JSON parsing errors.

## List all
`curl -i -X GET http://localhost:8080/pa165/rest/house`

## List
`curl -i -X GET http://localhost:8080/pa165/rest/house/{id}`

## Delete
`curl -i -X DELETE http://localhost:8080/pa165/rest/house/{id}`

## Create
`curl --location --request POST 'http://localhost:8080/pa165/rest/house/create' --header 'Content-Type: application/json' --data-raw '{"name":"nameValue","address":"addressValue","history":"historyValue","hint":"hintValue","hauntedSince":"yyyy-MM-dd"}'`

## Update
`curl --location --request POST 'http://localhost:8080/pa165/rest/house/update' --header 'Content-Type: application/json' --data-raw '{"id":"1","name":"nameValue","address":"addressValue","history":"historyValue","hint":"hintValue","hauntedSince":"yyyy-MM-dd"}'`
