# crawler
This program counts top javascript libraries used in web pages found on Google.

## Requirements
 - [JAVA 8](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html)
 - [Apache Maven](https://maven.apache.org/index.html)

## How to Setup the project
* `$ git clone git@github.com:barbarafarias/crawler.git` - Clone the project
* `$ cd crawler` - Go into the project's folder
* `$ mvn clean install` - Compile and install the project's dependencies
* `$ cd target` - Go into the project's target folder
* `$ java -jar crawler-0.0.1.jar <search_term>` - Run the program and get the top 5 scripts from results page

### Notes
* This project uses jsoup library which provides a very convenient API for extracting and manipulating HTML documents. Read more about jsoup [here](https://jsoup.org/).
