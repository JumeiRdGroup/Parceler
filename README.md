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

[中文使用文档](./USAGE-CH.md)

### Usage
**Parceler** is a compile-time injection framework, by using **apt** to generate the classes,
there are nearly no issue of runtime <i>PERFORMANCE</i>.

**Parceler** provides two ways to make the bundle operations more convenient on Android.<br>
First, <b>Parceler</b> provide a annotation <b>Arg</b> to indicate which field could be associate with <b>Bundle</b>, see example below:

```Java
public class UserInfo {
    @Arg("renameKey")// rename the key to put in bundle
    String username;// var type should be able to put in bundle.

    // add NonNull to indicate that this field should not be null when you injects from
    // bundle to field
    @NonNull
    @Arg
    String password;
    @Arg //You can also add it to a private variables,in this case,you must provide a pair of get/set methods instead
    private String address;
    public UserInfo (Bundle data) {
        // inject data from bundle to fields
        Parceler.toEntity(this, data);
    }

    public Bundle getBundle () {
        // inject data from UserInfo to bundle
        Bundle data = new Bundle();
        Parceler.toBundle(this, data);
        return data;
    }
    
    public void setAddress (String address) {
        this.address = address;
    }
    
    public String getAddress () {
        return address;
    }
}
```

### ChangeLogs

 - 0.9
 ```
 Remove Dispatcher annotations.
 Refactor injector logic. Separate the different injector with inheritance structure
 ```

 - 0.7
 ```
 Support annotation NonNull for field.You can use any annotations with name 'NonNull' to indicated that should not be null
 Support field annotated by @Arg who has generic types
 ```

 - 0.6

 ```
 Support min version to 8
 Support use @Dispatcher on all classes.include innerclass
 Some optimizes
 ```

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



