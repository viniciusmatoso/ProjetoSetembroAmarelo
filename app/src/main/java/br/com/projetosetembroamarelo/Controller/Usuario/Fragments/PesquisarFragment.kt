package br.com.projetosetembroamarelo.Controller.Usuario.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.projetosetembroamarelo.Controller.Usuario.FraseActivity
import br.com.projetosetembroamarelo.Controller.Usuario.FraseActivityUsuario
import br.com.projetosetembroamarelo.Controller.Usuario.MainUsuarioActivity
import br.com.projetosetembroamarelo.Model.DAO
import br.com.projetosetembroamarelo.Model.Frase
import br.com.projetosetembroamarelo.R
import br.com.projetosetembroamarelo.View.FraseAdapter
import kotlinx.android.synthetic.main.activity_admin_main.*
import kotlinx.android.synthetic.main.activity_meus_bilhetes.*
import kotlinx.android.synthetic.main.fragment_pesquisar.*
import kotlinx.android.synthetic.main.fragment_pesquisar.rcvFrases


class PesquisarFragment : Fragment() {

    private var adapter: FraseAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_pesquisar, container, false)

        (context as MainUsuarioActivity).setarIconToolbar(R.drawable.logo_toolbar)
        (context as MainUsuarioActivity).setarToolbar("")

        var btnPesquisar = view.findViewById<Button>(R.id.btPesquisar)
        var texto = view?.findViewById<EditText>(R.id.campoTexto)

        val rcvFrases = view.findViewById<RecyclerView>(R.id.rcvFrases)

        var users = this.context?.let { DAO.pegarFrasesDoUsuario(it) }
        var frasesUser: MutableList<Frase> = arrayListOf()


        btnPesquisar.setOnClickListener {

            var msg = 0
            for(i in users!!){

                if(i.titulo == texto?.text.toString()){

                    frasesUser.removeAll(listOf(i))
                    frasesUser.add(i)
                    msg = 1
                }else{

                    frasesUser.removeAll(listOf(i))
                    msg = 2
                }

            }

            if(msg == 2){

                naoEncontrado()
            }

            val layoutManager = LinearLayoutManager(context)
            rcvFrases.layoutManager = layoutManager
            adapter = FraseAdapter(frasesUser)
            rcvFrases.adapter = adapter

            adapter?.notifyDataSetChanged()

        }

        val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener(){

            override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {

                e?.let{

                    val view = rcvFrases.findChildViewUnder(e.x, e.y)
                    view?.let{

                        val position = rcvFrases.getChildAdapterPosition(view)
                        val f = frasesUser[position].id

                        val intent = Intent(context, FraseActivityUsuario::class.java).apply{
                            putExtra("fraseId", f.toInt())
                        }
                        startActivity(intent)
                    }
                }
                return super.onSingleTapConfirmed(e)
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

        return view
    }

    private fun naoEncontrado() {
        Toast.makeText(context, "Frase não encontrada!", Toast.LENGTH_LONG).show()
    }


}