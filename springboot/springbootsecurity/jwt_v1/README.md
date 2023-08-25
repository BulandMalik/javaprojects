## Build HTTP Basic Auth/Login in Spring Boot

Basic authentication is, well, basic. It’s a simple scheme built directly into the HTTP protocol. It comes from pastoral times before massive amounts of data and money flowed through the various pipes and tubes of the internet. As such, it’s really not incredibly secure. According to the spec, the password and username are encoded using Base64 in the HTTP authorization header. Because Base64 might as well be plain text, if you are going to use basic auth for anything, make sure that you’re always using HTTPS/SSL because you’re sending your authentication credentials with every request.

Each time a request is sent to the server, it would need to be authenticated so that the application can ensure that the request is from a valid user and identify the user. The easiest way to do this is by sending the username and password with each and every request. Theoretically, one could create some kind of session and store this information in a cookie. But sessions are hard to maintain when the application grows with a large number of users and also in cases where there is one than more backend server. So there needs to be a better way.

At first thought, you may think of sending the credentials to the server as a JSON string with the request params. This would mean that authentication has to be handled in every method.

The solution is to use Basic Auth, which requires sending the credentials with every request, but as a header.

An example:

Authorization: Basic QWxhZGRpbjpPcGVuU2VzYW1l

If you recognized the last part of the string as an encoded string, you are on the right track. We encoded Aladdin:OpenSesame (username:password) and it is Base64 encoded. If there is no encryption, the password will be transferred as clear text.

### Basic AUth Decode & Encode User+Pass

Get the base64 string from `Authroization` header as mentioned above.

Use following command to decode base64 string.
`echo -n [ENCODED_STRING] | base64 -D`

Use following command to encode string into base64.
`echo -n [DECODED_STRING] | base64`

### Sample Basic Auth Configuration

```aidl
@EnableWebSecurity
public class BasicSecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
    .authorizeRequests(authorizeRequests ->
        authorizeRequests
                .antMatchers("/board/*").hasAnyRole("MEMBER", "BOARD")
                .antMatchers("/members/*").hasRole("MEMBER")
                .antMatchers("/").permitAll()
    )
    .httpBasic().realmName("My org ream")
  }
  ...
}
```

### Session Management
One more thing. To prevent session cookies from being stored in the browser, it is beneficial to disable sessions for your requests. In general, sessions are complicated and can open security risks in your application, but that’s a story for another post. Here is the full class, including the disabled sessions.

```aidl
@EnableWebSecurity
public class BasicSecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests(authorizeRequests ->
            authorizeRequests
                    .antMatchers("/board/*").hasAnyRole("MEMBER", "BOARD")
                    .antMatchers("/members/*").hasRole("MEMBER")
                    .antMatchers("/").permitAll()
    )
        .httpBasic().realmName("My org ream")
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
    ...
}
```

### When Is the Session Created?

We can control exactly when our session gets created and how Spring Security will interact with it:

1. `always` – A session will always be created if one doesn't already exist.
2. `ifRequired` – A session will be created only if required (default).
3. `never` – The framework will never create a session itself, but it will use one if it already exists.
4. `stateless` – No session will be created or used by Spring Security.

```aidl
<http create-session="ifRequired">...</http>
```

Here is the Java configuration:
```aidl
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
    return http.build();    
}
```

It's very important to understand that this configuration only controls what Spring Security does, not the entire application. Spring Security won't create the session if we instruct it not to, but our app might!

By default, Spring Security will create a session when it needs one — this is `ifRequired`.

For a more stateless application, the `never` option will ensure that Spring Security itself won't create any session. But if the application creates one, Spring Security will make use of it.

Finally, the strictest session creation option, `stateless`, is a guarantee that the application won't create any session at all.

These more strict control mechanisms have the direct implication that cookies are not used, and so each and every request needs to be re-authenticated.

