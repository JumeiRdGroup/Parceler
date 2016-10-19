# Parceler ![svg](https://travis-ci.org/yjfnypeu/Parceler.svg?branch=master)    [ ![Download](https://api.bintray.com/packages/yjfnypeu/maven/Parceler/images/download.svg) ](https://bintray.com/yjfnypeu/maven/Parceler/_latestVersion)   <a href="http://www.methodscount.com/?lib=org.lzh.compiler.parceler%3Aparceler-api%3A0.2"><img src="https://img.shields.io/badge/Methods and size-core: 42 | deps: 1 | 7 KB-e91e63.svg"/></a>

A simple library to inject data between field of class and Bundle

####Dependencies

```
1.add it to build.gradle of root project
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        ...
        classpath 'com.novoda:bintray-release:0.3.4'
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

####Usage
Parceler used annotation compiler to generate class just like you write on handle.so DO NOT WORRY ABOUT PERFORMANCE

The parceler provided two ways to make the bundle operations more convenient on Android.<br>
At first:Parceler provided annotation <code>Arg</code> to indicate while filed should be associate with bundle.
the <code>Arg</code> can be used for any fields of class who could be put in bundle.for eg:<br>

```
public class UserInfo {
    @Arg("renameKey")// rename the key to put in bundle
    String username;// var type should be able to put in bundle.
    @NonNull // add NonNull to indicate that this filed should not be null when you injects from bundle to field
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

There are also more other annotation called <code>Dispatcher</code> to used.to used by Activity,it will generate class that
with suffix of <i>Dispatcher</i> in your build folder.for eg of LoginActivity

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

And then,as to launcher <code>LoginActivity</code>,you can use <code>LoginActivityDispatcher</code> to do it:

```
new LoginActivityDispatcher(password).setUsername(username).requestCode(100).start(activity);
```

And also to get intent to used for <i>PendingIntent</code>
```
// password has been annotated by NonNull,so it should be set with constructor
Intent intent = new LoginActivityDispatcher(password).setUsername(username).getIntent(activity);
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



