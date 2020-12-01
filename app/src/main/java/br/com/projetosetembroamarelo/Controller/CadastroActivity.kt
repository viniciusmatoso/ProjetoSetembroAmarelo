package br.com.projetosetembroamarelo.Controller

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.projetosetembroamarelo.Model.DAO
import br.com.projetosetembroamarelo.Model.SQLite
import br.com.projetosetembroamarelo.Model.Usuario
import br.com.projetosetembroamarelo.R
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
    }

    fun btnConfirmar(view: View) {

        var sexo = ""
        if(rbMasc.isChecked){

            sexo = "M"
        }else{

            sexo = "F"
        }
        if(txtNome.text.toString().equals("") || txtIdade.text.toString().equals("") || sexo.equals("") ||
            txtEmail.text.toString().equals("") || txtSenha.text.toString().equals("") || txtConfirmacao.text.toString().equals("")) {

            Toast.makeText(this, "Todos os campos são obrigatórios!", Toast.LENGTH_LONG).show()


            }else if(txtSenha.text.toString() != txtConfirmacao.text.toString()){

                Toast.makeText(this, "A senha de confirmação está diferente da digitada, por favor repita a senha!", Toast.LENGTH_LONG).show()
            }else{
                val user = Usuario(txtNome.text.toString(), txtIdade.text.toString().toInt(), sexo, txtEmail.text.toString(), txtSenha.text.toString())
                DAO.pegarUsuariosParaCadastro(this)
                DAO.addUser(user)

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()

            }


    }
    fun btnRetornar(view: View) {

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}