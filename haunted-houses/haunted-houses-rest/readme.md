# Haunted Houses REST API

## List all
`curl -i -X GET http://localhost:8080/pa165/rest/api/v1/house`

## List
`curl -i -X GET http://localhost:8080/pa165/rest/api/v1/house/{id}`

## Delete
`curl -i -X DELETE http://localhost:8080/pa165/rest/api/v1/house/{id}`

## Create
`curl -X POST -i -H "Content-Type: application/json" --data '{\"name\":\"nameValue\",\"address\":\"addressValue\",\"history\":\"historyValue\",\"hint\":\"hintValue\",\"hauntedSince\":\"yyyy-MM-dd\"}' http://localhost:8080/pa165/rest/api/v1/house`

## Update
`curl -X PUT -i -H "Content-Type: application/json" --data '{\"id\":"1",\"name\":\"nameValue\",\"address\":\"addressValue\",\"history\":\"historyValue\",\"hint\":\"hintValue\",\"hauntedSince\":\"yyyy-MM-dd\"}' http://localhost:8080/pa165/rest/api/v1/house`
