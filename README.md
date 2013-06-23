# Tapestry 5 demonstration application - Hotel Booking

http://tapestry.zones.apache.org:8180/tapestry5-hotel-booking/signin
http://localhost:8080/tapestry5-hotel-booking/signin

login/pass:
    cordenier/cordenier
    karesti/karesti

More information: http://spreadthesource.com/2010/09/wooki-0-4-is-out-whats-next/
https://github.com/ccordenier/tapestry5-hotel-booking/

5.4-alpha-9 fail with: (http://localhost:8080/tapestry5-hotel-booking/book)

org.apache.tapestry5.ioc.internal.OperationException

trace

        Handling page render request for page Book
        Triggering event 'activate' on Book

org.apache.tapestry5.runtime.ComponentEventException

context
eventType
    activate

java.lang.NullPointerException
                       Filter Frames?
                       Stack trace:

                           com.tap5.hotelbooking.pages.Book.advised$setupBooking_130a3fb5ac5ccb2e(Book.java:115)
                           org.apache.tapestry5.internal.plastic.AbstractMethodInvocation.proceed(AbstractMethodInvocation.java:84)
                           org.apache.tapestry5.ioc.internal.services.LoggingAdvice.advise(LoggingAdvice.java:45)
                           org.apache.tapestry5.internal.plastic.AbstractMethodInvocation.proceed(AbstractMethodInvocation.java:86)
                           com.tap5.hotelbooking.pages.Book.setupBooking(Book.java)
                           com.tap5.hotelbooking.pages.Book.advised$dispatchComponentEvent_130a3fb5ac5ccb26(Book.java)
                           org.apache.tapestry5.internal.plastic.AbstractMethodInvocation.proceed(AbstractMethodInvocation.java:84)
                           org.apache.tapestry5.internal.services.ComponentInstantiatorSourceImpl$EventMethodAdvice.advise(ComponentInstantiatorSourceImpl.java:462)
                           org.apache.tapestry5.internal.plastic.AbstractMethodInvocation.proceed(AbstractMethodInvocation.java:86)
                           org.apache.tapestry5.internal.services.ComponentInstantiatorSourceImpl$EventMethodAdvice.advise(ComponentInstantiatorSourceImpl.java:462)
                           org.apache.tapestry5.internal.plastic.AbstractMethodInvocation.proceed(AbstractMethodInvocation.java:86)
                           com.tap5.hotelbooking.pages.Book.dispatchComponentEvent(Book.java)
                           org.apache.tapestry5.internal.structure.ComponentPageElementImpl.dispatchEvent(ComponentPageElementImpl.java:931)
                           org.apache.tapestry5.internal.structure.ComponentPageElementImpl.processEventTriggering(ComponentPageElementImpl.java:1116)
                           org.apache.tapestry5.internal.structure.ComponentPageElementImpl$5.invoke(ComponentPageElementImpl.java:1061)
                           org.apache.tapestry5.internal.structure.ComponentPageElementImpl$5.invoke(ComponentPageElementImpl.java:1058)
                           org.apache.tapestry5.internal.structure.ComponentPageElementResourcesImpl.invoke(ComponentPageElementResourcesImpl.java:145)
                           org.apache.tapestry5.internal.structure.ComponentPageElementImpl.triggerContextEvent(ComponentPageElementImpl.java:1057)
                           org.apache.tapestry5.internal.structure.InternalComponentResourcesImpl.triggerContextEvent(InternalComponentResourcesImpl.java:302)
                           org.apache.tapestry5.internal.services.PageActivatorImpl.activatePage(PageActivatorImpl.java:34)
                           org.apache.tapestry5.internal.services.PageRenderRequestHandlerImpl.handle(PageRenderRequestHandlerImpl.java:60)