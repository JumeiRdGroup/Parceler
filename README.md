# Parceler  

Parceler是一款简单、轻量级的Bundle数据存取扩展框架。

使用此框架，你可以做到：

1. 向Bundle中存入任意类型的数据
2. 从Bundle中读取任意类型的数据
3. 将数据从Bundle容易中注入到指定的成员变量中去
4. 将数据从指定成员变量中注入到Bundle中去
5. 更方便的进行页面启动传值(避免key值硬编码)
6. 支持在kotlin环境下使用

更重要的：框架的**总方法数不到100，且无任何额外依赖**！

## 依赖

> 添加JitPack仓库

```Groovy
maven { url 'https://jitpack.io' }
```

> 添加依赖

LastestVersion=[![](https://jitpack.io/v/yjfnypeu/Parceler.svg)](https://jitpack.io/#yjfnypeu/Parceler)

```Groovy
dependencies {
	// 若要在kotlin环境下使用，请将annotationProcessor替换为kapt。
	annotationProcessor "com.github.yjfnypeu.Parceler:compiler:$LastestVersion"
	implementation "com.github.yjfnypeu.Parceler:api:$LastestVersion"
}
```

## 用法

### 1. 配置数据转换器

数据转换器是框架的核心类，只有配置过对应的数据转换器才能使框架做到**支持对任意类型数据进行存取**的效果。

由于常用的数据格式是JSON。所以框架针对流行的fastjson与GSON分别定制了各自的转换器(**FastJsonConverter**和**GsonConverter**)，方便进行直接使用。

请注意，框架本身并没有直接依赖fastjson或者GSON, 所以具体使用哪种转换器。取决于你当前的运行环境支持使用哪种转换器，比如我们项目有依赖fastjson。那么就可以选择依赖FastJson的转换器：

```
Parceler.setDefaultConverter(FastJsonConverter.class);
```

啰嗦了那么多，其实就是一句配置即可，然后就可以直接进行方便的数据存取了。

### 2. 使用BundleFactory进行任意数据存取

框架提供BundleFactory类来作为数据存取的操作类：

```
// 创建操作类实例。bundle为所需要的Bundle数据容器。
// 若为null则将创建一个新的Bundle对应提供使用
BundleFactory factory = Parceler.createFactory(bundle);

// 通过factory对象进行数据存取
...

// 操作完成后。获取操作完成后的Bundle容器实例
Bundle result = factory.getBundle();
```

然后即可使用此BundleFactory对任意数据进行存取：

```java
// 将指定数据value使用key值存入bundle中
factory.put(key, value);
// 将指定key值的数据从bundle中取出，并转换为指定type数据类型再返回
T t = factory.get(key, Class<T>);
```

#### 示例说明

当前我们有两个不同的实体类：

```java
// NormalUser未实现序列化
public class NormalUser {
    public String username;
}
// SerialUser实现序列化
public class SerialUser implements Serializable {
    public String username;
}
```

然后进行数据存储：

```
NormalUser normalUser = new NormalUser();
SerialUser serialUser = new SerialUser();
normalUser.username = "this is normal user bean";
serialUser.username = "this is serial user bean";

BundleFactory factory = Parceler.createFactory(new Bundle());
factory.put("normal", normalUser);
factory.put("serial", serialUser);
	
Bundle result = factory.getBundle();
```

最终result数据：

![](https://user-gold-cdn.xitu.io/2018/3/30/162766469fe51c10?w=653&h=175&f=png&s=42165)

可以看到：NormalUser的实例被自动转换成了JSON进行存储了。

然后再从Bundle中进行数据读取：

```
NormalUser newNormalUser = factory.get("normal", NormalUser.class);
SerialUser newSerialUser = factory.get("serial", SerialUser.class);
```

![](https://user-gold-cdn.xitu.io/2018/3/30/1627669776c6172a?w=646&h=165&f=png&s=44335)

### 3. Bundle数据自动注入

最常见的使用场景就是在进行Activity跳转传值时使用：

发起注入操作可放置于基类中进行使用。所以可以将注入操作添加在Activity基类中：

```java
// 将注入器配置到基类中。一次配置,所有子类共同使用
public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 启动时从intent中读取数据并注入到当前类中。
        Parceler.toEntity(this,getIntent());
    }

    // ============可用以下方式方便的进行数据现场保护==========
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 将当前类中的使用注解的成员变量的值注入到outState中进行保存。
        Parceler.toBundle(this,outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // 需要恢复现场时。将数据从saveInstanceState中读取并注入当前类中。恢复现场
        Parceler.toEntity(this,savedInstanceState);
    }
}
```

然后就可以愉快的在各种子类中方便的进行使用了：

```java
public class UserActivity extends BaseActivity {

	// 直接使用。
	@Arg
	User user;
	@Arg
	Address address;
	@Arg
	int age;
	
	...

}
```

### 4. 使用BundleBuilder, 避免key值硬编码

以上方的UserActivity为例。需要跳转到此页面并将数据传递过去。我们需要将数据装入Intent中：

常见的使用流程都应该是下面这样的：

```
Bundle bundle = Parceler.createFactory(null)
			.put("user", user)
			.put("address", address)
			.put("age", age)
			.getBundle();
			
Intent intent = new Intent(this, UserActivity.class);
intent.addExtras(bundle);
startActivity(intent);
```

可以看到有很多的key值的硬编码。这在开发过程中其实是不推荐的。所以框架提供了BundleBuilder注解：

```java
@BundleBuilder
public class UserActivity extends BaseActivity {
	...
}
```

添加此注解后。将会生成对应的用于生成相应的bundle数据的类, 通过此生成类即可避免key值硬编码：

```java
// 将数据通过生成类UserActivityBundleBuilder进行装载
IBundleBuilder builder = UserActivityBundleBuilder.create(new Bundle())
	.setUser(user)
	.setAddress(address)
	.setAge(age);

// 使用自带提供的IntentLauncher类，方便的进行启动
Parceler.createLauncher(builder)
	.start(context);
	
// 或者需要直接使用装载的数据
Bundle result = builder.build();
```

### 5. 使用回调处理onActivityResult

使用此回调机制。需要在BaseActivity中配置回调的派发方法：

```
public class BaseActivity extends Activity {
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Parceler.dispatchActivityResult(this, requestCode, resultCode, data);
	}
}
```

然后即可直接在启动时分别配置回调监听器：

```
ActivityResultCallback callback = new ActivityResultCallback() {
            @Override
            public void onResult(int resultCode, Intent data) {
                // TODO
            }
        };

Parceler.createLauncher(TargetActivity.class, bundle)
	.setResultCallback(callback)
	.start(activity);
```

友情提醒：当配置了有效的回调之后。可以选择不再设置requestCode。不用再每次都抓耳挠腮的去为requestCode取值了。

### 5. 在kotlin环境下进行使用

请注意：在kotlin环境下，需要将annotationProcessor替换为kapt进行使用：

> kapt "com.github.yjfnypeu.Parceler:compiler:$LastestVersion"

#### 存取数据示例：

```

val factory = Parceler.createFactory(null)
// 存入数据
factory.put("name", name)
// 读取数据
user = factory.get("name", String::class.java)
```

#### 数据注入示例:

```
class KotlinLoginActivity : BaseActivity() {
	
	@Arg
	var username: String? = null
	@Arg
	var password: String? = null
	...
}
```

## 混淆配置
```Proguard
-keep class com.lzh.compiler.parceler.annotation.**
-keep class * implements com.lzh.compiler.parceler.ParcelInjector
-keepclasseswithmembernames class * {
    @com.lzh.compiler.parceler.annotation.Arg <fields>;
}
```

## 联系作者

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



