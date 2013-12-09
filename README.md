# ProSubmit Installation Instructions
The fallowing instructions details how to install the application ProSubmit on a linux server.

## Requirements

* **ProSubmit WAR File** - Download thr lastest release of ProSubmit application package from /production
* **Web Server** -Additionally, you will need Tomcat 7 installed (not running)
* **Ubuntu 12.04 LTS** - You will need server with a release of at leaset Ununtu 12.04 LTS installed
* **MySSQL Database** - You will also need at least a release of MYSQL  5.5 installed
* **Memory** - Ensure that you have at least 300MiB of RAM
* **Disk Space** - Finally, you will need at least the size of the WAR file downloaded + 15.6MiB of hard disk space

### With all that said, lets begin!! 


##Installation Steps

* Locate the root of your tomcat server, open **server.xml** and ind the **Host** tag. Add a **Context** tag so that the code segment looks somewhat as seen below
  
```xml
<Host name="localhost"  appBase="webapps" unpackWARs="true" autoDeploy="true">
			
      <Context path="" docBase="C:\Program Files\apache-tomcat-7.0.19\webapps\ProSubmit" reloadable="true">
      </Context> 

  <!-- SingleSignOn valve, share authentication between web applications
       Documentation at: /docs/config/valve.html -->
  <!--
  <Valve className="org.apache.catalina.authenticator.SingleSignOn" />
  -->

  <!-- Access log processes all example.
       Documentation at: /docs/config/valve.html
       Note: The pattern used is equivalent to using pattern="common" -->
  <Valve className="org.apache.catalina.valves.AccessLogValve" directory="logs"  
         prefix="localhost_access_log." suffix=".txt"
         pattern="%h %l %u %t &quot;%r&quot; %s %b" resolveHosts="false"/>

</Host>
```


# You're Done!! Hooray!!

-![alt text](http://www.learnhebrewpod.com/images/library/Image/final%20for%20read%20more/24a--hooray%281%29.jpg "HOORAY")
