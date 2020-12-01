package br.com.projetosetembroamarelo.Controller.Admin

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.projetosetembroamarelo.Controller.GerenciarFraseActivity
import br.com.projetosetembroamarelo.Controller.LoginActivity
import br.com.projetosetembroamarelo.Model.DAO
import br.com.projetosetembroamarelo.R
import br.com.projetosetembroamarelo.View.FraseAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_admin_main.*

class MainAdminActivity : AppCompatActivity() {

    var id = 0
    val ADD = 1
    val UPDATE = 2

    private var adapter: FraseAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        id = intent.getIntExtra("id", id)
        DAO.pegarFrases(this)

        fab.setOnClickListener {
            val intent = Intent(this@MainAdminActivity, GerenciarFraseActivity::class.java).apply{
                putExtra("tipo", ADD)
                putExtra("id", id)
            }
            startActivityForResult(intent, ADD)
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setIcon(R.drawable.logo_toolbar)
        supportActionBar?.title = ""

        val layoutManager = LinearLayoutManager(this)
        rcvFrases.layoutManager = layoutManager
        adapter = FraseAdapter(DAO.frases)
        rcvFrases.adapter = adapter

        val gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener(){

            override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {

                e?.let{

                    val view = rcvFrases.findChildViewUnder(e.x, e.y)
                    view?.let{

                        val position = rcvFrases.getChildAdapterPosition(view)
                        val frase = DAO.getFrase(position)

                        val intent = Intent(this@MainAdminActivity, GerenciarFraseActivity::class.java).apply{
                            putExtra("tipo", UPDATE)
                            putExtra("position", position)
                        }
                        startActivityForResult(intent, UPDATE)
                    }
                }
                return super.onSingleTapConfirmed(e)
            }

            override fun onLongPress(e: MotionEvent?) {
                super.onLongPress(e)

                e?.let{

                    val view = rcvFrases.findChildViewUnder(e.x, e.y)
                    view?.let{

                        val position = rcvFrases.getChildAdapterPosition(view)
                        val frase = DAO.getFrase(position)

                        val dialog = AlertDialog.Builder(this@MainAdminActivity, R.style.AlertDialogCustom)
                        dialog.setMessage("Tem certeza que deseja excluir esta frase?")
                        dialog.setPositiveButton("Excluir", DialogInterface.OnClickListener { dialog, which ->
                            DAO.delFrases(position)
                            adapter!!.notifyDataSetChanged()
                            Snackbar.make(layAdminMain, "A Frase '${frase.titulo}' foi apagada!", Snackbar.LENGTH_LONG).show()
                        })
                        dialog.setNegativeButton("Cancelar", null)
                        dialog.show()
                    }
                }
            }
        })

        rcvFrases.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener{
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                TODO("Not yet implemented")
            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {

                val child = rv.findChildViewUnder(e.x, e.y)

                return (child != null && gestureDetector.onTouchEvent(e))
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_admin, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.btSair -> {

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var fraseTitulo = ""
        data?.let{

            fraseTitulo = data.getStringExtra("fraseTitulo").toString()
        } ?: run{

            fraseTitulo = "Erro de nome"
        }

        if(requestCode == ADD){

            if(resultCode == Activity.RESULT_OK){

                Snackbar.make(layAdminMain, "A Frase '${fraseTitulo}' foi adicionada!", Snackbar.LENGTH_LONG).show()
                adapter!!.notifyDataSetChanged()
            }
        }

        if(requestCode == UPDATE){

            if(resultCode == Activity.RESULT_OK){

                Snackbar.make(layAdminMain, "A Frase '${fraseTitulo}' foi alterada!", Snackbar.LENGTH_LONG).show()
                adapter!!.notifyDataSetChanged()
            }
        }
    }
}