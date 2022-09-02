///////////////////////////////////////////////////////////////////////////////
// 
// build.sbt file for Market Models project 
//
// See the README.md file for general information about the project. This file 
// describes the software dependencies for the project and configuration 
// management at the project level. It defines the libraries and version 
// numbers, as well as the particular tasks and directory structures 
// associated with the project. Basically, how it's built, tested, documented, 
// and deployed.
//
// Several of the defined tasks deal with moving files around as part of the 
// overarching documentation task, which produces a collection of HTML pages
// that include code documentation, test results, and other measures. That "web
// site" is intended to be a primary entry point for project stakeholders to
// survey their project's resources.
// 
// Authors: Jamie.Lawson@s3datascience.com
//          Rajdeep.Singh@s3datascience.com
//
// Last Updated: March 18, 2022
//
///////////////////////////////////////////////////////////////////////////////

// ============================================================================
// Standard values needed for the project.
val projectName = "csbook"

fork := true

ThisBuild / organization := "com.s3datascience"
ThisBuild / scalaVersion := "2.13.3"
ThisBuild / version      := "0.1.0-SNAPSHOT"

scapegoatVersion in ThisBuild := "1.4.5"

///////////////////////////////////////////////////////////////////////////////
//
// A multitude of configuration decisions go into a software project. This file
// serves to record those decisions in a formal language (SBT) that the SBT
// processor uses to build project artifacts. In most cases, those project 
// decisions are made from a set of choices, and each such decision is in
// effect a trade study. In order to keep the decisions and justifications in
// the same place, we also use this file to document those trade studies (in a
// natural language, English). Configuration decisions fall into two general 
// categories, one on the input side and one on the output side of production:
//
// 1) COMPONENTS: What off-the-shelf components are we going to bring into our
//    product? In general, these are libraries. We chose one library and not 
//    another. We document those trade studies at the same place where we 
//    formally declare them.
//
// 2) ARTIFACTS: What artifacts do we choose to produce for this product? 
//    Different artifacts will have different audiences, but the choice of
//    artifacts is key to the product's value. There are a number of standard
//    artifacts that SBT can produce. These can be listed with the SBT "tasks"
//    command, and include production of binary files, test results, 
//    documentation, etc. There are also "custom tasks" and the artifacts
//    produced by these are documented in this file.
//
///////////////////////////////////////////////////////////////////////////////

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// COMPONENT CHOICES (LIBRARY DEPENDENCIES)
//
// We generally seek libraries that are well-established industry standards and 
// flexible, meaning amongst other things that they play well in the Scala 
// ecosystem. Typically, if a Scala library achieves the level of "Scala
// standard", it will be adopted and curated by Lightbend (formerly Typesafe),
// which is run by Prof. Martin Odersky, who developed the Scala language. 
// Lightbend is the gold standard for Scala tools. Note that most of the
// Lightbend packages still use "typesafe" in their names.
//
// Some libraries are standard for Java, but not so much for Scala. For 
// instance, in the case of unit testing, the JUnit library is a bedrock 
// standard of the Java community, and it would work with Scala systems as 
// well. But there is also the ScalaTest library that is standard in the Scala 
// community, and designed to work better for Scala systems, which, by the 
// nature of the language, focus testing on other aspects of the system. In 
// that case, we prefer ScalaTest to JUnit.
//
// This leads into performance considerations as well. For instance, we could
// use Java's logging infrastructure without further thought. But there is a 
// performance risk. Consider this fairly common logging issue, where the 
// developer has conscientiously included logging statements at "debugging" 
// level so that if the program experiences a problem while in use, the
// logger can be configured to produce debugging messages that will help to 
// narrow down the problem. Here is a Java code example:
//
//  logger.finest("After update x=" + <computationally costly operation here>);
//
// On one hand, we only log that statement when the logger is configured for 
// "finest" mode and skip it if we are in, say, normal day-to-day "info" mode. 
// But on the other hand, Java does some work with the logger call outside the 
// act of logging the statement. Java uses "eager" evaluation, which means that 
// the operands of a function or method call are evaluated before the function 
// or method is itself evaluated. So in the case of the example call (assuming 
// we are in normal day to day operation at "info" level logging) the
// computationally costly operation is evaluated first, and then we decide 
// not to log the statement because we are operating at the "info" logging 
// level. To reiterate, the expensive computation will be performed even if 
// there is no intent to actually log the action. So Java does the expensive
// calculations, and then looks at the log level and decides not to use those
// calculations. Java programmers get around this by wrapping the log statement 
// in an if statement, as in:
//
//  if (logger.level() >= Logging.FINEST) {
//    logger.finest("...");
//  }
//
// This is not an elegant solution and clutters the code.
//
// Scala allows for "lazy" operations, where the operands are evaluated only 
// when they are used. Consequently, the Java logging system is not very 
// friendly in the Scala ecosystem. To be sure, eager evaluation always gives 
// the right results (though sometimes at huge performance costs), and lazy 
// evaluation only gives the right results when certain conditions hold. These
// are the subject of the field of Functional Programming, and beyond this 
// discussion. But what is clear is that lazy evaluation must be used with 
// care. The ScalaLogging library takes advantage of
// lazy evaluation. In other words, if we were to do the same thing in 
// ScalaLogging, in normal operation at the info level, the logger would look
// at the log level of the statement (finest) and the current logging level 
// (info), and conclude that it didn't need to evaluate the expensive operation 
// because it wasn't going to be logged anyway. So this is one important way in 
// which a library specifically for the Scala ecosystem can outperform one for 
// Java. Or, to put it another way, JUnit is unfriendly in the Scala ecosystem.
//
// As for the "well-established", or "level of adoption" criteria, one 
// important quality is that the Maven dependencies are published in 
// MvnRepository:
//
//   https://mvnrepository.com
// 
// or in MavenCentral:
//
//   https://search.maven.org/
// 
// This eliminates having to manage the dependencies ourselves, and keeping up
// with changing dependencies as the product evolves. In fact SBT calls the 
// Maven (actually Ivy) dependencies "managed dependencies", and there is a 
// whole different SBT infrastructure for dealing with "unmanaged" dependencies.
// We very much want to avoid those "unmanaged" dependencies.
//
// Finally, we are concerned with licensing. The Apache License and fellow 
// travelers (e.g. MIT License) are our ideal target for libraries. Libraries
// released under these licenses are open source, so we can use them without
// financial obligation, and they are not "sticky" or "viral" licenses, so we
// can release our products under whatever license we choose, including 
// proprietary licenses.
//
// ----------------------------------------------------------------------------
// UNIT TEST INFRASTRUCTURE: ScalaTest
//
//    Provider:             Scalatest.org via Artima.com
//    Target Ecosystem:     Scala
//    License:              Apache License version 2.0
//    Adoption Level:       ScalaTest is the best established and most widely
//                          supported library for unit tests in the Scala 
//                          ecosystem.
//    Managed Dependencies: 1) Maven Repository
//                             https://mvnrepository.com/artifact/org.scalatest
//                          2) Scalatest.org
//                             https://www.scalatest.org/user_guide/using_scalatest_with_sbt   
//    Alternatives:         JUnit, ScalaCheck, Specs2
//    Notes:                1) Well supported by IDEs. 
//                          2) Although ScalaTest is Apache licensed, the 
//                             license is not critical for the test framework 
//                             unless we intend to deliver tests to users of 
//                             the product.
//                          3) ScalaTest ropes us into an added dependency for
//                             Scalactic (also an Artima.com product). The 
//                             Scalactic web site is:
//
//                               https://www.scalactic.org/
// 
//                             Scalactic provides some special operators like 
//                             === and !== that are useful in testing, and 
//                             evaluating quality of functional code. Artima 
//                             describes ScalaTest and Scalactic as "sister"
//                             libraries, but seems to have kept the two 
//                             libraries distinct so that other Scala test 
//                             frameworks will adopt the Scalactic tools.
//
//                             On their webpage:
//
//                               https://www.scalatest.org/user_guide/using_scalatest_with_sbt
//
//                             The ScalaTest group also recommends adding their
//                             SuperSafe compiler plugin (Community Edition) to
//                             the test configuration. The SuperSafe web site
//                             is here:
//
//                               https://www.scalatest.org/supersafe
//                             
//                             SuperSafe "checks ScalaTest/Scalactic === and 
//                             matcher expressions for correctness." Their claim
//                             is that "using SuperSafe Community Edition 
//                             together with ScalaTest can save you time and 
//                             ensure certain errors do not exist in your code."
//                             They provide SBT setting and dependency
//                             configurations in that web page.
//
//                             The results of the SuperSafe checks appear to be
//                             published to the (SBT) console window.
//
//                             We have chosen NOT to install the SuperSafe 
//                             compiler plugin AT THIS TIME. The above cited
//                             web page describes how to intall it, and it 
//                             appears to be a couple simple updates to this
//                             build.sbt file. Further, the compiler plugin 
//                             will only be used in test tasks if installed 
//                             according to the Artima instructions. It could
//                             be worthwhile to add SuperSafe. We have not yet
//                             answered that question. 
//
val codeQualityLib = "org.scalactic" %% "scalactic" % "3.2.0"
val unitTestLib = "org.scalatest" %% "scalatest" % "3.2.0" % "test"
// ----------------------------------------------------------------------------
//
// ----------------------------------------------------------------------------
// PRETTY-PRINTED TEST REPORTS: Flexmark-All (markdown parser/processor).
//
//    Provider:             Flexmark.com, of which little is known, although
//                          Atlassian appears to hold copyrights.
//    Target Ecosystem:     Java
//    License:              BSD 2-clause
//    Adoption Level:       Not great. Used by 147 artifacts according to
//                          Maven Repository. But appears to be pretty well
//                          maintained.
//    Managed Dependencies: 1) Maven Repository
//                             https://mvnrepository.com/artifact/com.vladsch.flexmark/flexmark-all
//                          2) Identified in response to an issue on the
//                             ScalaTest github site:
//                             https://github.com/scalatest/scalatest/issues/1736
//    Alternatives:         Test reports to the console only, or plain text
//                          test reports.
//    Notes:                1) This library is used by ScalaTest for making
//                             HTML reports, but is not listed explicitly in
//                             their dependecies, probably because its use is
//                             very specific, or it's an oversight by the
//                             ScalaTest team (see the previously cited
//                             ScalaTest issue on github. So we really don't
//                             have any choice but to use the library if we want
//                             pretty-printed HTML reports from ScalaTest.
//                          2) ScalaTest appears to be the most important client
//                             of this library. Maven Repository reports a
//                             number of products that use the library, but all
//                             except ScalaTest have a very small audience.
//                          3) We are using v.0.35.10. That's from March 2019.
//                             There are lots of newer versions logged in Maven
//                             Repository. The latest at the time of this
//                             writing (July 2020) is release 0.62.2, released
//                             in June 2020. However, by far the greatest number
//                             of clients of this library use v.0.35.10. We
//                             selected that version because of the number of
//                             clients. However, if problems arise with the
//                             library, upgrading to a later version would be
//                             an option. Some of the users on the github issue
//                             page were using later releases. The Flexmark
//                             documentation on github describes "breaking
//                             changes" in version 0.60, which appears to mean
//                             breaking backward compatibility.
//                          4) It appears that earlier versions of ScalaTest
//                             used the Pegdown markdown processor. It's unclear
//                             why they moved away from that processor, although
//                             the Flexmark documentation says that Pegdown has
//                             a great feature set but is slow.
//                          5) Used only to produce test artifacts.
//                          6) The additional test options are there to support
//                             the pretty-printed reports.
//
val flexMark = "com.vladsch.flexmark" % "flexmark-all" % "0.35.10" % "test"
testOptions in Test ++= Seq(Tests.Argument(TestFrameworks.ScalaTest, "-o"), Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/test-reports"))
//
// ----------------------------------------------------------------------------
//
// ----------------------------------------------------------------------------
// LOGGING SYSTEM: ScalaLogging
//
//    Provider:             Lightbend
//    Target Ecosystem:     Scala
//    License:              Apache License version 2.0
//    Adoption level:       ScalaLogging is the best established library for 
//                          logging in the Scala ecosystem, and is more 
//                          efficient than the Java logging system.
//    Managed Dependencies: 1) Maven Repository
//                             https://mvnrepository.com/artifact/com.typesafe.scala-logging
//                          2) Scala-Logging github
//                             https://github.com/lightbend/scala-logging
//    Alternatives:         Java logging
//    Notes:                The logger configuration file, which determines the
//                          interactions of the logger with external systems 
//                          such as enterprise logging, is defined in the file:
//
//                             src/main/resources.logback.xml
//
//                          So important logging decisions may be expressed in
//                          that file. These decisions may have profound impact
//                          on system performance or behavior.
//                   
val loggingLib = "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
// ----------------------------------------------------------------------------
//
// ----------------------------------------------------------------------------
// SBT SCRIPTING: None
//
//    Provider:             None
//    Target Ecosystem:     Scala
//    License:              None
//    Adoption Level:       Typical. SBT Scripting is not usually installed.
//    Managed Dependencies: None
//    Alternatives:         SBT Scripting from scala-sbt.org. See
//                          https://www.scala-sbt.org/1.x/docs/Scripts.html
//    Notes:                We implement SBT scripts using Unix pipes. 
//                          Basically, we put the SBT commands in a text file
//                          to which, by our own local convention, we give the 
//                          extension ".script", and we place these in a 
//                          directory called "scripts", directly under the top
//                          level project directory. We execute these with a 
//                          pipe directive, as in:
//
//                            sbt < scripts/HelloWorld.script
//
//                          That's it. The commands in the script will run by
//                          SBT.
//
//                          The alternative is to install formal SBT Scripting  
//                          as described in the above-mentioned webpage. The big
//                          advantages of formal SBT Scripting are:
//
//                            1) It's more elegant. You don't need the pipes.
//                            2) It's more consistent with the Unix way of 
//                               doing things.
//                            3) It allows for comments in the scripts, using
//                               /*** (three asterisks) to open a comment and 
//                               */ to close a comment. This allows for:
//
//                              i)   Attribution: who to call when something 
//                                   doesn't work.
//                              ii)  Concise discussion of revision history.
//                              iii) Description of the script, its purpose and
//                                   scope.
//                            4) You don't need an 'exit' command to leave SBT
//                               at the end of the script.
//
//                          The disadvantages of formal SBT Scripting are:
//
//                            1) It requires additional installation and 
//                               additional configuration management.
//                            2) One more (actually two or three more) things
//                               to go wrong.
//
//                          Based on the added complexity of formal SBT
//                          Scripting, we elected to use the pipe/redirect
//                          approach instead. We overcome the lack of comments
//                          by providing a web page where each script can be
//                          described.
//       
//                          We may add formal SBT Scripting if the complexity
//                          proves worthwhile. 
// ----------------------------------------------------------------------------

// ----------------------------------------------------------------------------
// DATE TIME LIBRARY:       Nscala-Time
//
//    Provider:             SonaType
//    Target Ecosystem:     Scala
//    License:              Apache License version 2.0
//    Adoption Level:       Typical. Used by Twitter, Apache, etc.
//    Managed Dependencies: None
//    Alternatives:         Java Date, Joda-Time.
//    Notes:                The old Java Date classes have largely been
//                          deprecated. That introduces risks if we were to
//                          use them. And they've been deprecated due to known
//                          issues with the design. In the Java community,
//                          Joda-Time seems to be the way to do date/time
//                          computation. The Nscala-Time library is a more
//                          Scala-friendly way to do date/time in Scala.
//                          and it has a number of large adopters. So we
//                          chose that route.
val dateTimeLib = "com.github.nscala-time" % "nscala-time_2.12" % "2.30.0"
//-----------------------------------------------------------------------------
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

lazy val root = (project in file("."))
  .settings(
    name := projectName,
    libraryDependencies += codeQualityLib,
    libraryDependencies += unitTestLib,
    libraryDependencies += loggingLib,
    libraryDependencies += flexMark,
    libraryDependencies += dateTimeLib
  )


// ============================================================================
// Settings for making a root level Scaladoc for both main and test. 
scalacOptions in (Compile, doc) ++= Seq("-doc-root-content", baseDirectory.value+"/src/main/resources/root-doc.txt")

scalacOptions in (Test, doc) ++= Seq("-doc-root-content", baseDirectory.value+"/src/test/resources/root-doc.txt")
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

scalacOptions in (Compile) += "-deprecation"

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// This stuff deals with moving the resources (graphics, web pages, etc.) 
// for documentation into the scaladocs. It defines where you 
// put your resources and provides a task to move them when you execute the doc 
// task.

//-----------------------------------------------------------------------------
// Task for moving graphics associated with the source code documentation. 
lazy val copyDocGraphicsTask = taskKey[Unit]("Copy doc graphics")

copyDocGraphicsTask := {
  val graphicsSourceDir = file("src/main/resources/graphics")
  val targetDir = (target in (Compile, doc)).value
  val graphicsTargetDir = file(targetDir + "/graphics")
  IO.copyDirectory(graphicsSourceDir, graphicsTargetDir)
  println("Copying documentation graphics") 
}
copyDocGraphicsTask := (copyDocGraphicsTask triggeredBy Compile / doc).value
// ----------------------------------------------------------------------------

// ----------------------------------------------------------------------------
// Task for moving web pages associated with the source code documentation. 
lazy val copyDocAdditionalWebPagesTask = taskKey[Unit]("Copy doc web pages")

copyDocAdditionalWebPagesTask := {
  val pagesSourceDir = file("src/main/resources/webpages")
  val targetDir = (target in (Compile, doc)).value
  val pagesTargetDir = file(targetDir + "/webpages")
  IO.copyDirectory(pagesSourceDir, pagesTargetDir)
  println("Copying documentation web pages") 
}
copyDocAdditionalWebPagesTask := (copyDocAdditionalWebPagesTask triggeredBy Compile / doc).value
// ----------------------------------------------------------------------------  
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

// Exporting Jars tells tools like IntelliJ to look for jars for unmanaged dependencies
exportJars := true
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

