# books-spring

URL : 127.0.0.1:8080/rest/book

Post Body
{
"name":"book1",
"author":"author1",
"price":1000
}

Content-Type: application/json

Start docker image
docker run --name some-postgres -e POSTGRES_PASSWORD=mysecretpassword -d -p 5432:5432 postgres