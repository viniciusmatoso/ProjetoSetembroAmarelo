package br.com.projetosetembroamarelo.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import br.com.projetosetembroamarelo.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler()
        handler.postDelayed ( {

            startActivity(Intent(this , LoginActivity::class.java ))

            finish ()
        } , 2000 )
    }
    }
