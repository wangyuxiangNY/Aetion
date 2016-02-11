1. Test case logic is defined in ServiceClient.java
2. Run file( what test cases to run and how to run it) is defined in TestWebService.java
3. How verification is done: Instead of using assert, I hold all the errors in a variable (StringBuffer errors), and fail the case if any message there
4. Project is using maven to handle 3rd-party downloads and reporting automatically. No manual action is needed.  The other alternative is using Gradle. 
5. If the job is run on Jenkins, run the following command, and maven will generate reporting automatically:
	surefire-report:report-only
	site -DgenerateReports=false
 
6. The code is runnable! See attached run result screenshot
 
 Bugs found: 
     Search is not working.
     Modify User is not working: Age is not modified.        
  