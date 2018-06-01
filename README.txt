COM1032 Mobile Computing 
Assignment 1

Student Name: 		Vasily Shcherbinin
Student Email: 		vs00162@surrey.ac.uk
Application Name:	vs00162_todoList
Timestamp:		02:00 14/03/2016

My application fulfills all the core features set in the problem definition:

	1) When opening the app for the first time, the list is empty.
	2) User can add items to the list.
	3) User can delete items from the list (individually or delete entire list).
	4) List is displayed.
	5) Details of an item in the list can be displayed on their own.
	6) Application can support rotation from portrait to landscape orientation.*
	7) Application remembers the state of the list when rotated, exited and/or relaunched.

	*The MainActivity screen orientation is set permanently to portrait in the Manifest. 
	 This is not a bug, but is deliberate. 
	 Other activities do support screen rotation, e.g. ListActivity.

Additionally, the following requirements from the problem definition have been fulfilled:

	1) A nice icon for the app was created.
	2) Action Bar was used within the app.
	3) It is possible to record the due date of a task.
	4) More advanced UI for adding and deleting a task was implemented.

In addition to the said above, this application contains features:

	1) A Splashscreen that runs during bootup. Implemented as per Google recommendations.
	2) An SQLite Database has been implemented to store data (1 Table, 4 Columns). 
	3) Dates are implemented with the use of DatePickerDialog.
	4) It is possible to update a ListView item.
	5) Current date on the system is used as the date for when the task was created, although this can be edited if needed. 
	
Special instructions:

	1) In order to see the Splashscreen, the application must be booted from a portrait position. 
	2) ListView items support both long clicks and short clicks. 
	3) In the dialog to add task, in order to open the DatePickerDialog you need to double tap the EditText.*

	*This is because the first tap sets the focus on the EditText, whilst the second tap launches the actual dialog. 

Finally, all code has been tested using AVD and the ASUS tablets, as well as was correctly formatted in Android Studio. 