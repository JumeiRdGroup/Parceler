# Parceler  
<a href="http://www.methodscount.com/?lib=com.github.yjfnypeu.Parceler%3Aapi%3A1.2"><img src="https://img.shields.io/badge/Methods and size-core: 158 | deps: 24 | 17 KB-e91e63.svg"/></a>

Parceler是一款简单、轻量级的、用于在实体类与bundle之间进行双向数据注入的框架。可支持任意数据类型注入。

### 特性

1. 支持将任意类型数据存入bundle中。或者将从bundle中获取任意数据
2. 支持添加注解。对实体类中任意添加注解的字段进行自动注入。
3. 可定制自己的数据转换器。兼容更多场景使用。

### 添加依赖

LastestVersion=[![](https://jitpack.io/v/yjfnypeu/Parceler.svg)](https://jitpack.io/#yjfnypeu/Parceler)


```Groovy
//1.add it to build.gradle in Android module
dependencies {
    annotationProcessor "com.github.yjfnypeu.Parceler:compiler:$LastestVersion"
    compile "com.github.yjfnypeu.Parceler:api:$LastestVersion"
}
```

### 用法

[请参考wiki文档](https://github.com/yjfnypeu/Parceler/wiki)

### Proguard
```Proguard
-keep class com.lzh.compiler.parceler.annotation.**
-keep class * implements com.lzh.compiler.parceler.ParcelInjector
-keepclasseswithmembernames class * {
    @com.lzh.compiler.parceler.annotation.Arg <fields>;
}
```

### 联系作者

欢迎提issue<br>
Email:470368500@qq.com<br>
QQ Group:108895031

## License
```
Copyright 2015 Haoge

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



