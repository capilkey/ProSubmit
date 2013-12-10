# <span color="blue">ProSubmit Installation Instructions</span>
The following instructions details how to install ProSubmit on a Ubuntu distribution of Linux.

##### Note: CATALINA_HOME = Tomcat Server Root Folder

## Requirements

* **ProSubmit WAR File** - Download thr lastest release of the ProSubmit deployable package from /production
* **Web Server** -Additionally, you will need Tomcat 7 installed (not running)
* **Ubuntu 12.04 LTS** - You will need a server with a release of at leaset Ununtu 12.04 LTS installed
* **MySQL Database** - You will also need at least a release of MYSQL 5.5 installed
* **Database Setup Script**  Download the setup script **setup.sql** from /db
* **Memory** - Ensure that you have at least 300MiB of RAM
* **Disk Space** - Finally, you will need at least the size of the WAR file downloaded + 15.6MiB of hard disk space

### With all that said, lets begin!! 


##Installation Steps

* Locate the root of your tomcat server, open **server.xml** and find the **Host** tag. Add a **Context** tag so that the code segment looks somewhat as seen below.

##### Note: This is optional if you want ProSubmit as your ROOT applicatin
  
```xml
<Host name="localhost"  appBase="webapps" unpackWARs="true" autoDeploy="true">
			
      <Context path="" docBase="CATALINA_HOME/webapps/ProSubmit" reloadable="true">
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
* Copy the War file to the to **CATALINE_HOME/webapps**
* Log into your MySQL database and run the SQL script **setup.sql** as seen below
```sql
mysql> source 'setup.sql';
```

* Start your Tomcat server either by command line or by client
* Finally, open your favourite browser and go to the application. **http://\<Server Name\>:\<Port Number\>/**



# You're Done!! Hooray!!

-![alt text](http://www.learnhebrewpod.com/images/library/Image/final%20for%20read%20more/24a--hooray%281%29.jpg "HOORAY")
