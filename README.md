# MovieBook by Dimitris
MovieBook is a portfolio app.


## The MovieBook
MovieBook is a social sharing platform where users can share their favorite movies. Each movie has a title
and a small description as well as a date that corresponds to the date it was added to the database. In addition it
holds a reference to the user that submitted it. Users can also express their opinion about a movie by either likes or hates.

### Business Requirements
- Users should be able to log into their account or sign up for a new one
- Users should be able to add movies by completing a form. Movies should be
persisted and reference the user that submitted them.
- Users should be able to express their opinion for any movie by either a like or a hate.
Users can vote only once for each movie and can change their vote at any time by
switching to the opposite vote or by retracting their vote altogether.
- Users should not be able to vote for the movies they have submitted.
- Users should be able to view the list of movies and sort them by number of likes
- Users should be able to view all movies submitted by a specific user


#### System Requirements :

- Docker engine (v20.10.12 used)
- Java : 11 +
- Maven : 3.6 +
- Angular : 13.2.6+ (used)
  - Node : v14.16 +
  - NPM : 6.14 +
  

#### Instructions :


Checkout from repo:

    git clone https://github.com/dimipapadeas/movieBook.git

Navigate to project root folder:

    cd movieBook

Build project:

  `mvn clean install -DskipTests`
or
`mvn clean install`  (to build with tests)

Compose database docker image:

    docker-compose -f docker-compose.yml up -d

Navigate to backend module folder

    cd backend

Start the app

    mvn spring-boot:run

Open browser to

    http://localhost:8080/

Terminate the server 

    Ctrl+ C

#### Demos :

[demo1.webm](https://user-images.githubusercontent.com/20535822/189482995-e7bfb815-17d8-40e3-b67d-fe1973b8de3d.webm)


[demo2.webm](https://user-images.githubusercontent.com/20535822/189483001-4658fe9d-9908-4147-b933-7803bcbc8612.webm)

 Author: D. Papadeas
