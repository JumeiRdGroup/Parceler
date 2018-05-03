package com.lzh.compiler.parcelerdemo

import android.os.Bundle
import android.widget.EditText
import com.lzh.compiler.parceler.annotation.Arg
import com.lzh.compiler.parceler.annotation.BundleBuilder
import com.lzh.compiler.parcelerdemo.base.BaseActivity

/**
 * 在kotlin中使用示例
 * Created by haoge on 2018/4/11.
 */
@BundleBuilder
class KotlinLoginActivity : BaseActivity() {

    @Arg
    var username: String? = null
    @Arg
    var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<EditText>(R.id.username).setText(username)
        findViewById<EditText>(R.id.password).setText(password)
    }

    override fun onBackPressed() {
        setResult(2)
        finish()
    }
}