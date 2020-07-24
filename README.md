# ConfigBuilder (@Deprecated | Not Maintained)

Please use https://github.com/ROMVoid95/ReadOnlyCfg

## Usage
Make a config class containing public static variables.  
Annotate them with the @ConfigOption.  
Call the build function to override them with the associated property file.

## Add Dependency
To get ConfigBuilder

```xml
<repository>
	<id>jitpack.io</id>
	<url>https://jitpack.io</url>
</repository>
```

```xml
<dependency>
	<groupId>com.github.ROMVoid95</groupId>
	<artifactId>ConfigBuilder</artifactId>
	<version>1.0.0</version>
</dependency>
```

Replace VERSION with the latest version.

## How To Use
### > EDIT: How to not write a useage section
Make the config class containing variables with

```java
public class Config {

	@COption
	public static boolean ENABLED = true;

	@COption
	public static String WELCOME_TEXT = "Hello";

	@COption
	public static int MAX_AMOUNT = 10;

	@COption
	public static boolean RUN_ON_SUNDAY = true;

	@COption
	public static String TYPE_OF_WEATHER = "sunny";
}
```
### Very un-descriptive 
somewhere in the application

```java
public class Launcher{

	...
	public static main(String ... args){
		new ConfigBuilder(Config.class, new File("app.cfg")).build();
	}
}
```
### Way to go not showing what it actually could be used for
will generate application.cfg containing:

```ini
i_am_enabled=false
max_amount=8
run_on_sunday=true
type_of_weather=sunny
welcome_text=Hello
```

If we where to edit welcome_text like:
```ini
...
welcome_text=Hello there, Welcome to my repo
```

would result in..

```java
public class Launcher{

	...
	public static main(String ... args){
		System.out.println(Config.WELCOME_TEXT);
		//still prints: Hello
		
		new ConfigBuilder(Config.class, new File("app.cfg")).build();
		System.out.println(Config.WELCOME_TEXT);
		//prints: Hello there, Welcome to my repo
	}
}
```

