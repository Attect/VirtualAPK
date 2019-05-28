package com.didi.virtualapk.support

import com.android.build.gradle.AppPlugin
import com.didi.virtualapk.os.Build
import com.didi.virtualapk.tasks.AssemblePlugin
import com.didi.virtualapk.utils.Reflect
import org.gradle.api.Action
import org.gradle.api.Project

import static com.didi.virtualapk.os.Build.VERSION_CODE.V3_1_X
import static com.didi.virtualapk.os.Build.VERSION_CODE.V3_1_X
import static com.didi.virtualapk.os.Build.VERSION_CODE.V3_3_X

/**
 * @author 潘志会 @ Zhihu Inc.
 * @since 2019/05/27
 */
class TaskFactoryCompat {
    static getTaskFactory(AppPlugin appPlugin, Project project) {
        def taskFactory = null
        if (Build.V3_1_OR_LATER) {
            taskFactory = appPlugin.taskManager.taskFactory
        } else {
            taskFactory = Reflect.on('com.android.build.gradle.internal.TaskContainerAdaptor')
                    .create(project.tasks)
                    .get()
        }
        return taskFactory
    }

    static add(taskFactory, String name, Class clazz, Action configAction) {
        if (Build.isSupportVersion(V3_3_X)) {
            taskFactory.register(name, clazz, configAction)
        } else {
            taskFactory.create(name, clazz, configAction)
        }
    }

    static configure(taskFactory, String name, Action action) {
        if (Build.V3_1_OR_LATER) {
            taskFactory.configure(name, action)
        } else {
            taskFactory.named(name, action)
        }
    }


}
