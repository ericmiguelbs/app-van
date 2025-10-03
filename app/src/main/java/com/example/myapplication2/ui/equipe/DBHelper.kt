package com.example.myapplication2.data

import com.example.myapplication2.ui.equipe.Equipe
import com.example.myapplication2.ui.alunos.Aluno

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "MeuProjetoDB"
        const val TABLE_EQUIPES = "equipes"
        const val COLUMN_EQUIPE_ID = "id"
        const val COLUMN_EQUIPE_NOME = "nome"
        const val COLUMN_EQUIPE_DESCRICAO = "descricao"
        const val TABLE_ALUNOS = "alunos"
        const val COLUMN_ALUNO_ID = "id"
        const val COLUMN_ALUNO_NOME = "nome"
        const val COLUMN_ALUNO_IDADE = "idade"
    }

    override fun onCreate(db: SQLiteDatabase) {

        val CREATE_TABLE_ALUNOS = "CREATE TABLE $TABLE_ALUNOS (" +
                "$COLUMN_ALUNO_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_ALUNO_NOME TEXT NOT NULL," +
                "$COLUMN_ALUNO_IDADE INTEGER)"

        db.execSQL(CREATE_TABLE_ALUNOS)

        val CREATE_TABLE_EQUIPES = "CREATE TABLE $TABLE_EQUIPES (" +
                "$COLUMN_EQUIPE_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_EQUIPE_NOME TEXT NOT NULL," +
                "$COLUMN_EQUIPE_DESCRICAO TEXT)"

        db.execSQL(CREATE_TABLE_EQUIPES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Remove as tabelas antigas antes de recriá-las
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EQUIPES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ALUNOS")
        onCreate(db)
    }
    fun insertEquipe(equipe: Equipe): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_EQUIPE_NOME, equipe.nome)
            put(COLUMN_EQUIPE_DESCRICAO, equipe.descricao)
        }
        val result = db.insert(TABLE_EQUIPES, null, contentValues)
        db.close()
        return result
    }

    fun getEquipes(): List<Equipe> {
        val equipeList = mutableListOf<Equipe>()
        val selectQuery = "SELECT * FROM $TABLE_EQUIPES"
        val db = this.readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.close()
            return emptyList()
        }

        if (cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex(COLUMN_EQUIPE_ID)
            val nomeIndex = cursor.getColumnIndex(COLUMN_EQUIPE_NOME)
            val descIndex = cursor.getColumnIndex(COLUMN_EQUIPE_DESCRICAO)

            do {
                val id = if (idIndex >= 0) cursor.getInt(idIndex) else 0
                val nome = if (nomeIndex >= 0) cursor.getString(nomeIndex) else ""
                val descricao = if (descIndex >= 0) cursor.getString(descIndex) else null

                val equipe = Equipe(id = id, nome = nome, descricao = descricao)
                equipeList.add(equipe)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return equipeList
    }

    fun adicionarAluno(nome: String, idade: Int): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_ALUNO_NOME, nome)
            put(COLUMN_ALUNO_IDADE, idade)
        }
        val result = db.insert(TABLE_ALUNOS, null, contentValues)
        db.close()
        return result
    }

    fun listarAlunos(): List<Aluno> {
        val alunoList = mutableListOf<Aluno>()
        val selectQuery = "SELECT * FROM $TABLE_ALUNOS"
        val db = this.readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.close()
            return emptyList()
        }

        if (cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex(COLUMN_ALUNO_ID)
            val nomeIndex = cursor.getColumnIndex(COLUMN_ALUNO_NOME)
            val idadeIndex = cursor.getColumnIndex(COLUMN_ALUNO_IDADE)

            do {
                val id = if (idIndex >= 0) cursor.getInt(idIndex) else 0
                val nome = if (nomeIndex >= 0) cursor.getString(nomeIndex) else ""
                val idade = if (idadeIndex >= 0) cursor.getInt(idadeIndex) else 0

                val aluno = Aluno(id = id, nome = nome, idade = idade)
                alunoList.add(aluno)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return alunoList
    }

}