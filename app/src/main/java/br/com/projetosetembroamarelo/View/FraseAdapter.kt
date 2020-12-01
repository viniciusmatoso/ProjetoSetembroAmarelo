package br.com.projetosetembroamarelo.View

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.projetosetembroamarelo.Model.Frase
import br.com.projetosetembroamarelo.R

class FraseAdapter(var frases: MutableList<Frase>) : RecyclerView.Adapter<FraseAdapter.FraseHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FraseHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rcv_frases, parent, false)

        return FraseHolder(view)
    }

    override fun getItemCount(): Int {
        return frases.size
    }

    override fun onBindViewHolder(holder: FraseHolder, position: Int) {

        val frase = frases.get(position)

        holder.txtTitulo.text = "${frase.titulo}"
        holder.txtAutor.text = "${frase.autor}"
    }

    inner class FraseHolder(view: View) : RecyclerView.ViewHolder(view){

        var txtTitulo: TextView
        var txtAutor: TextView

        init{
            txtTitulo = view.findViewById(R.id.txtTitulo)
            txtAutor = view.findViewById(R.id.txtAutor)

        }
    }
}