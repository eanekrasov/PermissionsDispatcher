package permissions.dispatcher.processor.impl.java

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.MethodSpec

class WriteSettingsHelper : SensitivePermissionInterface {
    private val permissionUtils = ClassName.get("permissions.dispatcher", "PermissionUtils")
    private val settings = ClassName.get("android.provider", "Settings")
    private val intent = ClassName.get("android.content", "Intent")
    private val uri = ClassName.get("android.net", "Uri")

    override fun addHasSelfPermissionsCondition(builder: MethodSpec.Builder, activityVar: String, permissionField: String) {
        builder.beginControlFlow("if (\$T.hasSelfPermissions(\$N, true, \$N) || \$T.System.canWrite(\$N))", permissionUtils, activityVar, permissionField, settings, activityVar)
    }

    override fun addRequestPermissionsStatement(builder: MethodSpec.Builder, targetParam: String, activityVar: String, requestCodeField: String) {
        builder.addStatement("\$T intent = new \$T(\$T.ACTION_MANAGE_WRITE_SETTINGS, \$T.parse(\"package:\" + \$N.getPackageName()))", intent, intent, settings, uri, activityVar)
        builder.addStatement("\$N.startActivityForResult(intent, \$N)", targetParam, requestCodeField)
    }
}