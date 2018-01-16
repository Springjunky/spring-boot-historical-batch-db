# spring-boot-historical-batch-db
A Spring-Boot-Example with an unlimted an easy to configure HSQL file based _permanent_ database

### Description

This is just an HSQL-based independet Spring-Batch Historical Database

#### Usage

1. Just clone the source
2. Start the Application with the maven spring-boot plugin
``` java

    mvn spring-boot:run

```

Take a closer look at the output .. there ist your jdbc-connection-string.

```
************************************************************************
*                                                                       
* JDBC-Connection-String jdbc:hsqldb:hsql://<your-host>:4000/BATCH_META
*                                                                       
************************************************************************
                                                                    
```

If you want to change the listen-port edit the application.properties

