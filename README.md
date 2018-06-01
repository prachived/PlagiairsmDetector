SpringBoot project to detect plagiarism in Python source code files.

Following are the links of the application:

[Link to the live system](http://ec2-18-217-56-48.us-east-2.compute.amazonaws.com:8080/login)
[System Demo](https://www.youtube.com/watch?v=xacJUkbw_BU&feature=youtu.be)
[System Setup](https://www.youtube.com/watch?v=2g5RY8CKiXE)
[Final Presentation](https://www.youtube.com/watch?v=3BlBaAcXpr8)
[Jenkins Link](http://ec2-18-216-3-127.us-east-2.compute.amazonaws.com:8080/)



Requirements to run this application:
1.	Java (JDK 1.8)
2.	Tomcat Server.
3.	Eclipse IDE
To run the application, perform the following steps:
1.	Pull the candidate branch from the team github repository.
2.	Open Eclipse, select File -> Import -> Existing Maven Project.
3.	Browse to the folder where candidate branch contents exist. (PlagiarismDetector/plagiarism-detector)
4.	Click on Finish.

Now, refresh the project. Right click on the project, click refresh.
In order to generate the project grammar, right click, run as-> Maven clean. Then right click on the project, run as-> Maven install. 
This should get you rid of all the errors.
To locally run the project:
1. go to src/main/java/com/plagiarism/detector package and right click MainApp.java and do run as java application.
2. Now wait for 2 mins, in your local browser type http://localhost:8080/login if you are using port 8080 else replace 8080 with your port.
3.This should land you on login page, you can now register a new user by clicking register and then login in with username and password and use the application.
4. After logging in you see 2 option upload files in batch or upload files
5. If you are uploading files in batch make sure the directory structure is as follows uploads/student1, uploads/student2 etc.
For individual submissions use student 1, student 2 etc.
6.After files are successfully uploaded you can do run plagiarism check and run for different type of strategies.


Errors you may additionally face:
1.	Heap space error. – In this case, go to Run in the menu of Eclipse, then click on Run Configurations, in test tab, put the class name where the heap space error was encountered. Now, go to Arguments tab, In VM arguments, type -Xms512m -Xmx1024m, click apply and then run.

