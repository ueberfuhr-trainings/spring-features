# GraphQL

GraphQL is an open-source data query and manipulation language for APIs, and a runtime for fulfilling 
queries with existing data. GraphQL was developed internally by Facebook in 2012 before being publicly
released in 2015. On 7 November 2018, the GraphQL project was moved from Facebook to the newly established
GraphQL Foundation, hosted by the non-profit Linux Foundation. 
Since 2012, GraphQL's rise has closely followed the adoption timeline as set out by Lee Byron, GraphQL's
creator. Byron's goal is to make GraphQL omnipresent across web platforms.

GraphQL is used to leverage two types of requests, including mutations that change data and queries that retrieve data from server. The conceptual difference lies in the very nature of their operations. While the operations of SOAP represent logic, 
those of GraphQL and REST represent data resources. The advantage of GraphQL compared to REST is the flexibility
of the client to specify which information to retrieve (and which not!). REST resources always contain all
fields (unless you use OData `expand`, that is also not that flexible), while GraphQL responses only
fetch the data the client needs.

Centerpiece is the GraphQL schema, that you can find [in the project](../../src/main/resources/graphql/todo.graphqls).
It defines the queries, the mutations and the corresponding data types.

## Manually Invoke the API

We can use GraphiQL - a UI tool that can communicate with any GraphQL Server and helps to consume and develop against a GraphQL API.

We just need to open the browser with the URL `http://localhost:9080/graphiql`.
**Note:** This is only available when running the app with the `dev` profile.

We then have the possibility to enter end execute our queries.

### Read All Todos

```graphql
query ReadAllTodos {
    findTodos {
        id
        title
        status
    }
}
```
If we want to get the assignee too
(that in our code is simulated to get fetched by a further database query),
we just need to add the field and the requested subfields to the query:

```graphql
query ReadAllTodos {
    findTodos {
        id
        title
        status
        assignee {
            name
        }
    }
}
```

### Get Single Todo

```graphql
query FindById {
    findById(id: 1) {
        title
        status
    }
}
```

### Create Todo

```graphql
mutation CreateTodo {
    createTodo(input: {title: "graphiql-test"}) {
        id
    }
}
```
