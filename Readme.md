
##Feed Reader

Feed API:

http://localhost:8080/feed-reader/v1/feed?url={url1}&url={url2}
http://localhost:8080/feed-reader/v1/feed?url={url1}&url={url2}&sort=desc
http://localhost:8080/feed-reader/v1/feed?url={url1}&url={url2}&sort=round

There is only one api exposed through a GET method.
Multiple urls can be specified with the "url" parameter. Every url parameter must be URL encoded in order for the request to be processed properly, otherwise an invalid request will be returned.

The "sort" parameter supports two values "desc" and "round", it is optional
and defaults to "desc" which is order by descending chronological order.

#Build Project:

run unit tests
>mvn clean package 

run integration test (API)
>mvn verify


#Start Server
>mvn jetty:run


#Examples:


Example 1: 
Sorting omitted, will sort descending chronological order
feeds: http://tuneage.com/rss, http://metallica.tumblr.com/rss, http://blog.bandpage.com/feed/

http://localhost:8080/feed-reader/v1/feed?url=http%3A%2F%2Ftuneage.com%2Frss&url=http%3A%2F%2Fmetallica.tumblr.com%2Frss&url=http%3A%2F%2Fblog.bandpage.com%2Ffeed


Example 2: 
Sorting "desc"
feeds: http://tuneage.com/rss, http://metallica.tumblr.com/rss, http://blog.bandpage.com/feed/

http://localhost:8080/feed-reader/v1/feed?url=http%3A%2F%2Ftuneage.com%2Frss&url=http%3A%2F%2Fmetallica.tumblr.com%2Frss&url=http%3A%2F%2Fblog.bandpage.com%2Ffeed%2F&sort=desc

Example 3: 
Sorting "round"
feeds: http://tuneage.com/rss, http://metallica.tumblr.com/rss, http://blog.bandpage.com/feed/

http://localhost:8080/feed-reader/v1/feed?url=http%3A%2F%2Ftuneage.com%2Frss&url=http%3A%2F%2Fmetallica.tumblr.com%2Frss&url=http%3A%2F%2Fblog.bandpage.com%2Ffeed%2F&sort=round
