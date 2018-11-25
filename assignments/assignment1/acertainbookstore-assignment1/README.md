========== Instructions to build and run the project ================

*********** If you want to use ant and the build file provided **********

-- Install the following or add it to your classpath so that you
can invoke ant

- ant,ant-junit,ant-contrib

1. To clean the build
$ant clean

2. To build the project
$ant

3. To run the test cases locally
$ant -Dlocaltest=true test

4. To run the test cases remotely
$ant -Dlocaltest=false test

************ If you do not want to use ant **********************

1. Import the the project in your IDE. 

2. Add the jars in the lib directory in your build path as external jars.

3. You can run the JUnit tests by clicking on the Test case file and 
running it as a "JUnit test".

4. You can turn on/off the local/remote mode of running the test case i.e.,
using the *HTTPProxy classes or not by configuring the localTest variable
in each of the test classes. 