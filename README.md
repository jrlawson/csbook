# csbook
### Computer Science Book Project

This product is the source code for a book on computer science. The code is
written mostly in the **Scala** language. **Scala**
uses the Java Virtual Machine, and so the project relies on both **Scala** and 
**Java**.
 
The project is managed through **SBT**, the "Simple Build Tool", and so it also
relies on **SBT**, which is the standard tool for managing **Scala** projects. 
There are ways to manage **Scala** projects in Maven, but you really don't want 
to go there!

The only firm requirements for using **SBT** are that **Java** and **SBT** be 
installed. **SBT** downloads and configures everything else (except the source
code, of course, which is what this project is about). **SBT** can find the 
right version of **Scala** if it needs to. But that assumes that all of your
**Scala** work is funneled through **SBT**. There are lots of times when you 
want to use **Scala** on its own, without **SBT**, and so it's a really good 
idea to have **Scala** installed as well.

**NOTE:** This page describes how to get your system setup to work on this
project. Some of the things in this description assume that you are developing
on Linux or some other Unix variant, and Windows developers may have to make
certain adjustments.

**Note:** For purposes of illustration, in this set of instructions, we will 
assume that when your web browser downloads a file, it places it in the 
~/Downloads directory. This assumption will almost always hold true, but if
your files get downloaded somewhere else, you'll have to modify the instructions
to correct for that.


## JAVA

**Java** is an ecosystem for computing. It has a virtual machine (the JVM), a
programming language (Java language), standard libraries, and tools. The JVM is
designed so that other programming languages can be compiled to the **Java** 
byte codes that run on the JVM, and there are many such languages. The Clojure
language (based on Lisp) is an example. And there is a JavaScript implementation
in Java (called "Rhino") that runs on the JVM. Probably the most popular 
language--other than Java--for the JVM is **Scala**. For the most part, the only
parts of the **Java** ecosystem we will be using are the JVM and the standard 
libraries, as our programming will be in **Scala**. 

There are two basic ways to use the **Java** ecosystem. Either the Java Runtime 
Environment (JRE), or the Java Development Kit (JDK). To develop for the 
**Java** ecosystem, you really need the JDK. We use **OpenJDK11** for our 
**Java** ecosystem. For pretty much everything after JDK 8, the OpenJDK is the 
gold standard. There are more recent versions than 11, but JDK 11 is the most 
recent version some of our tools support. While those more recent versions would 
probably behave exactly the same, and possibly with slightly better 
performance, for a project that will be deployed in mission critical 
environments, there are good reasons to stick with supported versions. Go here 
for the OpenJDK 11 LTS releases.

https://jdk.java.net/archive/

Download and install the most recent JDK 11 version. In our case, the tar file 
that is downloaded is named openjdk-11.0.2_linux-x64_bin.tar.gz. We like to 
install **Java** by creating an OpenJDK directory under ~/Public, and moving 
the tar file there. The shell command

```
   mv ~/Downloads/openjdk-11.0.2_linux-x64_bin.tar.gz ~/Public/OpenJDK
``` 
will move the tar file to the installation directory. Then switch to the 
OpenJDK directory using the shell command

```
   cd ~/Public/OpenJDK
```

and then untar the file. For instance, if you are using our version, 
issue the command (assuming Linux)

```
   tar xvf openjdk-11.0.2_linux-x64_bin.tar.gz
```

You will need to update certain environment variables in order for the JDK to 
work properly. We use the following statements in our ~/.bashrc file to set 
these environment variables:

```
###############################################################################
# Java Stuff
###############################################################################
JAVA_ROOT=~/Public/OpenJDK
JAVA_VERSION=jdk-11.0.2
JAVA_HOME=$JAVA_ROOT/$JAVA_VERSION
PATH=$JAVA_HOME/bin:$PATH 
```

Once these changes are made to your .bashrc file, you can issue the command 

```
source ~/.bashrc
```

To set all of those changes in the current terminal. Other terminal windows that
are open will also need to re-source the .bashrc file if they need this update.
New terminal windows will automatically start with the updated settings. After 
that, you can check to insure that the right version of Java is setup by 
entering the command

