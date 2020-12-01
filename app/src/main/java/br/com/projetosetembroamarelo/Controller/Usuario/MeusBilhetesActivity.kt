package br.com.projetosetembroamarelo.Controller.Usuario

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MenuItem
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.projetosetembroamarelo.Model.DAO
import br.com.projetosetembroamarelo.Model.Frase
import br.com.projetosetembroamarelo.Model.SQLite
import br.com.projetosetembroamarelo.R
import br.com.projetosetembroamarelo.View.FraseAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_meus_bilhetes.*

class MeusBilhetesActivity : AppCompatActivity() {

    var id = 0
    private var adapter: FraseAdapter? = null

    var frasesUser: MutableList<Frase> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meus_bilhetes)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        id = intent.getIntExtra("id", id)

        supportActionBar?.title = "Seus bilhetes"

        var sql = SQLite(this)


        var frases = sql.pegarFrasesUsuarioId(id)

        val layoutManager = LinearLayoutManager(this)
        rcvFrasesUsuario.layoutManager = layoutManager
        adapter = FraseAdapter(frases)
        rcvFrasesUsuario.adapter = adapter

        val gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener(){

            override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {

                e?.let{

                    val view = rcvFrasesUsuario.findChildViewUnder(e.x, e.y)
                    view?.let{

                        val position = rcvFrasesUsuario.getChildAdapterPosition(view)
                        val f = frases[position].id

                        val intent = Intent(this@MeusBilhetesActivity, FraseActivityUsuario::class.java).apply{
                            putExtra("fraseId", f.toInt())
                        }
                        startActivity(intent)
                    }
                }
                return super.onSingleTapConfirmed(e)
            }

        })

        rcvFrasesUsuario.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener{
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            android.R.id.home -> {
                finish()
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

        if(requestCode == 2){

            if(resultCode == Activity.RESULT_OK){

                Snackbar.make(layMeusBilhetes, "A Frase '${fraseTitulo}' foi alterada!", Snackbar.LENGTH_LONG).show()
                adapter!!.notifyDataSetChanged()
            }
        }
    }
}