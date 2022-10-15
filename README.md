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

<<<<<<< HEAD
=======

>>>>>>> master
**Table of Contents**

[TOCM]

[TOC]

<<<<<<< HEAD
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

=======

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
>>>>>>> master
> sudo -i -u postgres
> psql
> \password 
> "your-password"
> CREATE DATABASE "DATABASE_NAME";

the DATABASE_NAME here either store for running application or storeTest to run the Test cases.
<<<<<<< HEAD
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

###Oauth2o
This guide shows you how to build a sample app doing various things with "social login" using OAuth 2.0 and Spring Boot.

> https://spring.io/guides/tutorials/spring-boot-oauth2/

i prefer always to get info about adding dependecy in your pom from spring.io related to tools needed, in my case i did that all info are easy to understand and there are many guide and examples.

###Elasticsearch and Kibana

ok,this is actually the most interesting part, let's begin with guide of elasticsearch and why i could need it, ok the purpose here that i don't need always to hit the database to get counts objects or i don't wanna keep basket of customer hit to database also since elasticsearch is nosql which will be faster in retreive Terms and Aggregations more than sql Database, acutally there are many advantges and disadvantiges for using any database, but i would like to share with you the way of get bucket of data and configuration and more first you would like to visit the link below:

> https://www.elastic.co/guide/en/elasticsearch/reference/7.17/getting-started.html
> also need to see 3. Requirments 
> https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/
> each time want to change the elasticsearch version you need to visit the spring 
> to see which compatble elasticsearch-data with elasticesearch version 

the yml file will be exist in docker folder with project, 
first you need to start docker i am using centos 7 so the command to start docker will be 

> systemctl start docker
> enter password if exists
> then 
> sudo docker-compose -f elk.yml up -d

