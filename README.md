# Tapestry 5 demonstration application - Hotel Booking

login/pass:
    cordenier/cordenier
    karesti/karesti

More information: http://spreadthesource.com/2010/09/wooki-0-4-is-out-whats-next/

5.4-alpha-8 fail with: (https://hibernate.atlassian.net/browse/HHH-7253)

 Exception constructing service 'RegistryStartup': Error invoking service contribution method com.tap5.hotelbooking.dal.DataModule.initialize(): java.lang.NullPointerException

WARN::Failed startup of context org.mortbay.jetty.plugin.Jetty6PluginWebAppContext@15452556{/tapestry5-hotel-booking,W:\git\tapestry5-hotel-booking\src\main\webapp}
 java.lang.RuntimeException: Exception constructing service 'RegistryStartup': Error invoking service contribution method com.tap5.hotelbooking.dal.DataModule.initialize(): java.lang.NullPointerException
 	at org.apache.tapestry5.ioc.internal.services.JustInTimeObjectCreator.obtainObjectFromCreator(JustInTimeObjectCreator.java:75)


 	Caused by: java.lang.NullPointerException
               	at java.util.concurrent.ConcurrentHashMap.hash(ConcurrentHashMap.java:333)
               	at java.util.concurrent.ConcurrentHashMap.get(ConcurrentHashMap.java:988)
               	at org.hibernate.engine.internal.NaturalIdXrefDelegate$NaturalIdResolutionCache.cache(NaturalIdXrefDelegate.java:454)
               	at org.hibernate.engine.internal.NaturalIdXrefDelegate.cacheNaturalIdCrossReference(NaturalIdXrefDelegate.java:92)
               	at org.hibernate.engine.internal.StatefulPersistenceContext$1.manageLocalNaturalIdCrossReference(StatefulPersistenceContext.java:1769)
               	at org.hibernate.action.internal.AbstractEntityInsertAction.handleNaturalIdPreSaveNotifications(AbstractEntityInsertAction.java:184)
               	at org.hibernate.action.internal.AbstractEntityInsertAction.<init>(AbstractEntityInsertAction.java:75)
               	at org.hibernate.action.internal.EntityIdentityInsertAction.<init>(EntityIdentityInsertAction.java:55)
               	at org.hibernate.event.internal.AbstractSaveEventListener.addInsertAction(AbstractSaveEventListener.java:317)
               	at org.hibernate.event.internal.AbstractSaveEventListener.performSaveOrReplicate(AbstractSaveEventListener.java:287)
               	at org.hibernate.event.internal.AbstractSaveEventListener.performSave(AbstractSaveEventListener.java:193)
               	at org.hibernate.event.internal.AbstractSaveEventListener.saveWithGeneratedId(AbstractSaveEventListener.java:126)
               	at org.hibernate.event.internal.DefaultPersistEventListener.entityIsTransient(DefaultPersistEventListener.java:208)
               	at org.hibernate.event.internal.DefaultPersistEventListener.onPersist(DefaultPersistEventListener.java:151)
               	at org.hibernate.event.internal.DefaultPersistEventListener.onPersist(DefaultPersistEventListener.java:78)
               	at org.hibernate.internal.SessionImpl.firePersist(SessionImpl.java:811)
               	at org.hibernate.internal.SessionImpl.persist(SessionImpl.java:786)
               	at org.hibernate.internal.SessionImpl.persist(SessionImpl.java:790)
               	at $Session_1c80c21d2822.persist(Unknown Source)
               	at $Session_1c80c21d27f5.persist(Unknown Source)
               	at com.tap5.hotelbooking.dal.HibernateCrudServiceDAO.create(HibernateCrudServiceDAO.java:28)
               	at $CrudServiceDAO_1c80c21d2815.create(Unknown Source)