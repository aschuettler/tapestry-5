Scratch pad for changes destined for the 5.5 release notes page.

# Java 8 required
The minimum Java release required to run apps created with Tapestry 5.5 is Java 8.

# Java 8, 9, 10 and 11 supported

# Updates to embedded Tomcat and Jetty versions (TAP5-2548)
With Java 8, we made the switch to servlet-api 3.0. We updated the embedded Tomcat and Jetty containers to the respective versions. Unfortunately, we had to rename Jetty7Runner to JettyRunner and Tomcat6Runner to TomcatRunner in the tapestry-runner package.

# Classpath asset protection (actually added in 5.4.4)
A new service, `ClasspathAssetProtectionRule`, which receives contributions of `ClasspathAssetProtectionRule`
instances, was created to you can easily add rules to block requests to classpath assets according to your 
security needs. Three rules are added
out-of-the-box and may be overriden:
* `ClassFile`: blocks access to assets with `.class` endings (case insensitive).
* `PropertiesFile`: blocks access to assets with `.properties` endings (case insensitive).
* `XMLFile`: blocks access to assets with `.xml` endings (case insensitive).