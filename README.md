# json-schema-constraint-validator
A JSON Schema validator that allows inter-property constraints.

## Available downloads (Gradle/maven)

This package is hosted by jitpack.io; the artifact is as follows:

#### Gradle:

Step 1. Add the JitPack repository to your build file
```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
Step 2. Add the dependency
```groovy
dependencies {
        compile 'com.github.Cocoduf:json-schema-constraint-validator:-SNAPSHOT'
}
```

#### Maven:

Step 1. Add the JitPack repository to your build file
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
Step 2. Add the dependency
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

#### Schema
Say you have an initial schema:
```json
{
  "$schema": "http://json-schema.org/draft-04/schema#",
  "title": "Data",
  "type": "object",
  "properties": {
    "SOURCE": {
      "type": "number"
    },
    "TARGET": {
      "type": "number"
    }
  }
}
```

In order to add a constraint between SOURCE and TARGET, you must add the "constraints" property to SOURCE:
```json
"SOURCE": {
  "type": "number",
  "constraints": [
  
  ]
}
```

A constraint is an object with a "type" and a "target".
```json
"constraints": [
    {
      "type": "CONSTRAINT_TYPE",
      "target": "#/TARGET"
    }
]
```
Target must be a valid [JSON Pointer](https://tools.ietf.org/html/rfc6901) (# being the root object).

Here is a table describing each available constraint type:
| Source field                           | Constraint name           | Target field                           | Required JSON Schema format |
|----------------------------------------|---------------------------|----------------------------------------|-----------------------------|
| BOOLEAN, NUMBER, STRING, ARRAY, OBJECT | valueEqualTo              | BOOLEAN, NUMBER, STRING, ARRAY, OBJECT |                             |
| BOOLEAN, NUMBER, STRING, ARRAY, OBJECT | valueNotEqualTo           | BOOLEAN, NUMBER, STRING, ARRAY, OBJECT |                             |
| NUMBER                                 | valueGreaterThan          | NUMBER                                 |                             |
| NUMBER                                 | valueGreaterThanOrEqualTo | NUMBER                                 |                             |
| NUMBER                                 | valueLesserThan           | NUMBER                                 |                             |
| NUMBER                                 | valueLesserThanOrEqualTo  | NUMBER                                 |                             |
| NUMBER, STRING                         | inArray                   | ARRAY                                  |                             |
| NUMBER, STRING                         | notInArray                | ARRAY                                  |                             |
| STRING                                 | priorTo                   | STRING                                 | date-time                   |
| STRING                                 | subsequentTo              | STRING                                 | date-time                   |

Available date formats: "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss".

#### Examples
Note: There are plenty of examples in the test files.

##### Testing if a number is greater than an other number
```json
{
  "$schema": "http://json-schema.org/draft-04/schema#",
  "title": "Data",
  "type": "object",
  "properties": {
    "SOURCE": {
      "type": "number",
      "constraints": [
        {
          "type": "valueGreaterThan",
          "target": "#/TARGET"
        }
      ]
    },
    "TARGET": {
      "type": "number"
    }
  }
}
```

##### Testing if a string is in an array
```json
{
  "$schema": "http://json-schema.org/draft-04/schema#",
  "title": "Data",
  "type": "object",
  "properties": {
    "SOURCE": {
      "type": "string",
      "constraints": [
        {
          "type": "inArray",
          "target": "#/TARGET"
        }
      ]
    },
    "TARGET": {
      "type": "array"
    }
  }
}
```

##### Testing if a date is prior to an other date
```json
{
  "$schema": "http://json-schema.org/draft-04/schema#",
  "title": "Data",
  "type": "object",
  "properties": {
    "SOURCE_NAME": {
      "type": "string",
      "constraints": [
        {
          "type": "priorTo",
          "target": "#/TARGET"
        }
      ],
      "format": "date"
    },
    "TARGET_NAME": {
      "type": "string",
      "format": "date"
    }
  }
}
```