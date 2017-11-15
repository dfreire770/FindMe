# FindMe
This Android App allows users to sign in with google and show the location of all connected users in a map. The idea was to track disabled people, kids or elder in a defined ratio. 
If they get out of range, it sends notifications to the others.

## Libraries and APIs
* Google Maps API
* Firebase

Warning: This app sends SMS messages to a defined CelLphone Number. Fees may be applyied.

## How it works
This App was created for Android Nougat. If you plan to use it, you'll probably need to update a couple of classes.
Node.js server included.

Install the application and run the Node.js server provided. LogIn to the App with your Google Account.
When the map shows, the app will connect to a Firebase Database to register and update your current location on real time.

The Node.js server, is used to register who is connected to the app and send messages to other connected users.
When the tracked user leaves the defined area, the server will notify to the others sending messages to connected devices.

This app was just an experiment and for academic porpuses.
