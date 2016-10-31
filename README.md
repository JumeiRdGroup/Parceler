# Parceler [ ![Download](https://api.bintray.com/packages/yjfnypeu/maven/Parceler/images/download.svg) ](https://bintray.com/yjfnypeu/maven/Parceler/_latestVersion)   <a href="http://www.methodscount.com/?lib=org.lzh.compiler.parceler%3Aparceler-api%3A0.4"><img src="https://img.shields.io/badge/Methods count-core: 42 | deps: 1-e91e63.svg"/></a>

A simple library for easily put and get data from Bundle automatically.

###Dependencies

```Groovy
//1.add it to build.gradle of root project
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.4'
    }
}

//2.add it to build.gradle of app project
apply plugin: 'com.neenbedankt.android-apt'
dependencies {
    apt 'org.lzh.compiler.parceler:parceler-compiler:0.5'
    compile 'org.lzh.compiler.parceler:parceler-api:0.5'
}
```

[中文使用文档](./USAGE-CH.md)

###Usage
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
        Parceler.injectToEntity(this, data);
    }

    public Bundle getBundle () {
        // inject data from UserInfo to bundle
        Bundle data = new Bundle();
        Parceler.injectToBundle(this, data);
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


There is another annotation **Dispatcher** which can work with the Parceler framework, with this
annotation on an `Activity` class, Parceler framework will generate a router class for it.
```Java
// Config injector to base class.so you can use it on it subclass directly
public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parceler.injectToEntity(this,getIntent());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Parceler.injectToBundle(this,outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Parceler.injectToEntity(this,savedInstanceState);
    }
}
```
```Java
@Dispatcher
public class LoginActivity extends BaseActivity {
    @Arg
    String username;
    @NonNull
    @Arg
    String password;
    //...
    TextView userTv;
    TextView psdTv;

    public void onCreate (Bundle saveInstanceState) {
        super.onCreate();
        userTv.setText(username);
        psdTv.setText(password);
    }
}
```
then you can easily use a simple code like the code below to navigate to this activity and even can pass the data if needed.

```
new LoginActivityDispatcher(password).setUsername(username).requestCode(100).start(activity);
```

If you want to handle `Intent` object(e.g. to add flags, or start activity for result, or combine
 usage for `PendingIntent`), there is a handy method called **`getIntent()`**:
```
// password has been annotated by NonNull,so it should be set with constructor
Intent intent = new LoginActivityDispatcher(password).setUsername(username).getIntent(activity);
```

###Proguard
```Proguard
-keep class com.lzh.compiler.parceler.**
-dontwarn com.lzh.compiler.parceler.processor.**
-keep class * implements com.lzh.compiler.parceler.ParcelInjector

-keepclasseswithmembernames class * {
    @com.lzh.compiler.parceler.annotation.Arg <fields>;
}
```

###Contacts
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



