package com.lzh.compiler.parceler;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import java.util.Random;

/**
 * 用于配合BundleBuilder生成类进行使用的启动器。可用于进行Activity/Service/BroadcastReceiver启动。
 * @author haoge on 2017/12/26.
 */
public class IntentLauncher {

    private ActivityResultCallback callback;
    private int requestCode = -1;
    private Bundle options;
    private Bundle bundle;
    private Class<?> target;
    private Intent extra;

    private static Random sCodeGenerator;

    private IntentLauncher(Bundle bundle, Class<?> target) {
        this.bundle = bundle;
        this.target = target;
    }

    /**
     * 创建一个Intent启动器。此启动器关联此Builder类。
     *
     * <p>此Builder实例用于提供传输的Bundle数据。以及所关联的目标类：{@link IBundleBuilder#getTarget()}。
     *
     * <p>目前此此启动器所支持的关联目标类包括Activity、Service以及BroadcastReceiver的子类。
     *
     * @param builder 提供数据的Builder
     */
    static IntentLauncher create(IBundleBuilder builder) {
        return new IntentLauncher(builder.build(), builder.getTarget());
    }

    static IntentLauncher create(Bundle bundle, Class<?> target) {
        return new IntentLauncher(bundle, target);
    }

    /**
     * 设置startActivity时所需要的requestCode。对应{@link Activity#startActivityForResult(Intent, int)}
     *
     * @param requestCode 请求码
     */
    public IntentLauncher setRequestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    /**
     * 设置在onActivityResult时使用的回调。
     * @param callback callback
     */
    public IntentLauncher setResultCallback(ActivityResultCallback callback) {
        this.callback = callback;
        return this;
    }

    /**
     * 设置startActivity时所需要的options参数。对应{@link Activity#startActivityForResult(Intent, int, Bundle)}
     * @param options {@link Activity#startActivityForResult(Intent, int, Bundle)}
     */
    public IntentLauncher setOptions(Bundle options) {
        this.options = options;
        return this;
    }

    /**
     * 设置Intent镜像。在进行Intent创建时。若设置的extra Intent不为null。则将对此intent实例进行clone并替换Classname提供使用
     * @param intent Intent镜像
     */
    public IntentLauncher setExtra(Intent intent) {
        this.extra = intent;
        return this;
    }

    /**
     * 根据之前配置的数据，获取所需要的Intent实例。
     */
    public Intent getIntent(Context context) {
        if (context == null) {
            return null;
        }

        Class<?> target = this.target;
        if (!Activity.class.isAssignableFrom(target)
                && !Service.class.isAssignableFrom(target)
                && !BroadcastReceiver.class.isAssignableFrom(target)) {
            return null;
        }

        Intent intent;
        if (extra != null) {
            intent = new Intent(extra);
            intent.setClass(context, target);
            extra = null;
        } else {
            intent = new Intent(context, target);
        }
        intent.putExtras(this.bundle);
        return intent;
    }

    /**
     * 使用此Context实例。根据对{@link IBundleBuilder#getTarget()}的类型判断，进行Intent启动。
     *
     * <ol>
     *     <li>
     *         当类型为Activity的子类，则根据context是否为Activity实例。使用
     *         {@link Activity#startActivityForResult(Intent, int, Bundle)}或者{@link Context#startActivity(Intent)}
     *         进行启动
     *     </li>
     *     <li>当类型为Service的子类，则使用{@link Context#startService(Intent)}进行启动</li>
     *     <li>当类型为BroadcastReceiver的子类，则使用{@link Context#sendBroadcast(Intent)}进行启动</li>
     *     <li>当需要</li>
     * </ol>
     */
    public void start(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = getIntent(context);
        Class<?> target = this.target;

        if (Activity.class.isAssignableFrom(target)) {
            startActivity(context, intent);
        } else if (Service.class.isAssignableFrom(target)) {
            context.startService(intent);
        } else if (BroadcastReceiver.class.isAssignableFrom(target)) {
            context.sendBroadcast(intent);
        }
    }

    private void startActivity(Context context, Intent intent) {
        if (context instanceof Activity) {
            if (callback != null && requestCode == -1) {
                requestCode = sCodeGenerator.nextInt(Integer.MAX_VALUE);
            }
            Activity activity = (Activity) context;
            if (options != null && Build.VERSION.SDK_INT >= 16) {
                activity.startActivityForResult(intent, requestCode, options);
            } else {
                activity.startActivityForResult(intent, requestCode);
            }
            ActivityResultDispatcher.get().bindRequestArgs(activity, requestCode, callback);
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    static {
        sCodeGenerator = new Random();
    }

}
