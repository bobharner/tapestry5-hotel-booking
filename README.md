# Tapestry 5 demonstration application - Hotel Booking

This is the source code to the "Hotel Booking" Tapestry demo app, currently
running at:

	http://tapestry.zones.apache.org:8180/tapestry5-hotel-booking

This is Tapestry's take on a classic "Hotel Booking" application (ala Seam). It
demonstrates simple page navigation, form validation and submission, session
management (including a shopping-cart-like mechanism), and easy annotation-based
authentication/authorization.

Building and running the app is easy. It uses maven and jetty, so you just need
to do:

    mvn jetty:run

or you can deploy it to the servlet container of your choice.

The following are two built-in accounts (usernames / passwords) that you can
use to log into the app, or you can create another account by registering with
the link on the login page.

* cordenier/cordenier
* karesti/karesti

