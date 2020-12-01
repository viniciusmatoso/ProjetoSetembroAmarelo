package br.com.projetosetembroamarelo.Controller

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import br.com.projetosetembroamarelo.Controller.Admin.MainAdminActivity
import br.com.projetosetembroamarelo.Controller.Usuario.MainUsuarioActivity
import br.com.projetosetembroamarelo.Model.DAO
import br.com.projetosetembroamarelo.Model.SQLite
import br.com.projetosetembroamarelo.Model.Usuario
import br.com.projetosetembroamarelo.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val preferenciasLogin = getSharedPreferences(getString(R.string.preferenciasLogin), Context.MODE_PRIVATE)

        val lembrarLogin = preferenciasLogin.getBoolean(getString(R.string.lembrarLogin), false)
        val email = preferenciasLogin.getString(getString(R.string.email), null) ?: return
        val senha = preferenciasLogin.getString(getString(R.string.senha), null) ?: return

        if(lembrarLogin){

            lembrarAcesso.isChecked = lembrarLogin
            txtEmail.setText(email)
            txtSenha.setText(senha)
        }

    }

    fun btnEntrar(view: View) {

        var usuarios = DAO.pegarUsuariosParaLogin(this)

        var email = txtEmail.text.toString()
        var senha = txtSenha.text.toString()

        var userEmail = ""
        var userSenha = ""
        var id = 0

        for (i in usuarios) {

            if (email == i.email && senha == i.senha) {

                userEmail = i.email
                userSenha = i.senha
                id = i.id.toInt()
            }
        }

        if (email.equals(DAO.email) && senha.equals(DAO.senha)) {


            val preferenciasLogin = getSharedPreferences(
                getString(R.string.preferenciasLogin),
                Context.MODE_PRIVATE
            )

            if (lembrarAcesso.isChecked) {

                preferenciasLogin.edit().apply() {
                    putBoolean(getString(R.string.lembrarLogin), lembrarAcesso.isChecked)
                    putString(getString(R.string.email), txtEmail.text.toString())
                    putString(getString(R.string.senha), txtSenha.text.toString())
                    commit()
                }
            } else {

                preferenciasLogin.edit().apply() {
                    putBoolean(getString(R.string.lembrarLogin), lembrarAcesso.isChecked)
                    putString(getString(R.string.email), null)
                    putString(getString(R.string.senha), null)
                    commit()
                }
            }

            val intent = Intent(this, MainAdminActivity::class.java).apply{
                putExtra("id", id)
            }
            startActivity(intent)
            finish()

        } else if (email.equals("") || senha.equals("")) {

            Toast.makeText(
                applicationContext,
                "Os campos email e senha são obrigatórios!",
                Toast.LENGTH_LONG
            ).show()

        } else if (email.equals(userEmail) && senha.equals(userSenha)) {

            val preferenciasLogin = getSharedPreferences(
                getString(R.string.preferenciasLogin),
                Context.MODE_PRIVATE
            )

            if (lembrarAcesso.isChecked) {

                preferenciasLogin.edit().apply() {
                    putBoolean(getString(R.string.lembrarLogin), lembrarAcesso.isChecked)
                    putString(getString(R.string.email), txtEmail.text.toString())
                    putString(getString(R.string.senha), txtSenha.text.toString())
                    commit()
                }
            } else {

                preferenciasLogin.edit().apply() {
                    putBoolean(getString(R.string.lembrarLogin), lembrarAcesso.isChecked)
                    putString(getString(R.string.email), null)
                    putString(getString(R.string.senha), null)
                    commit()
                }
            }

            val intent = Intent(this, MainUsuarioActivity::class.java).apply{
                putExtra("id", id)
            }
            startActivity(intent)
            finish()

        } else if (email !== userEmail || email !== DAO.email || senha !== userSenha) {

            Toast.makeText(
                applicationContext,
                "Email ou senha inválidos!",
                Toast.LENGTH_LONG
            ).show()
        }

    }

    fun btnCadastrar(view: View) {

        val intent = Intent(this, CadastroActivity::class.java)
        startActivity(intent)
        finish()
    }
}