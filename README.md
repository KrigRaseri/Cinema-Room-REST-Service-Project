# Cinema REST Controller Project
Hyperskill project to create a REST controller for a cinema ticket booking system. 
In this project, you will create a simple Spring REST service that will help you manage
a small movie theater. It will handle HTTP requests in controllers, create services, 
and respond with JSON objects.

## Technology / External Libraries
- Spring boot 3
- Spring Web
- Lombok
- WebTestClient
- JUnit 5
- Mockito
- H2 Database
- Gradle
- JPA / Hibernate

## Project Description

## Project Completion Status
Work in progress

## Project Progress Journal

# Stage 1/4

Project started. Created initial commits and project structure. Created a simple end point to return all
available seats in the cinema. This part was pretty easy, but took longer because I spent a good amount of time 
binging on Spring tutorials.


# Stage 2/4

Created an end point to purchase a ticket, and also implement exception handling for bad requests.
I started with making some mock tests first to practice test driven design, then made a skeleton of an integration test 
with test rest template. I spent way too long going back and forth on how to organize my layers, and naming conventions. 
Then I ended up getting stuck on how to make a post request with a seat object body. Turns out I understood it, the 
problem was I did not format the generated request right and therefore was not getting a correct response. Then I had 
some trouble with the global and custom exceptions but finally got it. Finally, I changed over to web test client for practice
and because it's better. Ended up having a big gap in progress due to moving and other life events.

=========================================================================

# Stage 3/4:

*==Primary tasks==*

Primary task for this stage was implementing a ticket return end point. I started with some mock tests that would return
the ticket object via mockito, however I discovered it needed to return a custom response and not the ticket by itself.
I then changed up the test to take a UUID request, and the custom return ticket response. From there I started actually
making said classes and implementations. Then I made a test for a token(UUID) not being found, implementing this was the
same as the other exceptions. I also needed to make a new custom jpa query to findByUUID.

*==Secondary tasks==*

The main secondary point was to change up the purchase ticket's response body to include the token along with the ticket.
This was a quick change where I added a PurchaseTicketResponse class and changed the purchase ticket method to return it.

*==Additional Changes==*

- Instead of returning seats from the controller, I made some response/request dto's that use ticket, a dto for seat, and
a mapper so the entity seat never leaves the service layer.
- Changed over to using application.properties row and column variables instead of hard coding them, so once set it will
be the same value for all classes and tests.
- Made CinemaInfo more simple, and be more like a model instead of a model/dto hybrid guy.
- Added logging. Info level for the console, and warn/error for the file for now.

***==Notes==***

I think I will try this journal format from now on to highlight the main points of each stage, the secondary changes 
needed for the stage, and any additional changes I made. I think this will help me keep track of what I did and also help
reinforce what I learned via active recall. 

Overall, this stage was pretty easy, but I made it harder but trying new stuff and adding other stuff the stage didn't even
need. It takes longer, but I feel like it's helping me a lot to do it this way. After all this project originally has no
JPA, and no database just a hash map. It also did not need tests, but it is good practice to do them, especially when TDD
is popular so might as well.

=========================================================================

# Stage 4/4

**==Primary tasks==**

**==Secondary tasks==**

**==Additional Changes==**

**==Notes==**