This stateless architecture plays well with REST APIs and their Statelessness constraint. They also work well with authentication mechanisms such as Basic and Digest Authentication.

### Under the Hood (Session Management & Context)

Before running the Authentication process, Spring Security will run a filter responsible for storing the Security Context between requests. This is the `SecurityContextPersistenceFilter`.

The context will be stored according to the strategy `HttpSessionSecurityContextRepository` by default, which uses the HTTP Session as storage.

For the strict `create-session=”stateless”` attribute, this strategy will be replaced with another — NullSecurityContextRepository — and no session will be created or used to keep the context.

### Concurrent Session Control

When a user that is already authenticated tries to authenticate again, the application can deal with that event in one of a few ways. It can either invalidate the active session of the user and authenticate the user again with a new session, or allow both sessions to exist concurrently.

The first step in enabling the concurrent session-control support is to add the following listener in the web.xml:

```aidl
<listener>
    <listener-class>
      org.springframework.security.web.session.HttpSessionEventPublisher
    </listener-class>
</listener>
```
Or we can define it as a Bean:
```aidl
@Bean
public HttpSessionEventPublisher httpSessionEventPublisher() {
    return new HttpSessionEventPublisher();
}
```

This is essential to make sure that the Spring Security session registry is notified when the session is destroyed.

In order to allow multiple concurrent sessions for the same user, the <session-management> element should be used in the XML configuration:

```aidl
<http ...>
    <session-management>
        <concurrency-control max-sessions="2" />
    </session-management>
</http>
```
Or we can do this via Java configuration:
```aidl
@Override
protected void configure(HttpSecurity http) throws Exception {
    http.sessionManagement().maximumSessions(2)
}
```

### Session Timeout
After the session has timed out, if the user sends a request with an expired session id, they will be redirected to a URL configurable via the namespace:
```aidl
<session-management>
    <concurrency-control expired-url="/sessionExpired.html" ... />
</session-management>
```
Similarly, if the user sends a request with a session id that is not expired but entirely invalid, they will also be redirected to a configurable URL:
```aidl
<session-management invalid-session-url="/invalidSession.html">
    ...
</session-management>
```

In Java, below will be the same configs.
```aidl
http.sessionManagement()
  .expiredUrl("/sessionExpired.html")
  .invalidSessionUrl("/invalidSession.html");
```

### Configure the Session Timeout With Spring Boot

```aidl
server.servlet.session.timeout=15m
```
If we don't specify the duration unit, Spring will assume it's seconds.

In a nutshell, with this configuration, the session will expire after 15 minutes of inactivity. The session is considered invalid after this period of time.

If we configured our project to use Tomcat, we have to keep in mind that it only supports minute precision for session timeout, with a minimum of one minute. This means that if we specify a timeout value of 170s, for example, it will result in a two-minute timeout.

>Finally, it's important to mention that even though Spring Session supports a similar property for this purpose (spring.session.timeout), if that's not specified, the autoconfiguration will fallback to the value of the property we first mentioned.

### Prevent Using URL Parameters for Session Tracking
Exposing session information in the URL is a growing security risk (from seventh place in 2007 to second place in 2013 on the OWASP Top 10 List).

