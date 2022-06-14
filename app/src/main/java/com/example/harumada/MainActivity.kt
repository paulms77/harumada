package com.example.harumada

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val email_login_btn: ImageButton = findViewById(R.id.login_btn)
        auth = FirebaseAuth.getInstance()
        email_login_btn.setOnClickListener {
            singinAndSignup()
        }
    }
    fun singinAndSignup(){
        val email_edittext : TextView = findViewById(R.id.email_edittext)
        val password_edittext : TextView = findViewById(R.id.password_edittext)
        auth?.createUserWithEmailAndPassword(email_edittext.text.toString(),password_edittext.text.toString())
            ?.addOnCompleteListener {
                    task ->
                if(task.isSuccessful){
                    moveMainpage(task.result.user)
                }else if(task.exception?.message.isNullOrEmpty()){
                    Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
                }else{
                    signinEmail()
                }
            }
    }
    fun signinEmail(){
        val email_edittext : TextView = findViewById(R.id.email_edittext)
        val password_edittext : TextView = findViewById(R.id.password_edittext)
        auth?.signInWithEmailAndPassword(email_edittext.text.toString(),password_edittext.text.toString())
            ?.addOnCompleteListener {
                    task ->
                if(task.isSuccessful){
                    moveMainpage(task.result.user)
                }else{
                    Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
                }
            }
    }
    fun moveMainpage(user:FirebaseUser?){
        if(user != null){
            startActivity(Intent(this,SecondActivity::class.java))
        }
    }
}

