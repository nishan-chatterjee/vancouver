
Authors:
Nishan Chaterjee:20337024
Terlo Akintola:18326179
Cornel Jonathan Cicai:19335265
Michael Adebusuyi:16321842

Project contributions:

Nishan Chaterjee & Cornel Jonathan Cicai: Implemenation of Finding shortest paths between 2 bus stops (as input by the user), returning the list of stops
en route as well as the associated “cost”.

Terlo Akintola: Implemenation of searching for all trips with a given arrival time, returning full details of all trips matching the
criteria (zero, one or more), sorted by trip id.

Michael Adebusuyi: Implemenation of searching for a bus stop by full name or by the first few characters in the name, using a
ternary search tree (TST), returning the full stop information for each stop matching the
search criteria (which can be zero, one or more stops).

Cornel Jonathan Cicai: Implemenation of Providing front interface enabling selection between the above features or an option to exit
the programme, and enabling required user input. & Video Demonstration of the project.

Document history available here.
https://docs.google.com/document/d/1kMWyTZ8r073y9HN-eIl0KnsdMiWoYiV3q1UU7zJrheg

Github repository available here.
https://github.com/nishan-chatterjee/vancouver


Dependencies/set up:
    download javafx-sdk-11.0.2
    store the lib files from javafx file inside the referenced library
    inside launch.json inlude "vmArgs" inside the configuration for "Launch app", for example see below
            "vmArgs": "--module-path <YOUR PATH TO LIB FOLDER INSIDE JAVAFX FOLDER> --add-modules javafx.controls"

        [example]  
        {
            "type": "java",
            "name": "Launch app",
            "request": "launch",
            "mainClass": "app",
            "projectName": "vancouver_e4ae7f2",
            "vmArgs": "--module-path C:\\Users\\New\\Desktop\\javafx-sdk-11.0.2\\lib --add-modules javafx.controls"
        },


in the event you complete all of these steps and you recieve a runtime error saying
" the method <method name> is undefined for the type <method name>" you may need to 
clean the Java language server workspace which can be done using Ctrl + shift + p (on VScode)
then clean Java language server workspace. 

    