```
which java
```

That should return something like

```
/home/<username>/Public/OpenJDK/11.0.2/bin/java
```

You can also enter the command 

```
java -version
```

to insure that you are using the expected version. That should give you a result
something like:

```
openjdk version "11.0.2" 2019-01-15
OpenJDK Runtime Environment 18.9 (build 11.0.2+9)
OpenJDK 64-Bit Server VM 18.9 (build 11.0.2+9, mixed mode)
```

As for Java updates, when the tools support a new version of Java, you may have
to install that version and update the .bashrc file.
 
 
## SCALA

Scala is an object-functional programming language that runs on the Java Virtual
Machine, and the central clearinghouse for Scala stuff is 

http://www.scala-lang.org

To download the current binaries you can go to this page:

https://www.scala-lang.org/download/

Installation is similar as for **Java**. In your ~/Public directory create (if 
it doesn't already exist) a Scala directory. Move the binary that you downloaded 
into that directory. As of this writing, we are using Scala version 2.13.3.
Once more, there are more recent versions, including the Scala 3.1 version, but
not all of our tools support that. We will use the 2.13.3 version as our example
for illustrations.

So issue the shell command

```
   mv ~/Downloads/scala-2.13.3.tgz ~/Public/Scala
```

Then switch to that directory with the shell command

```
   cd ~/Public/Scala
```

From that directory, untar the file. For example,

```
tar xvf scala-2.13.3.tgz
```

Again, you will have to set or update certain environment variables. We use the 
following statements in our .bashrc file to do that:

```
###############################################################################
# Scala Stuff
###############################################################################
SCALA_ROOT=~/Public/Scala
SCALA_VERSION=2.13.3
SCALA_HOME=$SCALA_ROOT/scala-$SCALA_VERSION
PATH=$SCALA_HOME/bin:$PATH 
```

Issuing the command

```
source ~/.bashrc 
```

will make those changes take effect. Once again, you will want to insure that 
the environment variables were set properly. Issue the command

```
which scala
```

That should give something like:

```
/home/<username>/Public/Scala/scala-2.13.3/bin/scala
```

You can also issue the command:

```
scala -version
```

which should yield something like:

```
Scala code runner version 2.13.3 -- Copyright 2002-2020, LAMP/EPFL and Lightbend, Inc.
```

Note that Scala 3 will require some slight modifications to the instructions
here for the .bashrc file. This line, in particular
```
SCALA_HOME=$SCALA_ROOT/scala3-$SCALA_VERSION
```

adds the '3' to "scala".


## SBT


**SBT** is a build automation and version maintenance tool like Maven, except it 
uses a powerful Domain Specific Language (DSL) based on **Scala**. One of the 
things that makes **Scala** such a powerful language is that you can write code 
in **Scala** that extends the language, giving it additional grammar. This is 
often done to create a language dedicated to solving problems in a specific, 
narrow domain (hence the term "Domain Specific Language"). **SBT** is a DSL 
whose domain is managing software projects.
 
**SBT** can be used to manage projects written in lots of different programming
languages, but it's really best suited for projects written in **Scala**, and
nearly all serious **Scala** projects are managed with **SBT**. 

Installation instructions for **SBT** can be found here:

https://www.scala-sbt.org/release/docs/Setup.html

On Linux, **SBT** can be installed through apt-get. The basic command is

```
sudo apt-get install sbt
```

However, you may find that you need to add an apt-get repository, so it's not 
quite that simple. If the apt-get command fails, this page on the **SBT** site 
gives the commands to add the required repository:

https://www.scala-sbt.org/release/docs/Installing-sbt-on-Linux.html

Follow those directions and then try the apt-get command again. 

And be aware that for several years, the gold standard version of **SBT** was
0.13. That is no longer the case and if you use the wrong **SBT**, things will
not work out well, and much of the documentation on the Internet hasn't been 
updated for **SBT** version 1.x. **SBT** is not really backward compatible. 
It can be confusing, and lots of stuff that worked when those web pages were
written doesn't work with 1.x.

Once apt-get installs **SBT** you can confirm that you have the right version 
with the command

```
sbt -version
```

It might spin its wheels for a moment because **SBT** uses a "lazy" installer
that doesn't actually download the binary content until it is used, but 
eventually you should get a response like:

```
sbt version in this project: 1.3.13
sbt script version: 1.3.13
```

That's the current version as of the time of this writing.

As far as configuration management, if you've gotten this far, you should be 
"ready to go" for some definition of "go". **SBT** knows how to find and use the 
right version of Scala on a project-specific basis. We probably won't need it 
but it will handle **Scala** requirements from here on out. And it will also 
download any/all of the Scala packages that the project depends on. At least the
so called "managed dependencies", and all of our dependencies at this point are
managed (see the **build.sbt** file for a discussion of the difference between
managed and unmanaged dependencies. From here on out, **SBT** will shoulder the
burden of configuration management at the project level (libraries that the
project needs, libraries that those libraries need, etc.). It's all in the 
**SBT** control file called **build.sbt** which we describe in subsequent 
sections, particularly the **SBT Tutorial** section.


## DEVELOPMENT ENVIRONMENT (INTELLIJ)

If you are not developing on the project, you might be able to do everything you
need to do through **SBT** and a web browser. That's one advantage of using a 
powerful build tool like **SBT**. But if you really want to explore the code and
see what it's doing, you will need a development environment, usually called an
"Integrated Development Environment" or IDE.

There are two popular IDEs for **Scala**. These are **Eclipse** and 
**IntelliJ**. **IntelliJ** appears to have gained the lead, and works better 
with the current versions of **Scala** and **SBT**. So we will give instructions
here for getting setup with **IntelliJ**. If you prefer other IDEs, that's fine,
but you will be on your own for this part of the setup.

**IntelliJ** is an advanced IDE from JetBrains. It had a loyal following for 
years but failed to become dominant because it wasn't free like **Eclipse**
and NetBeans. But **IntelliJ** is now freely available, and is very powerful,
particularly for dealing with **Scala** projects. You can download **IntelliJ**
from the following web page:

https://www.jetbrains.com/idea/

There is a download button in the upper-right corner. The "Community Edition" is
fine. After the file has downloaded, open a shell, and create an 
IntelliJ directory beneath ~/Public, and switch to that directory. Move the 
downloaded file there. At the time of this writing, that file is called

```
   ideaIC-2020.1.3.tar.gz
``` 

So we'll use that name in our examples. The shell command to move the file would
be

```
   mv ~/Downloads/ideaIC-2020.1.3.tar.gz ~/Public/IntelliJ
```

If your version is slightly later, use the name of that file. From a shell, 
switch to the ~/Public/IntelliJ directory, and unpack that file with the shell 
command

```
   tar xvf ideaIC-2020.1.3.tar.gz
```

This will create a bunch of new files and directories under the current 
directory, but it will not actually do the installation. It will create a 
direcory for the current version. In our case, that directory is called

```
   idea-IC-201.8538.31
```

Your directory name will probably be close to that. Change to that directory.
Under that directory is a bin directory. Change to that directory. There is a
shell script in the bin directory called idea.sh. Execute that shell script with
the command

```
   ./idea.sh
```

This will run the installer, which is actually pretty fast. You will be asked
a couple of configuration questions, but you can just accept the defaults. When
**IntelliJ** is installed, exit out and back to the command line. You can add
**IntelliJ** to the launcher (assuming you are using Ubuntu or some other
Gnome-based Linux distribution) by clicking on the Ubuntu icon at the top of the
launcher bar, and in the search box typing IntelliJ. You will see the 
**IntelliJ** icon, which you can point to, click on and pull to the desired spot
on the launcher bar. 

Now for a first test of **IntelliJ**, click on the launcher icon you just 
installed. **IntelliJ** should launch, and ask you if you want to create a new
project. If it takes you directly to the **IntelliJ** console, you can select
File->New from the main menu. In either case, you want to create the new project
from revision control, which is one of the options. That is actually very 
convenient. If you select that option, you will be prompted for a version
control URL. For this project, that URL is:

```
   git@github.com:jrlawson/market-models.git
```

If you give that URL, **IntelliJ** will try to clone this project (including 
this file) in a market-models directory beneath the ~/IdeaProjects 
directory, and then **IntelliJ** will find the **build.sbt** file, ingest it, 
and build the project around that project description. You will likely also be asked to 
install a Scala SDK at that time. Say 'yes' to that. It will take a few seconds
for **IntelliJ** to completely swallow the project, but it is a lot easier than
doing all of the setup by hand. To be sure, this is one place where things could
go wrong if your **github** credentials aren't just right. A common response from 
**IntelliJ** at this point is that the repository cannot be cloned because you 
lack proper access privileges. When this happens, it's not a problem with 
**IntelliJ**, it's more likely to be a problem with the public key credentials
setup in your **github** account. We cover that in the next, brief section.

Setting up **IntelliJ** in this way does not require any changes to your .bashrc
file. 


## GITHUB ACCOUNT

If you were not able to clone the repository in the last step we did, it is 
probably because you either don't have a **github** account, or your **github**
account is not setup to communicate securely with the specific machine you are
working on. Let's repeat that. **Github** uses two distinct levels of security:

1) **Password Protection**: You need to have a **github** account with a unique
username and secure password. That will get you in, but it won't let you do
anything.
2) **Secure Shell Protection**: You need to configure **github** with the SSH 
public key established for the particular machine you are working on. 

