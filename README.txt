Notes to run the environment

JAva version 1.8.328
Docker Version 23.04
Apache mave Maven 3.8.8




before the containers are up you need to make sure the ports are free
the ports that we need to run the app are:
8088 : serviceModels
8089 : ServiceOrderCustomer
8090 : esbService 
3306 : mysql 
27017 : Mongodb
2838 : zookeeper
9092 : kafka
6379: redis


in this project are included some request done with postman (the file is "parrolabs.postman_collection.json") the idea 
is use the esb Service as the mediator between the services and the comunication must be done using this service 
due to that all the request must be sending by through the por 8090 the communication between them is handle by this service 

Architecture:

The esb bus is ESB service (i started to dosome trans transformations unsing spring integration but i couldn't finished)
the cache is handleby Redis the deleted data is stored in mongo db database, the httpclient used mostly is feign but i did some
ResTemplate request as well
 
the MER is described in (ER.png)

How to run 

1.open model-dtos project (the project is done whith maven and the ide was Intellij in case you want to open it with that ide) (clean and install to create the jar and pom in local repository)

2.make su you have Admin rights, the you need to execute:
docker compose up-d

3. once the containers are up you need to start (springBot service) service Models (this service has the beans to create the tables and 
some data for test) (this service handles the basic data info (products, client, type of payment etc))

4. start the second service (ServiceOrderCustomer) this service is in charge to the logic for orders

5.the last step you need to start the last service esbService this is the bus in charge to handle all the requests (the idea is use this service as a gateway to consume the rest of the services) http://localhost:8090
In this service is configured a method to sent multiple PRoducts in case you need to send a lot of products to store (the method is /saveKafkaProducts) the consumer is configured in serviceModels.


Enjoy!!!!!




 

