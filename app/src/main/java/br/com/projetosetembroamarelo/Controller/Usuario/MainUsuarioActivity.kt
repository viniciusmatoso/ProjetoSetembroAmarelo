package br.com.projetosetembroamarelo.Controller.Usuario

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import br.com.projetosetembroamarelo.Controller.GerenciarFraseActivity
import br.com.projetosetembroamarelo.Controller.LoginActivity
import br.com.projetosetembroamarelo.Controller.Usuario.Fragments.FrasesFragment
import br.com.projetosetembroamarelo.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main_usuario.*
import kotlinx.android.synthetic.main.fragment_frases.*


class MainUsuarioActivity : AppCompatActivity() {

    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_usuario)

        setSupportActionBar(toolbar)

        id = intent.getIntExtra("id", id)

        val navView: BottomNavigationView = findViewById(R.id.bottomNavigation)
        val navController = findNavController(R.id.navFragmentHost)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.FrasesFragment,
            R.id.PesquisaFragment,
            R.id.PerfilFragment
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun setarToolbar(texto: String){

        supportActionBar?.title = texto
    }

    fun setarIconToolbar(int: Int){
        supportActionBar?.setIcon(int)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.meusBilhetes -> {

                val intent = Intent(this, MeusBilhetesActivity::class.java).apply {
                    putExtra("id", id)
                }
                startActivity(intent)

            }
            R.id.sair -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


    }
}