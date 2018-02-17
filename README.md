# Apache Tapestry: Hotel Booking Simulation

This is the source code to the "Hotel Booking" Tapestry demo app, currently
running with Tapestry 5.4 at:

	https://tapestry-app.apache.org/hotels/

This is Tapestry's take on a classic "Hotel Booking" application (ala Seam). It
demonstrates:

* Simple page navigation
* Custom components
* Form validation and submission
* Session management (including a shopping-cart-like mechanism)
* Partial-page updates (AJAX)
* Localization (English & Spanish)
* Form fragments
* Easy annotation-based authentication/authorization

Building and running the app is easy. It uses Java 8, maven and jetty, so you
just need to do:

    mvn jetty:run

or you can do `mvn package` and deploy the resulting war file to the servlet
container of your choice, such as Tomcat 7.

The following are two built-in accounts (usernames / passwords) that you can
use to log into the app, or you can create another account by registering with
the link on the login page.

* gosling/gosling
* fowler/fowler
