Download zip file of repository.

Extract to c:\

Project contains a backend and frontend component.

The backend is a Java Springboot application requiring JDK 17+ to run.

The frontend is a React TypeScript application requiring a recent version of node.

To run the backend open a command window in admin mode. 
Move to the tmbackend/build/lib folder.
Enter the following command:  java -jar tmbackend-001-SNAPSHOT.jar

This will initialise a H2 database with some initial data and provide a restful api for the frontend.

For the frontend open a second command window in admin mode.
Move to tmfrontend folder
Run the command:  npm install
This will download all the required dependencies.

Run the following command to launch the frontend application:  npm run dev

To view the documentation on the available api endpoints enter http://localhost:8888/swagger-ui/index.html

Enjoy!
