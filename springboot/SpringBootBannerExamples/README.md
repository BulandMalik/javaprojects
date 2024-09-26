# Spring Boot Banner Examples
By default, Spring Boot comes with a banner which shows up as soon as the application starts.

We can also change default banner programmatically using Spring Boot Banner interface.

## Text File as a Banner
One approach is to defene the banner inside a text file.

we need to create a file named `banner.txt` in the `src/main/resources` directory and paste the banner content into it.

Point to note here is that banner.txt is the default expected banner file name, which Spring Boot uses. However, if we want to choose any other location or another name for the banner, we need to set the spring.banner.location property in the application.properties file:
```aidl
spring.banner.location=classpath:/src/main/resources/bannername.txt
```

## Image as a Banner
We can also use images as banners too. Same as with `banner.txt`, Spring Boot expects the banner image’s name as `banner.gif`. Additionally, we can set different image properties such as height, width, etc. in the application.properties:

```aidl
spring.banner.image.location=classpath:banner.gif #Banner image file location (jpg/png can also be used)
spring.banner.image.width=  # Width of the banner image in chars (default 76) 
spring.banner.image.height= # Height of the banner image in chars (default based on image height)
spring.banner.image.margin= # Left hand image margin in chars (default 2)
spring.banner.image.invert= # If images should be inverted for dark terminal themes (default false)
```
Images will be converted into an ASCII art representation before getting printed on the startup which can add a lot of time on startup in case we have a complex image.It is recommended to use text format for a Custom Banners in Spring Boot.

## Banner related Props
In the `application.properties`/`application.yml` we can configure following properties related to banner.
- `banner.charset`: It configures banner encoding. Default is UTF-8
- `banner.location`: It is banner file location. Default is classpath:banner.txt
- `banner.image.location`: It configures banner image file location. Default is classpath:banner.gif. File can also be jpg, png.
- `banner.image.width`: It configures width of the banner image in char. Default is 76.
- `banner.image.height`: It configures height of the banner image in char. Default is based on image height.
- `banner.image.margin`: It is left hand image margin in char. Default is 2.
- `banner.image.invert`: It configures if images should be inverted for dark terminal themes. Default is false.

## Banner Variables
You can add context to the banner by adding predefined placeholders. For example, Adding the placeholders `${application.title}`, `${application.formatted-version}` and `${spring-boot.formatted-version}` placeholders will pull information from the application JAR’s MANIFEST.MF file and display it on the banner section.
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#features.spring-application.banner

- `${application.version}` : Picks the version number of our application from the property Implementation-Version configured in MANIFEST.MF file.
- `${application.formatted-version}` : Picks the version number of our application configured in MANIFEST.MF file that will be (surrounded with brackets and prefixed with v).
- `${application.title}` : Picks the application title from the property Implementation-Title configured in MANIFEST.MF file.
- `${spring-boot.version}` : It displays the Spring Boot version that we are using such as 1.4.3.RELEASE .
- `${spring-boot.formatted-version}` : It displays the Spring Boot version that we are using formatted for display (surrounded with brackets and prefixed with v) such as example (v1.4.3.RELEASE).
- `${AnsiColor.NAME}` : It is used to make colorful banner where NAME is an ANSI escape code. Find the values for NAME from the link
- `${AnsiBackground.NAME}` : It is used to change banner background color where NAME is an ANSI escape code. Find the values for NAME from the link.
- `${AnsiStyle.NAME}` : It is used to change style of banner where NAME is an ANSI escape code. Find the values for NAME from the link.

We need to configure above properties in banner text file as below.
```aidl
=============================
      Learn Tech Verse Banner
=============================
Application Version : ${application.version}
Application Formatted Version : ${application.formatted-version}
Application Title : ${application.title}
Spring Boot Version : ${spring-boot.version}
Spring Boot Formatted Version : ${spring-boot.formatted-version}
==============================================
```

## Turn off Spring boot Banners
You can disable the default banner by setting the `spring.main.banner-mode property` to `off`. The default value for this properties entry is `console` which means the output is not part of the `logs`. Apart from `off` and `console` this property can take `log` as one more value where the banner info is printed on the logs. When `log` is set, there is no guarantee that the colours would work.
```aidl
spring.main.banner-mode=off
```
It is also possible to disable the banner with the SPRING_MAIN_BANNER-MODE environment variable.

## Colorful Banners

> Tip: You can create colorful banners when you add Ansi Colors like ${AnsiColor.MAGENTA} to your banner.txt.

## Create Custom Banner Programmatically
To create custom banner programmatically, we need to follow below steps.

1. Spring Boot provides Banner interface that enables us to change banner programmatically. We will create a class that will implement Banner interface and override its method printBanner() to configure banner.
   MyBanner.java
```aidl
import java.io.PrintStream;
import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

public class StringBanner implements Banner  {
	@Override
	public void printBanner(Environment arg0, Class<?> arg1, PrintStream arg2) {
		arg2.println("================================");
		arg2.println("----------Hello World!----------");
		arg2.println("================================");
	}
}
```
2. Now we need to configure our banner class with SpringApplication. Find the application class.

## Programmatic Approach to Banners
e can also change the banner mode using the java configuration. For example, the following code will print banner directly to log entries instead of console. And the setBanner method helps us to provide a fallback Banner if a valid banner file is not present in the classpath.
```aidl
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

@SpringBootApplication
public class SpringBootBannerExampleApp {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringBootBannerExampleApp.class);
        springApplication.setBannerMode(Banner.Mode.CONSOLE);
        springApplication.setBanner((environment, sourceClass, out) -> out.println("Hello Banner"));
        springApplication.run(args);

    }

}
```

## Banner Generator
We can use following links to generate the banner.
1. [Option #1](https://springhow.com/spring-boot-banner-generator/)
2. [Option #2](https://devops.datenkollektiv.de/banner.txt/index.html)
3. [Text to ASCII Art Generator](http://patorjk.com/software/taag/#p=display&h=1&f=Soft&t=Planets%20Server)
4. [Spring Banner Plugin](https://github.com/acanda/spring-banner-plugin)
5. [JFiglet](https://lalyos.github.io/jfiglet/)