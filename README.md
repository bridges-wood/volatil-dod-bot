# volatil - Java Chat App (Dungeon of Doom Bot)

A multithreaded console chat application designed to demonstrate client-server and multithreading programming practices. Each message is ephemeral and anonymised, lasting only as long as the users that saw it are connected.

## Requirements

1. JRE 11 or above
2. Maven 3.6.3 or above

## Installation

### Default

1. Download the jar file from the latest release.

### Custom

Although this repository should be using the latest version of the core volatil package, to ensure you have the most up-to-date version you will need to do the following:

1. Download the latest version of the core volatil package [here](https://github.com/bridges-wood/volatil-core/releases/latest).
2. Download the latest version of the Dungeon of Doom Online package [here](https://github.com/DoD-Online/releases/latest)
3. Run the following command for each jar:

```
mvn install:install-file \
-Dfile={name}-{version}.jar \
-DgroupId=com.volatil \
-DartifactId={name} \
-Dversion={version} \
-Dpackaging=jar \
-DlocalRepositoryPath=lib \;
```

## Getting Started

Execute `mvn package` and run the generated jar file with `java -jar target/dodbot-{version}.jar`
