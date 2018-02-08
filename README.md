# Parceler  
<a href="http://www.methodscount.com/?lib=com.github.yjfnypeu.Parceler%3Aapi%3A1.2"><img src="https://img.shields.io/badge/Methods and size-core: 158 | deps: 24 | 17 KB-e91e63.svg"/></a>

Parceler是一款简单、轻量级的、用于在实体类与bundle之间进行双向数据注入的框架。可支持任意数据类型注入。

### 特性

- 超级精简：总共方法数不到100
- 可以存取任意数据类型
- 存取api统一
- 自动修复类型不匹配问题
- 支持数据转换器，以适配更多使用场景
- 自动在Bundle与实体类间注入数据
- 提供缓存机制提供性能优化
- 使用BundleBuilder, 避免key值硬编码
- 使用IntentLauncher，方便的进行跨页面跳转传值

### 添加依赖

添加使用JitPack仓库

```Groovy
// 加入jitpack仓库依赖
maven { url 'https://jitpack.io' }
```

LastestVersion=[![](https://jitpack.io/v/yjfnypeu/Parceler.svg)](https://jitpack.io/#yjfnypeu/Parceler)


```Groovy
// 添加依赖
dependencies {
    annotationProcessor "com.github.yjfnypeu.Parceler:compiler:$LastestVersion"
    compile "com.github.yjfnypeu.Parceler:api:$LastestVersion"
}
```

### 用法

[请参考wiki文档](https://github.com/yjfnypeu/Parceler/wiki)

Wiki文档比较零散，也可以参考下方的博客：

[Android开源: 快用Parceler来优雅的进行Bundle数据存取！](https://juejin.im/post/5a30c2056fb9a045055e1e2d)

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



