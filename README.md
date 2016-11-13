A Simple Read Once Messaging System
===================================

This is a sbt project written in scala that implements a read once messaging system web application.

It uses mongodb as a message database, nginx as a reverse proxy, sbt-native-packager and its docker plugin to containerize the webapp and provides sample ansible playbooks for deployment of the application system.

The user can browse to the application host, enter a short text message and retrieve a QRcode representation of a unique url identifying the message. The user may communicate the QRcode through a side channel to the message recipient. The message recipient retrieves the message by accessing the url contained in the QRcode. After the first access to this url, the message is deleted from the system.

The property of read-onceness has interesting logical attributes which make it an interesting component of more complex systems.

Notes
-----

* This application is provided to illustrate the architecture of a simple scala play app and the use of docker and ansible to provision a host with the application container.
* This application uses a standard sbt directory structure. You must have sbt installed to compile and publish it.
* It is based on the typesafe play framework. You must have play installed to compile it.
* It uses docker to containerize and publish the application to a private docker repository. You must have docker installed to run the sbt docker:publish command
* You must have run docker login prior to running the sbt docker:publish command
* Sample ansible playbooks are provided which deploy and un-deploy the application and its supporting mongodb and nginx services to the host system. You must have ansible installed to run these playbooks.
* The default docker repository and default hostname should be changed to suitable values before you build and deploy this application. See the dockerRepository and maintainer assignments in ./build.sbt, the docker repository identifiers in ./deploy/*.yml and the hostnames in ./deploy/hosts for more information.

Checkout a copy
---------------

You can checkout a copy of this project with this command:

<pre>
git clone git://github.com/n0n3such/r1ms.git
</pre>

License
-------

Please read the LICENSE file for information about applicable license conditions.
