# Native Image Sample with GraalVM

## Installation

We need to do the following steps:

### Install GraalVM

1. Download GraalVM (https://www.graalvm.org/downloads/)
1. Unzip into a folder
1. Modify OS environment variables
    1. Set `GRAALVM_HOME` to this
    1. Extend `PATH` by the `$GRAALVM_HOME/bin` folder

### Install `native-image` builder

We use the GraalVM Updater (`gu`, that is placed in the GraalVM's `bin` folder)
to install the `native-image` tool:

1. Open a native terminal - in Windows, the `cmd`!
2. Type: `gu install native-image`

The tool is downloaded into the same folder like `gu`.

Don't forget to follow the Prerequisites in the [installation instructions](https://www.graalvm.org/22.3/reference-manual/native-image/)
to install the platform-dependent native compiler.

## Sample

First, we create a simple hello world program:
```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, Native World!");
    }
}
```
Then, we compile the programm and build the native image.
```bash
javac HelloWorld.java
native-image HelloWorld
```
After that, we should find a native executable (Windows: `HelloWorld.exe`) 
in the directory.
