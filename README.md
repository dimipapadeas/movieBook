# MovieRama

MovieRama is (yet another) social sharing platform where users can share their favorite movies. Each movie has a title
and a small description as well as a date that corresponds to the date it was added to the database. In addition it
holds a reference to the user that submitted it. Users can also express their opinion about a movie by either likes or
hates.


#### Requirements :

- Docker engine (v20.10.12 used)
- Java : 11 +
- Maven : 3.6 +
- Angular : 13.2.6+ (used)
  - Node : v14.16 +
  - NPM : 6.14 +
  

#### Instructions :


Checkout from repo:

    git clone https://github.com/dimipapadeas/movieRama.git

Navigate to project root folder:

    cd movieRama

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


 Author: D. Papadeas



