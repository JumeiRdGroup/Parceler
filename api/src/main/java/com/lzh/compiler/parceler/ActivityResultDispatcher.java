/*
 * Copyright (C) 2017 Haoge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lzh.compiler.parceler;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class ActivityResultDispatcher {

    private Map<Activity, List<RequestArgs>> container = new HashMap<>();

    private static ActivityResultDispatcher dispatcher = new ActivityResultDispatcher();
    private ActivityResultDispatcher(){ }
    public static ActivityResultDispatcher get() {
        return dispatcher;
    }

    void bindRequestArgs(Activity activity, int requestCode, ActivityResultCallback callback) {
        if (activity.isFinishing()
                || callback == null
                || requestCode == -1) {
            return;
        }

        List<RequestArgs> list = findListByKey(activity);
        list.add(new RequestArgs(requestCode, callback));
    }

    boolean dispatchActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (!container.containsKey(activity)) {
            return false;
        }

        boolean handle = false;
        List<RequestArgs> list = findListByKey(activity);
        for (RequestArgs args:list) {
            if (args.requestCode == requestCode) {
                args.callback.onResult(resultCode, data);
                list.remove(args);
                handle = true;
                break;
            }
        }

        releaseInvalidItems();
        return handle;
    }

    // 移除无效的条目：比如当前activity在后台时被回收了
    private void releaseInvalidItems() {
        Set<Activity> keys = container.keySet();
        Iterator<Activity> iterator = keys.iterator();
        while (iterator.hasNext()) {
            Activity next = iterator.next();
            if (next.isFinishing()
                    || container.get(next).isEmpty()
                    || (Build.VERSION.SDK_INT >= 17 && next.isDestroyed()) ) {
                iterator.remove();
            }
        }
    }

    List<RequestArgs> findListByKey(Activity activity) {
        List<RequestArgs> list = container.get(activity);
        if (list == null) {
            list = new ArrayList<>();
            container.put(activity, list);
        }
        return list;
    }

    private static class RequestArgs {
        int requestCode;
        ActivityResultCallback callback;

        public RequestArgs(int requestCode, ActivityResultCallback callback) {
            this.requestCode = requestCode;
            this.callback = callback;
        }
    }
}
