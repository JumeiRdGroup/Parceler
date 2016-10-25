#Parceler中文使用说明
Parceler可用于将<code>Bundle</code>中的数据注入到任意实体类中或者将实体类中的数据注入到<code>Bundle</code>中<br>

Parceler提供两种方式让对<code>Bundle</code>的操作更加方便。

第一个。可以通过对任意类的成员变量设置<code>Arg</code>注解。框架会生成对应的注入代码让你可以通过一行代码实现数据注入

```Java
public class UserInfo {
    @Arg("renameKey")// 可以对key值进行重新指定
    String username;// 字段类型需要符合Bundle要求。即数据类型需要是能放入到Bundle中类型
    @NonNull // 指定password字段不能为null。即数据源Bundle中必须含有此字段
    @Arg
    String password;
    @Arg // 你也可以对私有变量添加注解,在这种情况下。你必须提供一对get/set方法。
    private String address;
    public UserInfo (Bundle data) {
        // 将bundle中数据注入到字段username.password中
        Parceler.injectToTarget(this,data);
    }

    public Bundle getBundle () {
        // 将UserInfo类中的数据。注入到Bundle中去。
        Bundle data = new Bundle();
        Parceler.injectToData(this,data);
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

其次,框架提供另一个注解：<code>Dispatcher</code>.目前此注解只能使用于Activity类之上。
当对Activity使用了此注解后。会生成带Dispatcher后缀的java文件。可以通过此java文件很方便的提供跳转操作。以LoginActivity为例：

```Java
// 将注入器配置到基类中。一次配置,所有子类共同使用
public abstract class BaseActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parceler.injectToTarget(this,getIntent() == null ? null : getIntent().getExtras());
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

```Java
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

需要跳转到LoginActivity时, 使用生成的LoginActivityDispatcher类：

```
new LoginActivityDispatcher(password).setUsername(username).requestCode(100).start(activity);
```
如果你需要直接操作Intent, 也可以:
```
Intent intent = new LoginActivityDispatcher(password).setUsername(username).getIntent(activity);
```