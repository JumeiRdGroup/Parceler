# Parceler ![svg](https://travis-ci.org/yjfnypeu/Parceler.svg?branch=master)    [ ![Download](https://api.bintray.com/packages/yjfnypeu/maven/Parceler/images/download.svg) ](https://bintray.com/yjfnypeu/maven/Parceler/_latestVersion)   <a href="http://www.methodscount.com/?lib=org.lzh.compiler.parceler%3Aparceler-api%3A0.2"><img src="https://img.shields.io/badge/Methods and size-core: 42 | deps: 1 | 7 KB-e91e63.svg"/></a>

A simple library for easily put and get data from Bundle automatically.

###Dependencies

```
1.add it to build.gradle of root project
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        ...
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.4'
    }
}

2.add it to build.gradle of app project
apply plugin: 'com.neenbedankt.android-apt'
dependencies {
    ...
    apt 'org.lzh.compiler.parceler:parceler-compiler:0.2'
    compile 'org.lzh.compiler.parceler:parceler-api:0.2'
}
```

[中文使用文档](./USAGE-CH.md)

###Usage
<b>Parceler</b> is a compile-time injection framework, it will use the annotation compiler to generate the POJO classes for you, so there is no need to worry about the <i>PERFORMANCE</i>

The parceler provide two ways to make the bundle operations more convenient on Android.<br>
First, <b>Parceler</b> provide a annotation <b>Arg</b> to indicate which field could be associate with <b>Bundle</b>, see example below:

```
public class UserInfo {
    @Arg("renameKey")// rename the key to put in bundle
    String username;// var type should be able to put in bundle.
    @NonNull // add NonNull to indicate that this field should not be null when you injects from
    bundle to field
    @Arg
    String password;
    public UserInfo (Bundle data) {
        // inject data from bundle to fields
        Parceler.injectToTarget(this,data);
    }

    public Bundle getBundle () {
        // inject data from UserInfo to bundle
        Bundle data = new Bundle();
        Parceler.injectToData(this,data);
        return data;
    }
}
```

There is another annotation <b>Dispatcher</b> which can work with the Parceler framework, with this annotation, Parceler framework will generate a router class for activity which has this annotation on it,
```
// Config injector to base class.so you can use it on it subclass directly
public class BaseActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parceler.injectToTarget(this,getIntent() == null ? null : getIntent().getExtras());
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Parceler.injectToData(this,outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Parceler.injectToTarget(this,savedInstanceState);
    }
}
```
```
@Dispatcher
public class LoginActivity extends BaseActivity {
    @Arg
    String username;
    @NonNull
    @Arg
    String password;
    ...
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

And also to get intent to used for <i>PendingIntent</code>
```
// password has been annotated by NonNull,so it should be set with constructor
Intent intent = new LoginActivityDispatcher(password).setUsername(username).getIntent(activity);
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



