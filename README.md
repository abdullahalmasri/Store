### Store Application

Suppose you are having a e-store and there are two authority first one for admin which can add/delete users and also add/delete items and second authority is customer where the customer only can buy items.

- Spring Boot 
- Elasticsearch and Kibana
- Kafka and Zookeeper
- PostgresSql
- Oauth2o Spring Boot 
- Test Driven Development
- Validation
- Log configuration

# Tools usage

![](https://pandao.github.io/editor.md/images/logos/editormd-logo-180x180.png)
![](https://miro.medium.com/max/720/1*yuhNL6d5DwVoXGwZWeb9ig.png)
![](https://dataview.in/wp-content/uploads/2019/12/Main-Image-min.png)
![](https://i0.wp.com/e4developer.com/wp-content/uploads/2018/01/spring-boot.png?w=810&ssl=1)
![](https://www.clipartmax.com/png/middle/242-2423721_logo-postgresql.png)

![](https://img.shields.io/github/stars/pandao/editor.md.svg)
![](https://img.shields.io/github/forks/pandao/editor.md.svg)
![](https://img.shields.io/github/tag/pandao/editor.md.svg) 
![](https://img.shields.io/github/release/pandao/editor.md.svg) 
![](https://img.shields.io/github/issues/pandao/editor.md.svg) 
![](https://img.shields.io/bower/v/editor.md.svg)

**Table of Contents**

[TOCM]

[TOC]

#pre-installation  [](https://github.com/pandao/editor.md "Heading link")

- first need to download postgres i am using centos7, the link below have instruction to install postgres on your machine please choice the os that you are using on your machine.
  https://www.postgresql.org/download/
- install java 11 the link below 
  https://docs.oracle.com/en/java/javase/11/install/overview-jdk-installation.html#GUID-8677A77F-231A-40F7-98B9-1FD0B48C346A
- maven 3.6.3 and set JAVA_HOME
  https://www.baeldung.com/install-maven-on-windows-linux-mac
- docker 
  https://docs.docker.com/deskto!p/install/
- intellij or any ide you like in my case i am using intellij so i will leave the url of installation down below
  https://www.jetbrains.com/help/idea/installation-guide.html
- postman
- Start code

#Technical Specs :
----

1.Create table in postgresDB in centos7

> sudo -i -u postgres
> psql
> \password 
> "your-password"
> CREATE DATABASE "DATABASE_NAME";

the DATABASE_NAME here either store for running application or storeTest to run the Test cases.
for running application change the DATABASE_NAME as below
    ``` url: jdbc:postgresql://localhost:5432/store ```

in case running the test cases change the application.yml 

  ```  url: jdbc:postgresql://localhost:5432/storetest```
also need to insert first user after the jpa create tables insert the statment below for both cases:
    ``` INSERT INTO user_tb(id,created_time,email,user_name,password,authorize,user_id)
    VALUES ('e2d54e03-fdc1-456a-a4a4-051ba0e6fe49',
    0,'test@admin.com','admin','123','ADMIN',null);```

after that the user will be created at database, please be careful,if you use "" with insert statment there will be error you need to use single ' as shown above.

![](https://github.com/abdullahalmasri/Store/blob/master/images/database.png)

----

###logging info 
In Java, logging is an important feature that helps developers to trace out the errors. Java is the programming language that comes with the logging approach. It provides a Logging API that was introduced in Java 1.4 version. It provides the ability to capture the log file. In this section, we are going to deep dive into the Java Logger API. Also, we will cover logging level, components, Logging handlers or appenders, logging formatters or layouts, Java Logger class,

Let's begin with Logging 

> https://logback.qos.ch/manual/layouts.html#coloring 

The Url above have all features needed about logging, also if you looked at my log console pattern in application.yml you will see that i made it corloful and i removed the data while you can get data by adding 

> %d{yyyy-MM-dd HH:mm:ss}

the refactor code in application.yml will be as below:

> ogging:
>   pattern:
>     console: "%d{yyyy-MM-dd HH:mm:ss} [%red(%thread)] [%green(level)] [%blue(%logger{36}):%yellow(%line)] %magenta(%method) - %msg%n"

and if you don't like it coroful all what you need to do is removing %COLOR_NAME from above statment

----

###Oauth2o
This guide shows you how to build a sample app doing various things with "social login" using OAuth 2.0 and Spring Boot.

> https://spring.io/guides/tutorials/spring-boot-oauth2/

i prefer always to get info about adding dependecy in your pom from spring.io related to tools needed, in my case i did that all info are easy to understand and there are many guide and examples.

----

###Elasticsearch and Kibana

ok,this is actually the most interesting part, let's begin with guide of elasticsearch and why i could need it, ok the purpose here that i don't need always to hit the database to get counts objects or i don't wanna keep basket of customer hit to database also since elasticsearch is nosql which will be faster in retreive Terms and Aggregations more than sql Database, acutally there are many advantges and disadvantiges for using any database, but i would like to share with you the way of get bucket of data and configuration and more first you would like to visit the link below:

> https://www.elastic.co/guide/en/elasticsearch/reference/7.17/getting-started.html
> also need to see 3. Requirments 
> https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/
> each time want to change the elasticsearch version you need to visit the spring 
> to see which compatble elasticsearch-data with elasticesearch version 

the yml file will be exist in docker folder with project, 
before you run up your docker images see this guide important for elasticsearch
please please look at this before you run the docker image

> https://www.elastic.co/guide/en/elasticsearch/reference/master/docker.html

first you need to start docker i am using centos 7 so the command to start docker will be 

> systemctl start docker

enter password if exists then 

> sudo docker-compose -f elk.yml up -d

you will see as below
![](https://github.com/abdullahalmasri/Store/blob/master/images/docker.png)

the elasticsearch will connect to localhost:9200
and kibana at localhost:5601 
we use kibana for testing most of time there many usage for it , but to test your Query go to console

> http://localhost:5601/app/dev_tools#/console

```
GET store/_search
{
  "query": {
    "match_all": {}
  }
}

GET store/_search
{
  "query": { 
    "bool" : {
    "must" : [
      {
        "match" : {
          "id": {
            "query": "d0395fed-9fc3-4bfb-9fdd-94c573b3ddbf"
          }
        }
      }
    ],
    "adjust_pure_negative" : true,
    "boost" : 1.0
    }

  }

}

GET store/_search
{
  "query": { 
  "bool" : {
    "must" : [
      {
        "match_all" : {
          "boost" : 1.0
        }
      }
    ],
    "adjust_pure_negative" : true,
    "boost" : 1.0
  }
  },"aggs": {
    "name":{
      "terms":{
        "field":"itemName",
        "size":10,"min_doc_count":1,
        "shard_min_doc_count":0,
        "show_term_doc_count_error":false,
        "order":[
          {"_count":"desc"},
          {"_key":"asc"}
          ]

      }

    },
    "hat_prices":{
      "sum":{
        "field": "price" 

      }

    }

  }

}
```

as you see some of my search qurey for more queries please visit the guide url 

----

###kafka

here i use kafka only to send messages, i haven't handle it by queue, there are many uasges as well , but i worked on configuration there are template topic if you don't like to use application.yml, i left both and your choice to work with what you need also please see guide url below :

> https://spring.io/projects/spring-kafka

when you run the application and save your items you will see that the kafka producer is create message using topic "accept_item" and after the database save the item and the elasticesearch save the same item you be notied that the consumer will show message that the elasticsearch saved well the item depends on validation if its right,to start kafka you will enter the docker-image folder and type the command below:

> sudo docker-compose -f kafka.yml up -d

keep remember before you run the application you need to docker up the elasticsearch docker image and kakfa docker image:
always check twice if image are up by : 

> docker ps

----

###application diagram

![](https://github.com/abdullahalmasri/Store/blob/master/images/app.png)

----

###postman

please check the postman collection to get the idea of how send data and get data will be found in folder postman

---

###run application 

before run please right down:

> mvn clean install 

Enjoy :)

---

OS uased : Centos7 

#Abdullah Al Masri 

----

###End
