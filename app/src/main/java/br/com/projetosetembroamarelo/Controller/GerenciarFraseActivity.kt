package br.com.projetosetembroamarelo.Controller

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import br.com.projetosetembroamarelo.Model.DAO
import br.com.projetosetembroamarelo.Model.Frase
import br.com.projetosetembroamarelo.R
import kotlinx.android.synthetic.main.activity_admin_frase.*
import kotlinx.android.synthetic.main.activity_admin_main.toolbar

class GerenciarFraseActivity : AppCompatActivity() {

    var tipo = 0
    var position = 0
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_frase)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Gerenciar Bilhete"

        tipo = intent.getIntExtra("tipo", 1)
        id = intent.getIntExtra("id", id)

        if(tipo == 2){

            position = intent.getIntExtra("position", 0)
            val frase = DAO.getFrase(position)

            etTitulo.setText(frase.titulo)
            etTexto.setText(frase.texto)
            etAutor.setText(frase.autor)
        }

        btSalvarFrase.setOnClickListener {

            var user = ""
            if(id == 0){

                user = DAO.id.toString()
            }else{

                user = id.toString()
            }
            val frase = Frase(etTitulo.text.toString(), etTexto.text.toString(), etAutor.text.toString(), user.toInt())

            if(tipo == 1){

                DAO.addFrases(frase)
            }
            else if(tipo == 2){

                DAO.editFrases(frase, position)
            }

            val intent = Intent().apply{
                putExtra("fraseTitulo", frase.titulo)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
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