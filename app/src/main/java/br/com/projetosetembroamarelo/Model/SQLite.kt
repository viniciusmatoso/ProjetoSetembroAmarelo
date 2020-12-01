package br.com.projetosetembroamarelo.Model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLite(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object {

        const val DATABASE_NAME = "banco.db"
        const val DATABASE_VERSION = 2

        const val DB_TABLE_FRASES = "frases_tabela"
        const val DB_FIELD_ID = "id"
        const val DB_FIELD_TITULO = "titulo"
        const val DB_FIELD_TEXTO = "texto"
        const val DB_FIELD_AUTOR = "autor"

        const val DB_TABLE_USERS = "usuarios_tabela"
        const val DB_FIELD_NOME = "nome"
        const val DB_FIELD_IDADE = "idade"
        const val DB_FIELD_SEXO = "sexo"
        const val DB_FIELD_EMAIL = "email"
        const val DB_FIELD_SENHA = "senha"
        const val DB_FIELD_FRASE = "frase"
        const val DB_FIELD_USUARIO = "usuario"

        const val sqlCreateFrases = "CREATE TABLE IF NOT EXISTS $DB_TABLE_FRASES(" +
                "$DB_FIELD_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$DB_FIELD_TITULO TEXT, " +
                "$DB_FIELD_TEXTO TEXT, " +
                "$DB_FIELD_AUTOR TEXT," +
                "$DB_FIELD_USUARIO INTEGER);"

        const val sqlCreateUsers = "CREATE TABLE IF NOT EXISTS $DB_TABLE_USERS(" +
                "$DB_FIELD_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$DB_FIELD_NOME TEXT, " +
                "$DB_FIELD_IDADE INTEGER, " +
                "$DB_FIELD_SEXO TEXT, " +
                "$DB_FIELD_EMAIL TEXT, " +
                "$DB_FIELD_SENHA TEXT);"

        const val sqlDropFrases = "DROP TABLE IF EXISTS $DB_TABLE_FRASES"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        val db = db ?: return

        db.beginTransaction()
        try{

            db.execSQL(sqlCreateUsers)
            db.execSQL(sqlCreateFrases)
            db.setTransactionSuccessful()
        }
        finally {

            db.endTransaction()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        val db = db ?: return

        if (oldVersion == 1) {

            if (newVersion == 2) {

                db.beginTransaction()
                try {

                    db.execSQL(sqlCreateFrases)
                    db.setTransactionSuccessful()
                } finally {

                    db.endTransaction()
                }
            }
        }
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val db = db ?: return

        if (oldVersion == 2) {

            if (newVersion == 1) {

                db.beginTransaction()
                try {

                    db.execSQL(sqlDropFrases)
                    db.setTransactionSuccessful()
                }
                finally {

                    db.endTransaction()
                }
            }
        }
        
    }

    fun getTodosUsuarios(): MutableList<Usuario> {

        var usuarios = mutableListOf<Usuario>()
        val db = readableDatabase
        val cursor = db.query(
            DB_TABLE_USERS,
            null,
            null,
            null,
            null,
            null,
            DB_FIELD_NOME)

        with(cursor){

            while(moveToNext()){

                val id = getInt(getColumnIndex(DB_FIELD_ID))
                val nome = getString(getColumnIndex(DB_FIELD_NOME))
                val idade = getInt(getColumnIndex(DB_FIELD_IDADE))
                val sexo = getString(getColumnIndex(DB_FIELD_SEXO))
                val email = getString(getColumnIndex(DB_FIELD_EMAIL))
                val senha = getString(getColumnIndex(DB_FIELD_SENHA))
                val user = Usuario(nome, idade, sexo, email, senha)
                user.id = id.toLong()
                usuarios.add(user)
            }
        }
        return usuarios
    }

    fun addUser(user: Usuario): Long{

        val db = writableDatabase

        val valores = ContentValues().apply{
            put(DB_FIELD_NOME, user.nome)
            put(DB_FIELD_IDADE, user.idade)
            put(DB_FIELD_SEXO, user.sexo)
            put(DB_FIELD_EMAIL, user.email)
            put(DB_FIELD_SENHA, user.senha)
        }

        var id: Long = 0
        db.beginTransaction()
        try{

            id = db.insert(DB_TABLE_USERS, "", valores)
            db.setTransactionSuccessful()
        }finally {
            db.endTransaction()
        }

        db.close()

        return id

    }

    fun editUser(user: Usuario): Int{

        val db = writableDatabase

        val valores = ContentValues().apply{
            put(DB_FIELD_NOME, user.nome)
            put(DB_FIELD_IDADE, user.idade)
            put(DB_FIELD_SEXO, user.sexo)
            put(DB_FIELD_EMAIL, user.email)
            put(DB_FIELD_SENHA, user.senha)
        }

        val selecionarId = "$DB_FIELD_ID = ?"
        val selecionarArgs = arrayOf(user.id.toString())
        var retornaNumero = 0
        db.beginTransaction()
        try{

            retornaNumero = db.update(DB_TABLE_USERS, valores, selecionarId, selecionarArgs)
            db.setTransactionSuccessful()
        }
        finally {

            db.endTransaction()
        }

        db.close()

        return retornaNumero
    }

    fun delUser(user: Usuario): Int{

        val db = writableDatabase

        val sqlDeletarUsuario = "DELETE FROM $DB_TABLE_USERS WHERE $DB_FIELD_FRASE = ${user.id}"
        val selecao = "$DB_FIELD_ID = ?"
        val selecaoArgs = arrayOf(user.id.toString())

        var retornaNumero = 0
        db.beginTransaction()
        try{

            db.execSQL(sqlDeletarUsuario)
            retornaNumero = db.delete(DB_TABLE_USERS, selecao, selecaoArgs)
            db.setTransactionSuccessful()
        }
        finally {

            db.endTransaction()
        }

        db.close()

        return retornaNumero
    }



    ////FRASES
    fun getTodasFrases(): MutableList<Frase>{

        var frases = mutableListOf<Frase>()

        val db = readableDatabase

        val cursor = db.query(
            DB_TABLE_FRASES,
            null,
            null,
            null,
            null,
            null,
            DB_FIELD_TITULO)

        with(cursor){

            while(moveToNext()){

                val id = getInt(getColumnIndex(DB_FIELD_ID))
                val titulo = getString(getColumnIndex(DB_FIELD_TITULO))
                val texto = getString(getColumnIndex(DB_FIELD_TEXTO))
                val autor = getString(getColumnIndex(DB_FIELD_AUTOR))
                val usuario = getInt(getColumnIndex(DB_FIELD_USUARIO))
                val frase = Frase(titulo, texto, autor, usuario)
                frase.id = id.toLong()
                frases.add(frase)

            }
        }

        return frases
    }

    fun addFrase(frase: Frase): Long{

        val db = writableDatabase

        val valores = ContentValues().apply {

            put(DB_FIELD_TITULO, frase.titulo)
            put(DB_FIELD_TEXTO, frase.texto)
            put(DB_FIELD_AUTOR, frase.autor)
            put(DB_FIELD_USUARIO, frase.usuario)
        }

        var id: Long = 0
        db.beginTransaction()
        try{

            id = db.insert(DB_TABLE_FRASES, "", valores)
            db.setTransactionSuccessful()
        }
        finally {

            db.endTransaction()
        }

        db.close()

        return id

    }

    fun editFrase(frase: Frase): Int{

        val db = writableDatabase

        val values = ContentValues().apply{

            put(DB_FIELD_TITULO, frase.titulo)
            put(DB_FIELD_TEXTO, frase.texto)
            put(DB_FIELD_AUTOR, frase.autor)
            put(DB_FIELD_USUARIO, frase.usuario)
        }

        val selection = "$DB_FIELD_ID = ? "
        val selectionArgs = arrayOf(frase.id.toString())
        var count = 0
        db.beginTransaction()
        try{

            //db.execSQL(sqlEditFrase)
            count = db.update(DB_TABLE_FRASES, values, selection, selectionArgs)
            db.setTransactionSuccessful()
        }
        finally {

            db.endTransaction()
        }

        db.close()

        return count
    }

    fun delFrase(frase: Frase): Int{

        val db = writableDatabase

        val sqlDeleteFrase = "DELETE FROM $DB_TABLE_FRASES WHERE $DB_FIELD_USUARIO = ${frase.id}"
        val selecao = "$DB_FIELD_ID = ?"
        val selecaoArgs = arrayOf(frase.id.toString())
        var count = 0

        db.beginTransaction()
        try{

            db.execSQL(sqlDeleteFrase)
            count = db.delete(DB_TABLE_FRASES, selecao, selecaoArgs)
            db.setTransactionSuccessful()
        }
        finally {

            db.endTransaction()
        }

        db.close()

        return count
    }

    fun pegarFrasesUsuarioId(userId: Int): MutableList<Frase> {

        var frases = mutableListOf<Frase>()

        val db = readableDatabase

        val cursor = db.query(
            DB_TABLE_FRASES,
            null,
            "$DB_FIELD_USUARIO = ?",
            arrayOf(userId.toString()),
            null,
            null,
            DB_FIELD_TITULO
        )

        with(cursor) {

            while (moveToNext()) {

                val id = getInt(getColumnIndex(DB_FIELD_ID))
                val titulo = getString(getColumnIndex(DB_FIELD_TITULO))
                val texto = getString(getColumnIndex(DB_FIELD_TEXTO))
                val autor = getString(getColumnIndex(DB_FIELD_AUTOR))
                val frase = Frase(titulo, texto, autor, userId)
                frase.id = id.toLong()
                frases.add(frase)
            }
        }

        return frases
    }

}