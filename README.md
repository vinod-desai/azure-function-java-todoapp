# azure-function-java-todoapp
Azure Function todo app - Java

# APIs

Start updating Function App(todoapp-be)...\
Function App(todoapp-be) is successfully updated\
Starting deployment...\
Trying to deploy artifact to todoapp-be...\
Successfully deployed the artifact to https://todoapp-be.azurewebsites.net \
Deployment done, you may access your resource through todoapp-be.azurewebsites.net \
Syncing triggers and fetching function information\
Querying triggers...\
HTTP Trigger Urls:\
	 create-todo : https://todoapp-be.azurewebsites.net/api/todos \
	 delete-todo : https://todoapp-be.azurewebsites.net/api/todos/{id} \
	 get-todo : https://todoapp-be.azurewebsites.net/api/todos/{id} \
	 get-todos : https://todoapp-be.azurewebsites.net/api/todos \
	 HttpExample : https://todoapp-be.azurewebsites.net/api/httpexample \
	 update-todo : https://todoapp-be.azurewebsites.net/api/todos/{id} \
Deploy succeed

# 1. Get Todos: api/todos
Response Body \
[
{
  "id": "63ec65465dad283dc8dde419",
  "description": "Update Todo 1",
  "done": true
},
{
  "id": "63ec676a5dad2841ec1c5416",
  "description": "Todo 2",
  "done": true
},
{
  "id": "63ec6cc85dad2841ec1c541b",
  "description": "Todo 3",
  "done": false
},
{
  "id": "63ec802f5dad282c3865e8bb",
  "description": "Todo 4",
  "done": false
},
{
  "id": "63ec84185dad28153cf375d9",
  "description": "Todo 5",
  "done": false
},{
  "id": "63ec87895dad283eb0dfb330",
  "description": "Todo 6",
  "done": false
},
{
  "id": "63ec9c2d5dad28384c689bae",
  "description": "update Todo 7",
  "done": false
},
{
  "id": "63ecbb9e5dad282c205efe49",
  "description": "Todo 8",
  "done": true
}
]

# 2. Get Todo: api/todos/{id}
Response Body \
{
  "id": "63ec6cc85dad2841ec1c541b",
  "description": "Todo 3",
  "done": false
}
