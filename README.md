[![](https://jitpack.io/v/ROMVoid95/ConfigBuilder.svg)](https://jitpack.io/#ROMVoid95/ConfigBuilder)

# ConfigBuilder

A utility to make maintaining Config files somewhat easier.

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
Make the config class containing variables with

```java
public class Config {

	@ConfigOption
	public static boolean ENABLED = true;

	@ConfigOption
	public static String WELCOME_TEXT = "Hello";

	@ConfigOption
	public static int MAX_AMOUNT = 10;

	@ConfigOption
	public static boolean RUN_ON_SUNDAY = true;

	@ConfigOption
	public static String TYPE_OF_WEATHER = "sunny";
}
```

somewhere in the application

```java
public class Launcher{

	...
	public static main(String ... args){
		new ConfigBuilder(Config.class, new File("app.cfg")).build();
	}
}
```

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

