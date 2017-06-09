# Parceler  ![svg](https://travis-ci.org/yjfnypeu/Parceler.svg?branch=master)  <a href="http://www.methodscount.com/?lib=org.lzh.compiler.parceler%3Aparceler-api%3A0.6"><img src="https://img.shields.io/badge/Methods count-core: 44 | deps: 1-e91e63.svg"/></a>

A simple library for easily put and get data from Bundle automatically.

### Dependencies

LastestVersion=[ ![Download](https://api.bintray.com/packages/yjfnypeu/maven/Parceler/images/download.svg) ](https://bintray.com/yjfnypeu/maven/Parceler/_latestVersion)

```Groovy
//1.add it to build.gradle in Android module
dependencies {
    annotationProcessor "org.lzh.compiler.parceler:parceler-compiler:LastestVersion"
    compile "org.lzh.compiler.parceler:parceler-api:LastestVersion"
}
```

### Usage

see [wiki](https://github.com/yjfnypeu/Parceler/wiki)

### Proguard
```Proguard
-keep class com.lzh.compiler.parceler.**
-dontwarn com.lzh.compiler.parceler.processor.**
-keep class * implements com.lzh.compiler.parceler.ParcelInjector

-keepclasseswithmembernames class * {
    @com.lzh.compiler.parceler.annotation.Arg <fields>;
}
```

### Contacts
Email:470368500@qq.com<br>
QQ Group:108895031


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



