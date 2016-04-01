*****************************************************************************************	
Business requirements
	Client can apply for loan by providing his personal data (e.g. first name and surname), amount and term.
	Loan application risk analysis is performed. Risk is considered too high if:
		the attempt to take loan is made between 00:00 to 6:00 AM with maximum possible amount.
		the client reached the maximum number of applications (e.g. 3) per day from a single IP.
Loan is issued if there are no risks associated with the application. If so, client receives a reference to newly created loan. However, if risk is surrounding the application, client receives "rejection" message.
Technical requirements
	Backend in JVM language (Java, Groovy, etc.), XML-less Spring, Hibernate
	RESTful API
	
*****************************************************************************************	

Running as a packaged application:
	$ java -jar assignment-0.0.1-SNAPSHOT.jar


USAGE NOTE:
-add new client:
	$ curl -i -X POST -H "Content-Type:application/json" -d '{ "firstName" : "Bob", "lastName" : "Smit" }' http://localhost:8080/clients
	
	result:	{"newReference":1}

-view client:
	browser: http://localhost:8080/clients/1
	$ curl http://localhost:8080/clients/1

	result: {"id":1,"firstName":"Bob","lastName":"Smit"}

-apply for loan:
	$ curl -i -X POST -H "Content-Type:application/json" -d '{ "amount" : "300", "term" : "12" }' http://localhost:8080/clients/1/loans
	
	result:	{"newReference":1}
	
-view client:
	browser: http://localhost:8080/clients/1/loans/1
	$ curl http://localhost:8080/clients/1/loans/1

	result: {"id":1,"amount":300,"term":12,"start":"2016-04-01T02:33:46.878","ipAddress":"0:0:0:0:0:0:0:1","lastDay":"2016-04-13T02:33:46.878"}