***********************************************************************
CONTENTS
***********************************************************************

 1. Introduction
 2. Installing a Binary Distribution
 3. Running the Foundry
 4. Manifest
 5. Where to go for Help/Bug Reports

--------------------------------------------------------------------------
1. Introduction
--------------------------------------------------------------------------

This is a v1.0 or later release of the ActorFoundry, a Java-based
programming environment for building actor systems.  ActorFoundry is
structured as a set of interfaces which describe the behavior of key
components of the system.  A pure-Java implementation is provided for
each such component.  Thus, the foundry is "portable" in the sense
that a fully functional pure-Java implementation exists.

Currently, the foundry is distributed as a pre-compiled binary distributions.
The pre-compiled binary distributions include a foundry-xxx.jar file, which 
contains all the class files necessary to run the foundry, and several example 
programs.

--------------------------------------------------------------------------
2. Installing a Binary Distribution
--------------------------------------------------------------------------
Pre-requisites: JDK 6, Ant 1.7

The pre-compiled binary distribution contains only those files
necessary to run the foundry.  If you are planning on tinkering with
foundry internals then you should install the raw source distribution. We
plan to make the source available soon. Please contact one of us to have
a peek at the source.

Installing the binary distribution is simply a matter of figuring out
where to put it. Here's an example session (assuming foundry distribution 
is unpacked in /tmp):

> cd /tmp/foundry-local-1.0
> ant

If you encounter any errors here, please try including tools.jar from
$JAVA_HOME/lib into the classpath in build.xml.

See section 3 below for instructions on how to run the foundry.

--------------------------------------------------------------------------
3. Configuring and Running the Foundry
--------------------------------------------------------------------------

Running foundry is just a matter of launching Java VM and providing
information about foundry run-time..
> java -cp lib/foundry-1.0.jar:classes osl.foundry.FoundryStart osl.examples.helloworld.HelloActor hello

The last command loads the foundry run-time, creates the HelloWorld
actor and sends the hello message
to it. Note: The last command uses Unix/Linux syntax for specifying classpath.

Alternatively one can use the following command to view the usage:
java -cp lib/foundry-1.0.jar:classes osl.foundry.FoundryStart

An important switch is "-open" which keeps the ActorFoundry run-time
alive after processing the initial actor/message. This is useful for 
running gui applications and for multi-node actors.

There are a bunch of example actor programs in osl.examples.* to play with.

--------------------------------------------------------------------------
4. Manifest
--------------------------------------------------------------------------

For the pre-compiled binary distribution:

 README            You're looking at it.

 LICENSE           A description of the license restrictions
                   governing the ActorFoundry.

 LICENSE-Kilim     A description of the license restrictions
                   governing the Kilim code that we use internally.

 foundry.conf      A sample configuration file for the foundry.

 lib/              Jar files used for building and some for running
                   actor programs. This includes foundry-xxx.jar which 
                   contains the foundry run-time.

 src/              Contains a bunch of examples programs.

--------------------------------------------------------------------------
5. Where to go for Help/Bug Reports
--------------------------------------------------------------------------

If you need help, want to report a bug, or have suggestions on how the
foundry may be improved, check out the foundry web page at:

       http://osl.cs.uiuc.edu/af

It contains news on the latest release and pointers to applications
written in the foundry.

You can also e-mail the current maintainers of the foundry:

       Rajesh Karmani <rkumar8@cs.uiuc.edu>
       Amin Shali <shali1@cs.uiuc.edu>
       
