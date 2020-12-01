package br.com.projetosetembroamarelo.Model

import android.content.Context
import android.util.Log

object DAO {

    //admin inicio
    var email = "admin@gmail.com"
        private set
    var senha = "123456"
        private set
    var id = 0
        private set
    //admin fim

    var users: MutableList<Usuario> = arrayListOf()
        private set

    var frases: MutableList<Frase> = arrayListOf()
        private set

    private var database: SQLite? = null

    fun pegarFrases(context: Context){

        database = SQLite(context)
        database?.let{
            frases = it.getTodasFrases()
        }
    }

    fun pegarUsuariosParaCadastro(context: Context){

        database = SQLite(context)
        database?.let{
            users = it.getTodosUsuarios()
        }
    }

    fun pegarUsuariosParaLogin(context: Context): MutableList<Usuario>{

        var retornarUsuarios = SQLite(context).getTodosUsuarios()
        return retornarUsuarios
    }

    fun pegarFrasesDoUsuario(context: Context): MutableList<Frase>{

        var retornarFrases = SQLite(context).getTodasFrases()
        return retornarFrases
    }

    fun carregarFrasesUsuario(userId: Int){

        database?.let{

            frases = it.pegarFrasesUsuarioId(userId)
        }

    }

    fun carregarFrases(user: Usuario): MutableList<Frase>{

        carregarFrasesUsuario(user.id.toInt())

        return frases
    }

    fun addUser(user: Usuario){
        val id = database?.addUser(user) ?: return

        if (id > 0) {
            user.id = id
            users.add(user)
        }
    }

    fun getUser(id: Int): Usuario {

        var i = 0
        var user = Usuario("", 0, "", "", "")
        for(i in users){

            if(i.id == user.id){

                user.nome = i.nome
                user.idade = i.idade
                user.sexo = i.sexo
                user.email = i.email
                user.senha = i.senha
                user.id = id.toLong()
                user = i
            }
        }
        return user
    }

    fun editUser(user: Usuario) {

        database?.editUser(user) ?: return

    }

    fun getFrase(position: Int): Frase{
        return frases.get(position)
    }

    fun addFrases(frase: Frase){

        val id = database?.addFrase(frase) ?: return

        if(id > 0){

            frase.id = id
            frases.add(frase)
        }
    }

    fun editFrases(frase: Frase, position: Int){

        frase.id = frases[position].id
        val count = database?.editFrase(frase) ?: return

        if(count > 0){
            frases.set(position, frase)
        }
    }

    fun delFrases(position: Int){

        val count = database?.delFrase(frases[position]) ?: return

        if(count > 0){

            frases.removeAt(position)
        }
    }
}