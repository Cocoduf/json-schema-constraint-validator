# json-schema-constraint-validator
A JSON Schema validator that allows inter-property constraints.

## Available downloads (Gradle/maven)

This package is available on Maven central; the artifact is as follows:

Gradle:

```groovy
dependencies {
    compile(group: "com.github.cocoduf", name: "json-schema-constraints-validator", version: "1.0.0");
}
```

Maven:

```xml
<dependency>
    <groupId>com.github.cocoduf</groupId>
    <artifactId>json-schema-constraints-validator</artifactId>
    <version>1.0.0</version>
</dependency>
```
## Developing

Basic use of this library only requires the methods found in the JsonValidator.java file.
Here's how to test some JSON:

```java
JsonValidator jsonValidator = new JsonValidator();
jsonValidator.setSchema("raw json string" OR java.io.File instance);
boolean isJsonValid = jsonValidator.validateJson("raw json string" OR java.io.File instance);
```