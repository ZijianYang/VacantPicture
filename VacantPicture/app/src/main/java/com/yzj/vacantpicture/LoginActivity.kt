package com.yzj.vacantpicture

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.yzj.vacantpicture.database.AppDatabase
import com.yzj.vacantpicture.basic.BaseActivity
import com.yzj.vacantpicture.basic.MessageDigestUtils
import com.yzj.vacantpicture.database.entity.User
import com.yzj.vacantpicture.databinding.ActivityLoginBinding
import com.yzj.vacantpicture.service.ServiceCreator.ServiceCreator
import com.yzj.vacantpicture.service.ServiceResponse
import com.yzj.vacantpicture.service.user.UserInfoService
import com.yzj.vacantpicture.service.user.UserLoginRequest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val prefs = getPreferences(Context.MODE_PRIVATE)
        val isRemember = prefs.getBoolean("remember_password", false)
        if (isRemember) {
            // 将账号和密码都设置到文本框中
            val account = prefs.getString("account", "")
            val password = prefs.getString("password", "")
            binding.accountEdit.setText(account)
            binding.passwordEdit.setText(password)
            binding.rememberPass.isChecked = true
        }

        binding.login.setOnClickListener {
            val account = binding.accountEdit.text.toString()
            val password = binding.passwordEdit.text.toString()

//            val userDao = AppDatabase.getDatabase(this).userDao()
//            val user =User("admin", MessageDigestUtils.md5("123456"))
//            val async=GlobalScope.async {
//                userDao.insertUser(user);
//            }
//            Toast.makeText(this,   user.id.toString(),
//                Toast.LENGTH_SHORT).show()
            val userService = ServiceCreator.create<UserInfoService>()
            val userLoginRequest=UserLoginRequest(account,password)
            val loginCall = userService.login(userLoginRequest)
            //var userLoginResponse=loginCall.execute().body()
            loginCall.enqueue(object : Callback<ServiceResponse>{
                override fun onResponse(
                    call: Call<ServiceResponse>,
                    response: Response<ServiceResponse>
                ) {
                    if (response.body()?.ResponseResultEnum==0) {
                        val editor = prefs.edit()
                        if (
                            binding.
                            rememberPass.isChecked) { // 检查复选框是否被选中
                            editor.putBoolean("remember_password", true)
                            editor.putString("account", account)
                            editor.putString("password", password)
                        } else {
                            editor.clear()
                        }
                        editor.apply()
                        val intent = Intent()
                        intent.setClass(this@LoginActivity,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "account or password is invalid",
                            Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ServiceResponse>, t: Throwable) {
                    Log.d("LoginActivity", "api request error"+t.message)
                    Toast.makeText(this@LoginActivity, "api request error "+t.message,
                        Toast.LENGTH_SHORT).show()
                }
            })


//            // 如果账号是admin且密码是123456，就认为登录成功
//            //if (account == "admin" && password == "123456") {
//            if (userLoginResponse?.ResponseResultEnum==0) {
//                val editor = prefs.edit()
//                if (
//                    binding.
//                    rememberPass.isChecked) { // 检查复选框是否被选中
//                    editor.putBoolean("remember_password", true)
//                    editor.putString("account", account)
//                    editor.putString("password", password)
//                } else {
//                    editor.clear()
//                }
//                editor.apply()
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                finish()
//            } else {
//                Toast.makeText(this, "account or password is invalid",
//                    Toast.LENGTH_SHORT).show()
//            }
        }
    }
}