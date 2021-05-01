# vancouver
A bus management system based on Vancouver using the TransLink open API.

## Dependencies/set up:

- Download javafx-sdk-11.0.2 which can be downloaded here:
https://gluonhq.com/products/javafx/

- Store all of the lib files from javafx file inside the referenced libraries of the project.
- Inside launch.json inlude "vmArgs" inside the configuration for "Launch app", for example see below.
        
"vmArgs": "--module-path *YOUR PATH TO LIB FOLDER INSIDE JAVAFX FOLDER GOES HERE* --add-modules javafx.controls"

        [example]  
        {
        "type": "java",
        "name": "Launch app",
        "request": "launch",
        "mainClass": "app",
        "projectName": "vancouver_e4ae7f2",
        "vmArgs": "--module-path C:\\Users\\New\\Desktop\\javafx-sdk-11.0.2\\lib --add-modules javafx.controls"
        },


In the event you complete all of these steps and you recieve a runtime error saying
"the method *method name* is undefined for the type *method name*" you may need to 
clean the Java language server workspace which can be done using Ctrl + shift + p (on VScode)
then clean Java language server workspace.