[Starting with Spring 3.0](https://jira.springsource.org/browse/SEC-1052), the URL rewriting logic that would append the jsessionid to the URL can now be disabled by setting the disable-url-rewriting=”true” in the <http> namespace.

Alternatively, starting with Servlet 3.0, the session tracking mechanism can also be configured in the web.xml:
```aidl
<session-config>
     <tracking-mode>COOKIE</tracking-mode>
</session-config>

Or Programmatically,

servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
```
This chooses where to store the JSESSIONID — in the cookie or in a URL parameter.

### Session Fixation Protection With Spring Security
The framework offers protection against typical Session Fixation attacks by configuring what happens to an existing session when the user tries to authenticate again:

```
<session-management session-fixation-protection="migrateSession"> ...
```
And here's the corresponding Java configuration:
```aidl
http.sessionManagement().sessionFixation().migrateSession()
```

By default, Spring Security has this protection enabled (“migrateSession“). On authentication, a new HTTP Session is created, the old one is invalidated and the attributes from the old session are copied over.

If this is not what we want, two other options are available:

1. When `“none”` is set, the original session will not be invalidated.
2. When `“newSession”` is set, a clean session will be created without any of the attributes from the old session being copied over.

### Secure Session Cookie
We can use the httpOnly and secure flags to secure our session cookie:

1. `httpOnly`: if true then browser script won't be able to access the cookie
2. `secure`: if true then the cookie will be sent only over HTTPS connection

We can set those flags for our session cookie in the web.xml:
```aidl
<session-config>
    <session-timeout>1</session-timeout>
    <cookie-config>
        <http-only>true</http-only>
        <secure>true</secure>
    </cookie-config>
</session-config>
```
This configuration option is available since Java servlet 3. By default, http-only is true and secure is false.

Let's also have a look at the corresponding Java configuration:
```aidl
public class MainWebAppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext sc) throws ServletException {
        // ...
        sc.getSessionCookieConfig().setHttpOnly(true);        
        sc.getSessionCookieConfig().setSecure(true);        
    }
}
```
If we're using Spring Boot, we can set these flags in our application.properties:
```aidl
server.servlet.session.cookie.http-only=true
server.servlet.session.cookie.secure=true
```
Finally, we can also achieve this manually by using a Filter:
```aidl
public class SessionFilter implements Filter {
    @Override
    public void doFilter(
      ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        Cookie[] allCookies = req.getCookies();
        if (allCookies != null) {
            Cookie session = 
              Arrays.stream(allCookies).filter(x -> x.getName().equals("JSESSIONID"))
                    .findFirst().orElse(null);

            if (session != null) {
                session.setHttpOnly(true);
                session.setSecure(true);
                res.addCookie(session);
            }
        }
        chain.doFilter(req, res);
    }
}
```

### Working With the Session
```aidl
@Component
@Scope("session")
public class Foo { .. }

OR XML

<bean id="foo" scope="session"/>
```
Then the bean can be injected into another bean:
```aidl
@Autowired
private Foo theFoo;
```
And Spring will bind the new bean to the lifecycle of the HTTP Session.

We can also Inject the raw HTTP Session directly into a Controller method:

```aidl
@RequestMapping(..)
public void fooMethod(HttpSession session) {
    session.setAttribute(Constants.FOO, new Foo());
    //...
    Foo foo = (Foo) session.getAttribute(Constants.FOO);
}
```

The current HTTP Session can also be obtained programmatically via the raw Servlet API:
```aidl
ServletRequestAttributes attr = (ServletRequestAttributes) 
    RequestContextHolder.currentRequestAttributes();
HttpSession session= attr.getRequest().getSession(true); // true == allow create
```

### Running with Standalone Tomcat
Apache states that the Jakarta EE platform represents the evolution of the Java EE platform. Tomcat 10 and later versions implement specifications that were developed as part of Jakarta EE. In contrast, Tomcat 9 and earlier versions implement specifications that were developed as part of Java EE. Consequently, applications that were developed for Tomcat 9 and earlier will not run on Tomcat 10. Nonetheless, there may still be ways to address this issue.
[Tomcat 10 Key things to keep in mind](https://www.appsdeveloperblog.com/deploy-a-spring-boot-rest-app-as-a-war-to-tomcat-10/#google_vignette)

### Steps to create WAR FIle
1. Set packagaing as `war` inside pom.xml
2. Add Tomcat Dependency
```aidl
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-tomcat</artifactId>
    <scope>provided</scope>
</dependency>
```
3. To run the application from Tomcat in the classical way, extend SpringBootServletInitializer in the main application and override the configure method:
```aidl
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Tomcat10Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Tomcat10Application.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Tomcat10Application.class);
    }
}
```