package br.com.projetosetembroamarelo.Controller.Usuario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import br.com.projetosetembroamarelo.Model.DAO
import br.com.projetosetembroamarelo.R
import kotlinx.android.synthetic.main.activity_admin_main.*
import kotlinx.android.synthetic.main.activity_frase.*

class FraseActivity : AppCompatActivity() {

    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frase)

        setSupportActionBar(toolbarFrase)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        position = intent.getIntExtra("position", position)
        var frase = DAO.getFrase(position)

        supportActionBar?.title = frase.titulo

        txtTitulo.setText(frase.titulo)
        txtFrase.setText(frase.texto)
        txtAutor.setText(frase.autor)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}