The second level causes more problems. You can provide **github** with multiple
keys if you are working on multiple machines, but you will need to add your GPG 
keys to your **github** account, one for every machine (including virtual machines) 
that you want to work on the project from. Basically, **github** needs to verify 
that it's really you, whenever you are doing something with the project, and 
that communication is secure, so it uses SSH and the keystore on your machine. 

This **github** page describes how to add the GPG key to your **github** account:

https://docs.github.com/en/github/authenticating-to-github/adding-a-new-gpg-key-to-your-github-account

It falls a bit short because it doesn't really explain how to copy it to your 
clipboard. There's another **github** page about generating your GPG key that 
goes most of the way there:

https://docs.github.com/en/github/authenticating-to-github/generating-a-new-gpg-key

However, that still falls a bit short because the actual _copying_ of the GPG 
key can be a bit tricky, and that is swept under the rug by that **github** page. 
Most people use xclip to copy their GPG key. So there is yet another **github**
page that describes how to do that:

https://docs.github.com/en/github/authenticating-to-github/adding-a-new-ssh-key-to-your-github-account

At this point, assuming you have configured your **github** account with your 
GPG keys, try creating the project through **IntelliJ** again and use the 
"Clone from VCS" option again. If that still fails, try cloning from the command
line. To do so, go to the ~/IdeaProjects directory (make sure it exists, or
create it if it doesn't).

Switch to that directory with the shell command

```
   cd ~/IdeaProjects/
```

and clone the repository with the shell command

```
   git clone git@github.com:jrlawson/market-models.git
```

That will create a directory called market-models, and put the files for
this project in that directory.
 
If that works, the problem is in your **IntelliJ** configuration. If it doesn't
work, the problem is in your **github** configuration.


## VALIDATION CHECKS

There's a lot of infrastructure to get right, and so we've provided a small 
validation check to make sure that we are okay. From the root directory
of the project (~/IdeaProjects/market-models), execute the following command:

```
sbt < scripts/HelloWorld.script
```

This will test to see if **Java**, **Scala**, and **SBT** are all okay and 
working together, and that at least some of the project files are in place. 
Earlier, we validated that **Java**, **Scala**, and **SBT** were each working
independently. This test will validate that they are working together, and with
this project. If all is well, this command should produce output something 
like this, and then return to the command prompt:

```
  [info] welcome to sbt 1.3.13 (Oracle Corporation Java 11)
  [info] loading settings for project global-plugins from plugins.sbt ...
  [info] loading global plugins from /home/<username>/.sbt/1.0/plugins
  [info] loading project definition from /home/<username>/IdeaProjects/market-models/project
  [info] loading settings for project root from build.sbt ...
  [info] set current project to market-models (in build file:/home/<username>/IdeaProjects/market-models/)
  [info] sbt server started at local:///home/<username>/.sbt/1.0/server/87dcb5ccda00536577ba/sock
  sbt:market-models> run / mainClass := Some("com.s3datascience.sample.HelloWorld")
  [info] Compiling 1 Scala source to /home/<username>/IdeaProjects/market-models/target/scala-2.13/classes ...
  [info] Done compiling.
  [info] running (fork) com.s3datascience.sample.HelloWorld / mainClass := Some("com.s3datascience.sample.HelloWorld")
  [info] Hello, world!
  [success] Total time: 3 s, completed Jul 12, 2020, 2:17:21 PM
  sbt:market-models> exit
  [info] shutting down sbt server

```

Once again, this test is to validate the infrastructure, not to validate the
market-models system. We'll get to that soon enough.

We have also provided a validation test to insure that the test environment is
working, at least in the "water through the pipes" sense. If you run the
following program from the shell, in the project root directory:

```
sbt < scripts/HelloWorldTest.script
```

It will exercise the test system and run exactly one test, which should succeed.
The only purpose of this script is to validate that the test components are in
place and able to function. It isn't about testing the market-models 
service, only the infrastructure. The results should look like this:

```
  [info] welcome to sbt 1.3.13 (Oracle Corporation Java 11)
  [info] loading settings for project global-plugins from plugins.sbt ...
  [info] loading global plugins from /home/<username>/.sbt/1.0/plugins
  [info] loading project definition from /home/<username>/IdeaProjects/market-models/project
  [info] loading settings for project root from build.sbt ...
  [info] set current project to market-models (in build file:/home/<username>/IdeaProjects/market-models/)
  [info] sbt server started at local:///home/<username>/.sbt/1.0/server/87dcb5ccda00536577ba/sock
sbt:market-models> testOnly com.s3datascience.test.sample.HelloWorldTest
  [info] HelloWorldTest:
  [info] HelloWorld
  [info] - should return "Hello world!"
  [info] Run completed in 724 milliseconds.
  [info] Total number of tests run: 1
  [info] Suites: completed 1, aborted 0
  [info] Tests: succeeded 1, failed 0, canceled 0, ignored 0, pending 0
  [info] All tests passed.
  [success] Total time: 1 s, completed Jul 13, 2020, 12:46:56 PM
sbt:market-models> exit
  [info] shutting down sbt server
```

We're still not ready to do real work, because we need to validate that 
**IntelliJ** is working with these other tools.

From inside the project browser on the left of the **IntelliJ** console, browse
to the file 

```
   src/main/Scala/com/s3datascience/sample/HelloWorld.scala
```

That file should appear in an editor window, and the code should have nice
syntax highlighting. From the main menu, select Run->Run... and then select 
HelloWorld. In the Run pane at the bottom of the **IntelliJ** console, the 
following should be printed out:

```
   Hello world!

   Process finished with exit code 0

```

If that's what you get, then **IntelliJ** is working with **Java**, **Scala**,
**SBT**, and with this project. And **IntelliJ** has its own SBT console, and
its own shell. Further, **IntelliJ** has a git console so you can do all of the
normal version control operations as well. So you can do pretty much everything 
that you want to do on this project from within **IntelliJ**.

At this point, finally, we are able to do some work! So let's talk about the
project's structure.


## PROJECT STRUCTURE

The root directory of the project is named **market-models**. If you are 
using **IntelliJ**, that project directory should be

```
~/IdeaProjects/market-models
```

You can clone this git repository into that directory. From there on down, the
project structure is managed by **SBT**. **SBT** uses the Maven directory 
structure for files. So you will have the following "Mavenesque" structure:

```
   src/
      main/
      test/
   target/
```

This is just a partial view. Call it the "Maven" view. The "main" directory is
where the source code for the project goes. The "test" directory is where the 
validation tests (unit tests, integration tests, etc.) go. The "target" 
directory is where the build artifacts go. There are other directories that 
**SBT** provides by default. There is a "project" directory that **SBT** uses as
a sort of scratch pad. Things like library binaries go there. Beneath the 
"target" directory, **SBT** will put directories for all of the different 
**Scala** versions that the project builds for. So you might end up with a
directory structure something like this:

```
   src/
      main/
         scala/
            com/
               s3datascience/
                  ...
      test/
         scala/
            com/
               s3datascience/
                  ...
      target/
         scala-2.12
            ...
         scala-2.13
            ...
``` 
   
The file that describes and manages **SBT** configuration is **build.sbt** in 
the project's root directory, along side this **README.md** file. The 
**build.sbt** file describes all of the project dependencies.

We add some things to the structure. For instance, we add a "resources" 
directory beneath src/main, and another beneath src/test.

The project uses **Scala Logging**, which is a highly configurable logger. The
logger configuration is managed through the file

```
     src/main/resources/logback.xml
```
That file relies on Unix directory structures, and so this is one place where 
Windows developers will need to make some adjustments.

There is a "scripts" directory at the top level of the project to run useful 
scripts. 

There are also webpages and graphics directories beneath src/main/resources. 
These directories hold website content that serves as an entry point to overall
documentation for the system. These resources are copied into the target
directory when documentation is generated. 

## IMPORTANT FILES
There are several important files at the top level of the repository.

### README.md
This file. A well-behaved **git** repository will have a **README.md** file that
describes the project, its parts, and how to get it working.

### .gitignore
The **.gitignore** file at the top of the project directory tells **git** which 
files in your working directory to ignore for purposes of revision control. For 
instance, if we committed the **IntelliJ** project files to the shared repository, 
every time a developer pushed their changes, it would screw up every other 
developer the next time they pulled an update.

The entire **target** directory is ignored by **git**, firstly because the 
target directory is **SBT**s responsibility, and secondly because we want the 
target artifacts to be pure and independent of anything except the source. The 
target directory will strictly be populated by **SBT** tasks.

Backup files in various naming formats are ignored by **git** for obvious 
reasons, as are some other scratch files used by the build system.

### build.sbt
The **build.sbt** file describes how the files in the project work together, and
what the tasks of the project are. It's basically the roadmap for the project.
Changes to this file need to be carefully considered because they impact the 
whole project, and without a working **build.sbt** file, no progress can be 
made. The **build.sbt** file is described in greater detail in the 
**SBT Tutorial**.

The **build.sbt** file is where our choices of support libraries are made 
concrete, an so it is an appropriate place to lay out the rationale for making
the choices that we have. If you are concerned or curious about our choices of
support libraries, inspect the **build.sbt** file and it will explain each 
choice in a comment associated with the library declaration.


## SBT TUTORIAL

**SBT** is quite powerful. See the **SBT** documentation at

   https://www.scala-sbt.org/1.x/docs/index.html
     
for more information. To use **SBT** run **SBT** commands from the root
directory of the project where the **build.sbt** file is. **SBT** looks for 
the **build.sbt** file, and if it finds it, it processes tasks on that
project. The command 

     sbt tasks
     
gives a list of project tasks.

There are two standard tasks for producing project documentation.

     sbt doc
     
produces Scaladocs for the **market-models Service**, including an 
overall project description at the root level of the Scaladocs. That 
documentation will be produced in the subdirectory **target/scala-2.13/api** of 
the project, and the entry point (which should be bookmarked in your browser)
is the **index.html** file. 

Likewise, the command

     sbt test:doc
     
produces test documentation including the test plan, and the root of that 
documentation is the subdirectory **target/scala-2.13/test-api** of the 
project's root directory. The entry point to the test documentation (which
should also be bookmarked in your browser) is the **index.html** file of that
directory.

