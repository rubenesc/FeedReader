
##Feed Reader

Feed Aggregator demo.

####Stack
	Java 1.7, Maven, Jersey 2, Spring 4, ROME (RSS parser)

####Feed API

Create a Group of RSS Feeds

Method | URI                         | Resource        | Example
-------|-----------------------------|-----------------|----------
POST   | /v1/group                   | create group    | curl -i -X POST -H 'Content-Type: application/json' -d '{"name":"Group 1","urls":["http://tuneage.com/rss","http://metallica.tumblr.com/rss","http://blog.bandpage.com/feed/"]}' http://localhost:8080/feed-reader/v1/group
GET    | /v1/group                   | list groups     | curl -i -X GET -H 'Content-Type: application/json' http://localhost:8080/feed-reader/v1/group
GET    | /v1/group/{id}              | find a group    | curl -i -X GET -H 'Content-Type: application/json' http://localhost:8080/feed-reader/v1/group/1
PUT    | /v1/group/{id}              | update a group  | curl -i -X PUT -H 'Content-Type: application/json' -d '{"name":"Group 1 updated","urls":["http://tuneage.com/rss"]}' http://localhost:8080/feed-reader/v1/group/1
DELETE | /v1/group/{id}              | delete a group  | curl -i -X DELETE -H 'Content-Type: application/json' http://localhost:8080/feed-reader/v1/group/1
DELETE | /v1/group/{id}              | delete all      | curl -i -X DELETE -H 'Content-Type: application/json' http://localhost:8080/feed-reader/v1/group


Fetch RSS Feeds

Method | URI                                       | Resource                  | Example
------ | ------------------------------------------|---------------------------|-----------------------------
GET    | /v1/feed/{groupId}?sort={sort}            | Fetch feeds by groupId.   |  curl -i -X GET -H 'Content-Type: application/json' http://localhost:8080/feed-reader/v1/feed/1?sort=round
GET    | /v1/feed?url={feedUrl}&sort={sort}        | Fetch feeds by url.       | http://localhost:8080/feed-reader/v1/feed?url=http%3A%2F%2Ftuneage.com%2Frss&url=http%3A%2F%2Fmetallica.tumblr.com%2Frss&url=http%3A%2F%2Fblog.bandpage.com%2Ffeed%2F&sort=round




####Install
```bash
  $ git clone https://github.com/rubenesc/FeedReader.git
  $ cd FeedReader
  $ mvn clean package
  $ mvn jetty:run
```

####Build Project

run unit tests
>mvn clean package 

run integration test (API)
>mvn verify

####Start Server
>mvn jetty:run

####Examples

#####Groups
```bash
curl -i -X GET -H 'Content-Type: application/json' http://localhost:8080/feed-reader/v1/group

curl -i -X POST -H 'Content-Type: application/json' -d '{"name":"Group 1","urls":["http://tuneage.com/rss","http://metallica.tumblr.com/rss","http://blog.bandpage.com/feed/"]}' http://localhost:8080/feed-reader/v1/group
curl -i -X POST -H 'Content-Type: application/json' -d '{"name":"Group 2","urls":["http://metallica.tumblr.com/rss"]}' http://localhost:8080/feed-reader/v1/group
curl -i -X POST -H 'Content-Type: application/json' -d '{"name":"Group 3","urls":["http://blog.bandpage.com/feed/]}' http://localhost:8080/feed-reader/v1/group


curl -i -X GET -H 'Content-Type: application/json' http://localhost:8080/feed-reader/v1/group/1
curl -i -X GET -H 'Content-Type: application/json' http://localhost:8080/feed-reader/v1/group/2
curl -i -X GET -H 'Content-Type: application/json' http://localhost:8080/feed-reader/v1/group/3

curl -i -X PUT -H 'Content-Type: application/json' -d '{"name":"Group 1 updated","urls":["http://tuneage.com/rss"]}' http://localhost:8080/feed-reader/v1/group/1
curl -i -X GET -H 'Content-Type: application/json' http://localhost:8080/feed-reader/v1/group/1

curl -i -X DELETE -H 'Content-Type: application/json' http://localhost:8080/feed-reader/v1/group/3
curl -i -X GET -H 'Content-Type: application/json' http://localhost:8080/feed-reader/v1/group/3
```
#####Feeds
```bash
curl -i -X GET -H 'Content-Type: application/json' http://localhost:8080/feed-reader/v1/feed/1?sort=desc
curl -i -X GET -H 'Content-Type: application/json' http://localhost:8080/feed-reader/v1/feed/1?sort=round
```
