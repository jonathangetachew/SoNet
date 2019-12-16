# SoNet [![CircleCI](https://circleci.com/gh/jonathangetachew/SoNet.svg?style=svg)](https://circleci.com/gh/jonathangetachew/SoNet)
A Social Network application for our Enterprise Architecture course at Maharishi University of Management.

## Application Features
* i18n with English and Spanish Support
* Push Notifications
* Unhealthy content filtering
* Search Posts
* Manage Profile
* Follow / Unfollow Users
* Post text, photo or video
* Automatic user deactivation after 20 unhealthy posts
* Admin can push advertisements to all users / targeted users

## Getting Started
* Download / Clone the source code locally.
* Import Maven Project into your IDE.
* Make changes to properties file for database settings.
    * Change spring.profiles.active properties to 'dev' if you want to use H2 in memory DB
    * Change spring.profiles.active properties to 'prod' if you want to use MySQL
        * Setup datasource properties to connect
* Add environment variables for google application id and secret.
    * spring.security.oauth2.client.registration.google.clientId = [Your Google client ID]
    * spring.security.oauth2.client.registration.google.clientSecret = [Your Google client Secret]
* Add environment variables for mail configuration.
    * spring.mail.properties.mail.smtp.starttls.enable = [true if you're using gmail]
    * spring.mail.host = [smtp.gmail.com - if you're using gmail]
    * spring.mail.username = [your email address]
    * spring.mail.password = [application password if you're using gmail]
* Run.
    * Navigate to http://localhost:8080 in your browser
    * Test Users
        * admin@sonet.com | admin123 -> ACTIVE
        * user@sonet.com | user123 -> ACTIVE
        * user2@sonet.com | user21 -> BLOCKED
        * user3@sonet.com | user31 -> BANNED

## Frameworks / Technologies Checklist
### Backend
* [x] Spring Boot
* [x] Hibernate
* [x] Maven
* [x] Lombok
* [x] H2
* [x] MySQL
* [x] JUnit 5
* [x] Spring Security
* [x] JWT / OAuth 2.0
* [x] CircleCI
* [x] WebSocket
* [x] Rabbit MQ
* [x] Docker

### Frontend
* [x] Thymeleaf
* [x] Bulma
* [x] VueJS