you will 
=======
![](https://github.com/abdullahalmasri/Store/blob/master/images/database.png)

https://logback.qos.ch/manual/layouts.html#coloring 
logging info 

~~Strikethrough~~ <s>Strikethrough (when enable html tag decode.)</s>
*Italic*      _Italic_
**Emphasis**  __Emphasis__
***Emphasis Italic*** ___Emphasis Italic___

Superscript: X<sub>2</sub>，Subscript: O<sup>2</sup>

**Abbreviation(link HTML abbr tag)**

The <abbr title="Hyper Text Markup Language">HTML</abbr> specification is maintained by the <abbr title="World Wide Web Consortium">W3C</abbr>.

###Blockquotes

> Blockquotes

Paragraphs and Line Breaks
                    
> "Blockquotes Blockquotes", [Link](http://localhost/)。

###Links

[Links](http://localhost/)

[Links with title](http://localhost/ "link title")

`<link>` : <https://github.com>

[Reference link][id/name] 

[id/name]: http://link-url/

GFM a-tail link @pandao

###Code Blocks (multi-language) & highlighting

####Inline code

`$ npm install marked`

####Code Blocks (Indented style)

Indented 4 spaces, like `<pre>` (Preformatted Text).

    <?php
        echo "Hello world!";
    ?>
    
Code Blocks (Preformatted text):

    | First Header  | Second Header |
    | ------------- | ------------- |
    | Content Cell  | Content Cell  |
    | Content Cell  | Content Cell  |

####Javascript　

```javascript
function test(){
	console.log("Hello world!");
}
 
(function(){
    var box = function(){
        return box.fn.init();
    };

    box.prototype = box.fn = {
        init : function(){
            console.log('box.init()');

			return this;
        },

		add : function(str){
			alert("add", str);

			return this;
		},

		remove : function(str){
			alert("remove", str);

			return this;
		}
    };
    
    box.fn.init.prototype = box.fn;
    
    window.box =box;
})();

var testBox = box();
testBox.add("jQuery").remove("jQuery");
```

####HTML code

```html
<!DOCTYPE html>
<html>
    <head>
        <mate charest="utf-8" />
        <title>Hello world!</title>
    </head>
    <body>
        <h1>Hello world!</h1>
    </body>
</html>
```
>>>>>>> master

###Images

Image:

![](https://pandao.github.io/editor.md/examples/images/4.jpg)

> Follow your heart.

![](https://pandao.github.io/editor.md/examples/images/8.jpg)

> 图为：厦门白城沙滩 Xiamen

图片加链接 (Image + Link)：

[![](https://pandao.github.io/editor.md/examples/images/7.jpg)](https://pandao.github.io/editor.md/examples/images/7.jpg "李健首张专辑《似水流年》封面")

> 图为：李健首张专辑《似水流年》封面
<<<<<<< HEAD

=======
                
>>>>>>> master
----

###Lists

####Unordered list (-)

- Item A
- Item B
- Item C
<<<<<<< HEAD

=======
     
>>>>>>> master
####Unordered list (*)

* Item A
* Item B
* Item C

####Unordered list (plus sign and nested)
<<<<<<< HEAD

+ Item A
+ Item B
  + Item B 1
  + Item B 2
  + Item B 3
+ Item C
  * Item C 1
  * Item C 2
  * Item C 3

####Ordered list

1. Item A
2. Item B
3. Item C

----

###Tables

| First Header | Second Header |
| ------------ | ------------- |
| Content Cell | Content Cell  |
| Content Cell | Content Cell  |

| First Header | Second Header |
| ------------ | ------------- |
| Content Cell | Content Cell  |
| Content Cell | Content Cell  |

| Function name | Description                |
| ------------- | -------------------------- |
| `help()`      | Display the help window.   |
| `destroy()`   | **Destroy your computer!** |

| Item     | Value |
| -------- | -----:|
| Computer | $1600 |
| Phone    | $12   |
| Pipe     | $1    |

| Left-Aligned  | Center Aligned  | Right Aligned |
|:------------- |:---------------:| -------------:|
| col 3 is      | some wordy text | $1600         |
| col 2 is      | centered        | $12           |
| zebra stripes | are neat        | $1            |

=======
                
+ Item A
+ Item B
    + Item B 1
    + Item B 2
    + Item B 3
+ Item C
    * Item C 1
    * Item C 2
    * Item C 3

####Ordered list
                
1. Item A
2. Item B
3. Item C
                
----
                    
###Tables
                    
First Header  | Second Header
------------- | -------------
Content Cell  | Content Cell
Content Cell  | Content Cell 

| First Header  | Second Header |
| ------------- | ------------- |
| Content Cell  | Content Cell  |
| Content Cell  | Content Cell  |

| Function name | Description                    |
| ------------- | ------------------------------ |
| `help()`      | Display the help window.       |
| `destroy()`   | **Destroy your computer!**     |

| Item      | Value |
| --------- | -----:|
| Computer  | $1600 |
| Phone     |   $12 |
| Pipe      |    $1 |

| Left-Aligned  | Center Aligned  | Right Aligned |
| :------------ |:---------------:| -----:|
| col 3 is      | some wordy text | $1600 |
| col 2 is      | centered        |   $12 |
| zebra stripes | are neat        |    $1 |
                
>>>>>>> master
----

####HTML entities

&copy; &  &uml; &trade; &iexcl; &pound;
&amp; &lt; &gt; &yen; &euro; &reg; &plusmn; &para; &sect; &brvbar; &macr; &laquo; &middot; 

X&sup2; Y&sup3; &frac34; &frac14;  &times;  &divide;   &raquo;

18&ordm;C  &quot;  &apos;

##Escaping for Special Characters

\*literal asterisks\*

##Markdown extras

###GFM task list

- [x] GFM task list 1
- [x] GFM task list 2
- [ ] GFM task list 3
<<<<<<< HEAD
  - [ ] GFM task list 3-1
  - [ ] GFM task list 3-2
  - [ ] GFM task list 3-3
- [ ] GFM task list 4
  - [ ] GFM task list 4-1
  - [ ] GFM task list 4-2
=======
    - [ ] GFM task list 3-1
    - [ ] GFM task list 3-2
    - [ ] GFM task list 3-3
- [ ] GFM task list 4
    - [ ] GFM task list 4-1
    - [ ] GFM task list 4-2
>>>>>>> master

###Emoji mixed :smiley:

> Blockquotes :star:

####GFM task lists & Emoji & fontAwesome icon emoji & editormd logo emoji :editormd-logo-5x:

- [x] :smiley: @mentions, :smiley: #refs, [links](), **formatting**, and <del>tags</del> supported :editormd-logo:;
- [x] list syntax required (any unordered or ordered list supported) :editormd-logo-3x:;
- [x] [ ] :smiley: this is a complete item :smiley:;
- [ ] []this is an incomplete item [test link](#) :fa-star: @pandao; 
- [ ] [ ]this is an incomplete item :fa-star: :fa-gear:;
<<<<<<< HEAD
  - [ ] :smiley: this is an incomplete item [test link](#) :fa-star: :fa-gear:;
  - [ ] :smiley: this is  :fa-star: :fa-gear: an incomplete item [test link](#);

###TeX(LaTeX)

=======
    - [ ] :smiley: this is an incomplete item [test link](#) :fa-star: :fa-gear:;
    - [ ] :smiley: this is  :fa-star: :fa-gear: an incomplete item [test link](#);
            
###TeX(LaTeX)
   
>>>>>>> master
$$E=mc^2$$

Inline $$E=mc^2$$ Inline，Inline $$E=mc^2$$ Inline。

$$\(\sqrt{3x-1}+(1+x)^2\)$$
<<<<<<< HEAD

$$\sin(\alpha)^{\theta}=\sum_{i=0}^{n}(x^i + \cos(f))$$

=======
                    
$$\sin(\alpha)^{\theta}=\sum_{i=0}^{n}(x^i + \cos(f))$$
                
>>>>>>> master
###FlowChart

```flow
st=>start: Login
op=>operation: Login operation
cond=>condition: Successful Yes or No?
e=>end: To admin

st->op->cond
cond(yes)->e
cond(no)->op
```

###Sequence Diagram
<<<<<<< HEAD

=======
                    
>>>>>>> master
```seq
Andrew->China: Says Hello 
Note right of China: China thinks\nabout it 
China-->Andrew: How are you? 
Andrew->>China: I am good thanks!
```

<<<<<<< HEAD
###End
=======
###End
>>>>>>> master
