package br.com.projetosetembroamarelo.Controller.Usuario.Fragments

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.projetosetembroamarelo.Controller.GerenciarFraseActivity
import br.com.projetosetembroamarelo.Controller.LoginActivity
import br.com.projetosetembroamarelo.Controller.Usuario.FraseActivity
import br.com.projetosetembroamarelo.Controller.Usuario.MainUsuarioActivity
import br.com.projetosetembroamarelo.Controller.Usuario.MeusBilhetesActivity
import br.com.projetosetembroamarelo.Model.DAO
import br.com.projetosetembroamarelo.Model.SQLite
import br.com.projetosetembroamarelo.R
import br.com.projetosetembroamarelo.View.FraseAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_admin_main.*
import kotlinx.android.synthetic.main.activity_main_usuario.*
import kotlinx.android.synthetic.main.fragment_frases.*
import kotlinx.android.synthetic.main.fragment_frases.view.*
import kotlinx.android.synthetic.main.fragment_frases.view.layFragmentFrases


class FrasesFragment : Fragment() {

    private var adapter: FraseAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_frases, container, false)

        var id = 0
        id = activity?.intent?.getIntExtra("id", id)!!

        var user = 0
        user = requireActivity().intent?.getIntExtra("user", user)!!

        this.context?.let { DAO.pegarFrases(it) }

        (context as MainUsuarioActivity).setarIconToolbar(R.drawable.logo_toolbar)
        (context as MainUsuarioActivity).setarToolbar("")

        view.fab?.setOnClickListener {
            val intent = Intent(context, GerenciarFraseActivity::class.java).apply{
                putExtra("tipo",1)
                putExtra("id", id)
            }
            startActivityForResult(intent, 1)
        }

        val rcvFrases = view.findViewById<RecyclerView>(R.id.rcvFrases)

        val layoutManager = LinearLayoutManager(activity)
        rcvFrases.layoutManager = layoutManager
        adapter = FraseAdapter(DAO.frases)
        rcvFrases.adapter = adapter

        val gestureDetector = GestureDetector(activity, object : GestureDetector.SimpleOnGestureListener(){

            override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {

                e?.let{

                    val view = rcvFrases.findChildViewUnder(e.x, e.y)
                    view?.let{

                        val position = rcvFrases.getChildAdapterPosition(view)
                        DAO.getFrase(position)

                        val intent = Intent(context, FraseActivity::class.java).apply{
                            putExtra("position", position)
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var fraseTitulo = ""
        data?.let{

            fraseTitulo = data.getStringExtra("fraseTitulo").toString()
        } ?: run{

            fraseTitulo = "Erro de nome"
        }

        if(requestCode == 1){

            if(resultCode == Activity.RESULT_OK){

                Snackbar.make(layFragmentFrases, "A Frase '${fraseTitulo}' foi adicionada!", Snackbar.LENGTH_LONG).show()
                adapter!!.notifyDataSetChanged()
            }
        }
    }


}