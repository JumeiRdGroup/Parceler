# Parceler ![svg](https://travis-ci.org/yjfnypeu/Parceler.svg?branch=master)
[ ![Download](https://api.bintray.com/packages/yjfnypeu/maven/Parceler/images/download.svg) ](https://bintray.com/yjfnypeu/maven/Parceler/_latestVersion)
<a href="http://www.methodscount.com/?lib=org.lzh.compiler.parceler%3Aparceler-api%3A0.2"><img src="https://img.shields.io/badge/Methods and size-core: 42 | deps: 1 | 7 KB-e91e63.svg"/></a>
A simple library to inject data between field of class and Bundle

####Dependencies

```
1.add apt dependence to build.gradle of root project
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        ...
        classpath 'com.novoda:bintray-release:0.3.4'
    }
}

2.add parceler dependencies to build.gradle of app project
dependencies {
    ...
    apt 'org.lzh.compiler.parceler:parceler-compiler:0.2'
    compile 'org.lzh.compiler.parceler:parceler-api:0.2'
}
```

## License
```
Copyright 2015 

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```