This project is setup to allow some flexibility in the documentation. there is
a source directory 

     src/main/resources/graphics
     
where graphics files can be placed during development. Those files are copied 
into the **target/scala-2.13/api/graphics** directory when the **sbt doc** 
task is invoked. So developers can include a graphic from **src/main/resources/graphics**
in their Scaladocs with an "img" tag as follows:

     <img src="graphics/MyImage.gif">
     
Similarly, for test documentation, graphics and images go into the subdirectory
**src/test/resources/graphics** and a test developer wishing to include one of 
those graphics in their test documentation can do so identically as shown above. 
There are two separate places where graphics (or other supplementary material 
goes (one in **src/main** and the other in **src/test** but they are referred 
to in the same way).





## ECLIPSE (DEPRECATED MATERIAL)

**Eclipse** has not kept up with the **Scala** ecosystem as well as **IntelliJ**.
But if you prefer to use **Eclipse**, we've provided some material on getting
started. You will probably find that **Eclipse** requires you to use an older
version of **Scala** (2.12), but this material may be helpful if that's the way
you choose to go. 

The current version of **Eclipse** at this time is version 2020-06 (aka 4.16.0). 
This will likely change, but the installation instructions are not likely to 
change very much (though they have changed recently, so it's not beyond the 
scope of possibility).

The **Eclipse** web site

https://www.eclipse.org/

is the central routing point for things about eclipse. If you go to that 
website, there is a button in the top right of the page to download **Eclipse**.
Click that button and go through the instructions and agree to the licenses to 
get the download file, which isn't **Eclipse** itself, but an installer for 
**Eclipse**. You will get a tar file called eclipse-inst-linux64.tar.gz, at 
least if you are using Linux, which we assume here. Note that, unlike for the
Java and Scala tools, there is no indication of the specific version number in 
that filename. Whenever you update **Eclipse** you will download a file of that
name, but you may get a very different file from one time to another, and each
one will likely overwrite the previous one. 

Go to the Eclipse directory of your ~/Public directory, or create one if it
doesn't already exist, and move the tar file there. Now from the 
~/Public/Eclipse directory, type the command

```
tar xvf eclipse-inst-linux64.tar.gz
```

That will create a directory called ~/Public/Eclipse/eclipse-installer. Change
to that directory and launch the installer with the following command:

```
./eclipse-inst
```

There are lots of options for **Eclipse** installation. You will want the 
"Eclipse for Java" option, Click on it and follow the prompts. That will 
install **Eclipse**. It will ask you which Java version you want to use, and it
will suggest the standard system version that you've already setup.

Like with the other tools we have installed, you will need to update some 
environment variables. We do this by adding the following lines to our .bashrc
file.

```
###############################################################################
# Eclipse Stuff
###############################################################################
ECLIPSE_ROOT=~/Public/Eclipse
ECLIPSE_VERSION=java-2020-06
ECLIPSE_HOME=$ECLIPSE_ROOT/$ECLIPSE_VERSION
PATH=$ECLIPSE_HOME/eclipse:$PATH
```

Now, if you issue the command

```
source ~/.bashrc
```

The environment variables will be updated and you'll be able to use **Eclipse**.

**Eclipse** will want to put projects in the directory ~/eclipse-workspace. 
That's a good default, and accepting that default will save confusion later,
for instance, when some piece of documentation assumes that you are using the
default.

But we aren't done. We configured **Eclipse** for **Java** (which is necessary),
but we still need to configure it for **Scala**. The next section describes this.


## SCALA IDE FOR ECLIPSE

The **Scala IDE for Eclipse** is an add-on to **Eclipse** to make it work with
**Scala**. It's quite powerful, and without it, **Eclipse** doesn't even know
how to display **Scala** code. 

To install the **Scala IDE for Eclipse** launch **Eclipse** with the command

```
eclipse
```

Then go to the Help->Eclipse_Marketplace menu. This will allow you to search the
**Eclipse** ecosystem for add-ons. The marketplace has a search function. Use it
to search for "scala". The options will include **Scala IDE for Eclipse**. 
Follow the prompts to install the **Scala IDE for Eclipse**. This will take a 
few minutes. Once the **Scala IDE for Eclipse** is installed, you will need to
exit out of **Eclipse** and restart it for **Eclipse** to pick up the new 
add-ons, but now, you can work with **Scala** code in **Eclipse**.

But, we _still_ aren't done. We still need to make **Eclipse** play nice with
**SBT**. Left to itself, **Eclipse** doesn't know anything about **SBT**, and
**Eclipse** has its own way of creating and managing projects. So we need a tool
for **Eclipse** to ingest the **build.sbt** file and create a project from it. 
That tool is **SBT-eclipse**, and the next section deals with its installation  
and configuration. 

Note that this does _not_ add **Eclipse** to the launcher. There are ways to do
that, but we will not go into that here. We will assume that you launch 
**Eclipse** from the command line.


## SBT-ECLIPSE (DEPRECATED MATERIAL)

**SBT-Eclipse** is a "plug-in" for **SBT**. It is curated by Lightbend, which is
the most robust and secure source for Scala tools. Lightbend was founded by 
Prof. Martin Odersky, who invented the Scala language. It used to be called 
Typesafe, and the two names are used interchangeably. So anything with the names
Lightbend or Typesafe on them can generally be considered reliable. The 
**SBT-Eclipse** project is distributed through github at the following page. 

https://github.com/sbt/sbteclipse/

However, because **SBT-Eclipse** is an **SBT** plug-in, you don't need to
download or install anything. Instead, **SBT** manages it just it manages other
libraries. That is, somewhere, you describe the plug-in, and its version 
information, and **SBT** goes out and gets it when it's needed. The 
**SBT-Eclipse** plug-in then serves to extend **SBT** by giving it another named
task. We will talk about **SBT** tasks in a moment, but for now, it's enough to
say that once the **SBT-Eclipse** plug-in is installed, you can create an 
**Eclipse** project from **SBT** usig the eclipse task, which can be executed 
from the command line as follows:

```
sbt eclipse
```

But you still need to make **SBT** aware of the **SBT-Eclipse** plug-in.
The **SBT-Eclipse** plug-in can be declared in a couple of places, and the 
README.md file at the **SBT-Eclipse** github page describes the options. The
basic choices are to make it project-specific, or always available to **SBT**.
Usually, it is made to be always available to **SBT**. To do this, you add the
plug-in to the system-level **SBT** configuration file, which is in a (hidden)
directory just below your user home directory. That directory is ~/.sbt

Change to that directory in a terminal window, then list its contents with ls. 
There will be a "boot" directory, and then a directory for each of the versions 
of **SBT** that the system has been configured for. We are interested in the 
1.0 version. So change to that directory, and list its contents with ls. You 
should see a "plugins" directory. Change to that directory. So at this point,
your current directory should be

 ~/.sbt/1.0/plugins

There's more stuff in that directory, but the thing you are interested in is the
file named **plugins.sbt**. That's a text file. In fact, it's an **SBT** file. 
Open that file in your text editor and add the following line to the end of the
file and then save the file:

```
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "6.0.0-SNAPSHOT")
```

In all likelihood, that line is the only line in your **plugins.sbt** file. 
Once you've saved that change to your **plugins.sbt** file, you have now
configured your system so that **SBT** has the "eclipse" available to it.
This command takes a few seconds, and lots of whirring and buzzing goes on, but
when finished, a bright shiny **Eclipse** project will be there, ready to be
imported. Yes, there's still more to do. We need to import the project into 
**Eclipse**. 


## IMPORTING THE SBT PROJECT INTO ECLIPSE (DEPRECATED MATERIAL)

To import the project you created with **SBT-Eclipse**, launch **Eclipse**. At
this point, the eclipse command from the command line should do that, or if you 
have added it to your launcher, you can launch **Eclipse** that way). From the 
main **Eclipse** menu, issue the command:

   File -> Import -> "Existing Projects into Workspace"
   
And then proceed with "Next". You will see a dialog box for identifying the
directory where the project is. It will say "Select root directory". In that 
dialog box, navigate to 

   eclipse-workspace/market-models

That will populate the "Projects" list in the lower part of the dialog box. The
market-models project should be selected, and you can press the "Finish" 
button at the very bottom of the dialog box. 

Later, we will describe the **build.sbt** file, which describes the project and
its artifacts. That file doesn't change very often, but when it does, you will
have to rerun the 

```
sbt eclipse
```

command to synchronize **Eclipse** with the project.
