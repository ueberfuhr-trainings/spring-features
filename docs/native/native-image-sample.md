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

### Install Native Compiler (Windows)

Unfortunately, `native-image` needs a platform-specific, native compiler that depends
on our platform that we run the tool. On Windows, it is a `cl.exe`, that is part of the
C++ toolset (MS Build Tools). We can get this by following the
[official instructions](https://learn.microsoft.com/en-us/cpp/build/building-on-the-command-line).

1. Download and install [Visual Studio](https://visualstudio.microsoft.com/de/downloads/).
1. In the `cmd`, run `C:\Program Files (x86)\Microsoft Visual Studio\2019\BuildTools\VC\Auxiliary\Build\vcvars64.bat`.
1. After that, we should be able to run `cl`

## Sample

First, we create a simple hello world program:
```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, Native World!");
    }
}
```
Then we compile the programm and build the native image.
```bash
javac HelloWorld.java
native-image HelloWorld
```
After that, we should find a native executable (Windows: `HelloWorld.exe`) in the directo
