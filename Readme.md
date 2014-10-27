
##Feed Reader

Feed Aggregator demo.

####Stack
	Java 1.7, Maven, Jersey 2, Spring 4, ROME (RSS parser)

####Feed API

    http://localhost:8080/feed-reader/v1/feed?url={url1}&url={url2}
    http://localhost:8080/feed-reader/v1/feed?url={url1}&url={url2}&sort=asc
    http://localhost:8080/feed-reader/v1/feed?url={url1}&url={url2}&sort=desc
    http://localhost:8080/feed-reader/v1/feed?url={url1}&url={url2}&sort=round

Method | URI                                  | Resource        
------ | -------------------------------------|-----------------
GET    | /v1/feed?url={feedUrl}&sort={sort}   | fetch feeds.


Parameters<br/>
url: required, it's a multi parameter, it must have a valid url format, and it must be URL encoded. <br/>
sort: optional, if specified it must have one of the following options [asc, desc, round]<br/>         


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

Example 1: Feeds: http://tuneage.com/rss, http://metallica.tumblr.com/rss, http://blog.bandpage.com/feed/

    http://localhost:8080/feed-reader/v1/feed?url=http%3A%2F%2Ftuneage.com%2Frss&url=http%3A%2F%2Fmetallica.tumblr.com%2Frss&url=http%3A%2F%2Fblog.bandpage.com%2Ffeed


Example 2: Sorting "desc", Feeds: http://tuneage.com/rss, http://metallica.tumblr.com/rss, http://blog.bandpage.com/feed/

    http://localhost:8080/feed-reader/v1/feed?url=http%3A%2F%2Ftuneage.com%2Frss&url=http%3A%2F%2Fmetallica.tumblr.com%2Frss&url=http%3A%2F%2Fblog.bandpage.com%2Ffeed%2F&sort=desc

Example 3:Sorting "round", Feeds: http://tuneage.com/rss, http://metallica.tumblr.com/rss, http://blog.bandpage.com/feed/

    http://localhost:8080/feed-reader/v1/feed?url=http%3A%2F%2Ftuneage.com%2Frss&url=http%3A%2F%2Fmetallica.tumblr.com%2Frss&url=http%3A%2F%2Fblog.bandpage.com%2Ffeed%2F&sort=round
