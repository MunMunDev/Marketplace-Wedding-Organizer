package com.abcd.vian_marketplaceweddingorganizer.ui.activity.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.abcd.vian_marketplaceweddingorganizer.data.model.UsersModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivityLoginBinding
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.admin.AdminMainActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.register.RegisterActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.main.MainActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.main.WeddingOrganizerMainActivity
import com.abcd.vian_marketplaceweddingorganizer.utils.LoadingAlertDialog
import com.abcd.vian_marketplaceweddingorganizer.utils.SharedPreferencesLogin
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var sharedPreferencesLogin: SharedPreferencesLogin
    @Inject
    lateinit var loading : LoadingAlertDialog
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        konfigurationUtils()
        button()
        setDataSebelumnya()
        getData()
    }

    private fun setDataSebelumnya() {
        val extras = intent.extras
        if(extras != null) {
            val username = extras.getString("username")
            val password = extras.getString("password")

            loginBinding.etUsername.setText(username)
            loginBinding.etPassword.setText(password)
        }
    }

    private fun konfigurationUtils() {
        sharedPreferencesLogin = SharedPreferencesLogin(this@LoginActivity)
    }

    private fun button(){
        btnDaftar()
        btnLogin()
    }

    private fun btnLogin() {
        loginBinding.apply {
            btnLogin.setOnClickListener{
                if(etUsername.text.isNotEmpty() && etPassword.text.isNotEmpty()){
                    cekUsers(etUsername.text.toString(), etPassword.text.toString())
                }
                else{
                    if(etUsername.text.isEmpty()){
                        etUsername.error = "Masukkan Username"
                    }
                    if(etPassword.text.isEmpty()){
                        etPassword.error = "Masukkan Password"
                    }
                }
            }
        }
    }

    private fun btnDaftar() {
        loginBinding.tvDaftar.setOnClickListener{
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    private fun cekUsers(username: String, password: String) {
        loginViewModel.fetchDataUser(username, password)
    }

    private fun getData(){
        loginViewModel.getDataUser().observe(this@LoginActivity){result ->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@LoginActivity)
                is UIState.Success-> setSuccessDataUser(result.data)
                is UIState.Failure -> setFailureDataUser(result.message)
            }
        }
    }

    private fun setFailureDataUser(message: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
        loading.alertDialogCancel()
    }

    private fun setSuccessDataUser(data: ArrayList<UsersModel>) {
        loading.alertDialogCancel()
        if(data.isNotEmpty()){
            successFetchLogin(data)
        } else{
            Toast.makeText(this@LoginActivity, "Data tidak ditemukan \nPastikan Username dan Password Benar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun successFetchLogin(userModel: ArrayList<UsersModel>){
//        loading.alertDialogCancel()
        var valueIdUser = 0
        userModel[0].idUser?.let {
            valueIdUser = it.toInt()
        }
        val valueIdWO = userModel[0].id_wo!!
        val valueNama = userModel[0].nama.toString()
        val valueAlamat = userModel[0].alamat.toString()
        val valueNomorHp = userModel[0].nomorHp.toString()
        val valueUsername = userModel[0].username.toString()
        val valuePassword = userModel[0].password.toString()
        val valueSebagai= userModel[0].sebagai.toString()
        val valueLogo = userModel[0].logo_wo.toString()
        val valueDeskripsi= userModel[0].deskripsi_wo.toString()

        try{
            Toast.makeText(this@LoginActivity, "Login Berhasil", Toast.LENGTH_SHORT).show()
            if(valueSebagai == "wo"){
                sharedPreferencesLogin.setLogin(valueIdUser, valueIdWO, valueNama, valueAlamat, valueNomorHp, valueUsername, valuePassword, valueSebagai, valueDeskripsi, valueLogo)
                startActivity(Intent(this@LoginActivity, WeddingOrganizerMainActivity::class.java))
            } else{
                sharedPreferencesLogin.setLogin(valueIdUser, valueIdWO, valueNama, valueAlamat, valueNomorHp, valueUsername, valuePassword, valueSebagai, "", "")
                if(valueSebagai=="user"){
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                } else{
                    Toast.makeText(this@LoginActivity, "Selamat Datang Admin", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, AdminMainActivity::class.java))
                }
            }
            finish()
        } catch (ex: Exception){
            Toast.makeText(this@LoginActivity, "gagal: $ex", Toast.LENGTH_SHORT).show()
        }
    }

    var tapDuaKali = false
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (tapDuaKali){
            super.onBackPressed()
        }
        tapDuaKali = true
        Toast.makeText(this@LoginActivity, "Tekan Sekali Lagi untuk keluar", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({
            tapDuaKali = false
        }, 2000)

    }
}