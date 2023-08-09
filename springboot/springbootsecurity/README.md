# Spring Boot Login Options

This repository shows how to create a plethora of login options for Spring Boot 2.1. Please read [A Quick Guide to Spring Boot Login Options](https://developer.okta.com/blog/2019/05/15/spring-boot-login-options) to see how this example was created.

**Prerequisites:** [Java 8](https://adoptopenjdk.net/).

> [Okta](https://developer.okta.com/) has Authentication and User Management APIs that reduce development time with instant-on, scalable user infrastructure. Okta's intuitive API and expert support make it easy for developers to authenticate, manage, and secure users and roles in any application.

* [Getting Started](#getting-started)
* [Links](#links)
* [Help](#help)
* [License](#license)

## Getting Started

To install this example, run the following commands:

```bash
git clone https://github.com/oktadeveloper/okta-spring-boot-login-options-example.git
cd okta-spring-boot-login-options-example
```

The `basic-auth`, `custom-form-auth`, and `form-auth` examples can all be run using:

```
./gradlew bootRun
```

Look at the code or the [blog post](https://developer.okta.com/blog/2019/05/15/spring-boot-login-options) for the hard-coded credentials. The blog post also has instructions for configuring GitHub and Okta as identity providers.

To run the `oauth-okta-starter` example, you'll need to create an account and OIDC app on Okta.

### Create an Application in Okta

Log in to your Okta Developer account (or [sign up](https://developer.okta.com/signup/) if you don’t have an account).

1. From the **Applications** page, choose **Add Application**.
2. On the Create New Application page, select **Web**.
3. Give your app a memorable name, add `http://localhost:8080/login/oauth2/code/okta` as a Login redirect URI, then click **Done**.

Copy your issuer (found under **API** > **Authorization Servers**), client ID, and client secret into `oauth-okta-starter/src/main/resources/application.yml` as follows:

```yaml
okta:
  oauth2:
    issuer: https://{yourOktaDomain}/oauth2/default
    client-id: {yourClientID}
    client-secret: {yourClientSecret}
```

**NOTE:** The value of `{yourOktaDomain}` should be something like `dev-123456.okta.com`. Make sure you don't include `-admin` in the value!

After modifying this file, start the example with `./gradlew bootRun`. You should be able to authenticate with Okta at `http://localhost:8080`.

## Links

This example uses the following open source libraries:

* [Okta Spring Boot Starter](https://github.com/okta/okta-spring-boot) 
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Security](https://spring.io/projects/spring-security)
* [OpenJDK](https://openjdk.java.net/)

## Help

Please post any questions as comments on the [blog post](https://developer.okta.com/blog/2019/05/15/spring-boot-login-options), or on the [Okta Developer Forums](https://devforum.okta.com/).

## License

Apache 2.0, see [LICENSE](LICENSE).
