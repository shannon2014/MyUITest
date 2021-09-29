package com.shannon.testgradleplugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * gradle插件开发学习
 * 参考https://www.jianshu.com/p/75bf31149a53
 */
class TestGroovyPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        def extension = project.extensions.create("publishPluginExtension", PublishPluginExtension)
        project.task("publishPluginTest"){
            doLast {
                println("新内容是：${extension.msg}")
            }
        }
    }
}