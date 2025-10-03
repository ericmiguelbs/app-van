package com.example.myapplication2.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication2.ui.escola.Escola
import com.example.myapplication2.ui.alunos.Aluno
import com.example.myapplication2.ui.equipe.Equipe
import java.lang.Exception

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 4
        private const val DATABASE_NAME = "MeuProjetoDB"
        const val TABLE_ESCOLAS = "escolas"
        const val COLUMN_ESCOLA_ID = "id"
        const val COLUMN_ESCOLA_NOME = "nome"
        const val COLUMN_ESCOLA_ENDERECO = "endereco"
        const val COLUMN_FK_ESCOLA_ID = "escola_id"
        const val TABLE_ALUNOS = "alunos"
        const val COLUMN_ALUNO_ID = "id"
        const val COLUMN_ALUNO_NOME = "nome"
        const val COLUMN_ALUNO_IDADE = "idade"
        const val TABLE_EQUIPES = "equipes"
        const val COLUMN_EQUIPE_ID = "id"
        const val COLUMN_EQUIPE_NOME = "nome"
        const val COLUMN_EQUIPE_DESCRICAO = "descricao"
    }

    override fun onCreate(db: SQLiteDatabase) {

        // 1. Criação da tabela ESCOLAS
        val CREATE_TABLE_ESCOLAS = "CREATE TABLE $TABLE_ESCOLAS (" +
                "$COLUMN_ESCOLA_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_ESCOLA_NOME TEXT NOT NULL," +
                "$COLUMN_ESCOLA_ENDERECO TEXT)"
        db.execSQL(CREATE_TABLE_ESCOLAS)

        // 2. Criação da tabela ALUNOS (com FK para Escola)
        val CREATE_TABLE_ALUNOS = "CREATE TABLE $TABLE_ALUNOS (" +
                "$COLUMN_ALUNO_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_ALUNO_NOME TEXT NOT NULL," +
                "$COLUMN_ALUNO_IDADE INTEGER," +
                "$COLUMN_FK_ESCOLA_ID INTEGER," +
                "FOREIGN KEY($COLUMN_FK_ESCOLA_ID) REFERENCES $TABLE_ESCOLAS($COLUMN_ESCOLA_ID) ON DELETE CASCADE)"
        db.execSQL(CREATE_TABLE_ALUNOS)

        // 3. Criação da tabela EQUIPES (com FK para Escola)
        val CREATE_TABLE_EQUIPES = "CREATE TABLE $TABLE_EQUIPES (" +
                "$COLUMN_EQUIPE_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_EQUIPE_NOME TEXT NOT NULL," +
                "$COLUMN_EQUIPE_DESCRICAO TEXT," +
                "$COLUMN_FK_ESCOLA_ID INTEGER," +
                "FOREIGN KEY($COLUMN_FK_ESCOLA_ID) REFERENCES $TABLE_ESCOLAS($COLUMN_ESCOLA_ID) ON DELETE CASCADE)"
        db.execSQL(CREATE_TABLE_EQUIPES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EQUIPES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ALUNOS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ESCOLAS")
        onCreate(db)
    }

    fun insertEscola(escola: Escola): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_ESCOLA_NOME, escola.nome)
            put(COLUMN_ESCOLA_ENDERECO, escola.endereco)
        }
        val result = db.insert(TABLE_ESCOLAS, null, contentValues)
        db.close()
        return result
    }

    fun getEscolas(): List<Escola> {
        val escolaList = mutableListOf<Escola>()
        val selectQuery = "SELECT * FROM $TABLE_ESCOLAS"
        val db = this.readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.close()
            return emptyList()
        }

        cursor?.let {
            if (it.moveToFirst()) {
                val idIndex = it.getColumnIndex(COLUMN_ESCOLA_ID)
                val nomeIndex = it.getColumnIndex(COLUMN_ESCOLA_NOME)
                val endIndex = it.getColumnIndex(COLUMN_ESCOLA_ENDERECO)

                do {
                    val id = if (idIndex >= 0) it.getInt(idIndex) else 0
                    val nome = if (nomeIndex >= 0) it.getString(nomeIndex) else ""
                    val endereco = if (endIndex >= 0) it.getString(endIndex) else null

                    val escola = Escola(id = id, nome = nome, endereco = endereco)
                    escolaList.add(escola)
                } while (it.moveToNext())
            }
            it.close()
        }
        db.close()
        return escolaList
    }

    fun insertEquipe(equipe: Equipe): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_EQUIPE_NOME, equipe.nome)
            put(COLUMN_EQUIPE_DESCRICAO, equipe.descricao)
            put(COLUMN_FK_ESCOLA_ID, equipe.escolaId) // Salva a FK
        }
        val result = db.insert(TABLE_EQUIPES, null, contentValues)
        db.close()
        return result
    }

    fun getEquipes(escolaId: Int? = null): List<Equipe> {
        val equipeList = mutableListOf<Equipe>()
        var selectQuery = "SELECT * FROM $TABLE_EQUIPES"
        val db = this.readableDatabase
        val whereArgs = mutableListOf<String>()

        if (escolaId != null && escolaId > 0) {
            selectQuery += " WHERE $COLUMN_FK_ESCOLA_ID = ?"
            whereArgs.add(escolaId.toString())
        }

        val cursor: Cursor? = try {
            db.rawQuery(selectQuery, whereArgs.toTypedArray())
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }

        try {
            cursor?.let {
                if (it.moveToFirst()) {
                    val idIndex = it.getColumnIndex(COLUMN_EQUIPE_ID)
                    val nomeIndex = it.getColumnIndex(COLUMN_EQUIPE_NOME)
                    val descIndex = it.getColumnIndex(COLUMN_EQUIPE_DESCRICAO)
                    val escolaIdFkIndex = it.getColumnIndex(COLUMN_FK_ESCOLA_ID)

                    do {
                        val id = if (idIndex >= 0) it.getInt(idIndex) else 0
                        val nome = if (nomeIndex >= 0) it.getString(nomeIndex) ?: "" else ""
                        val descricao = if (descIndex >= 0) it.getString(descIndex) else null
                        val escolaIdFk = if (escolaIdFkIndex >= 0) it.getInt(escolaIdFkIndex) else 0

                        val equipe = Equipe(id = id, nome = nome, descricao = descricao, escolaId = escolaIdFk)
                        equipeList.add(equipe)
                    } while (it.moveToNext())
                }
            }
        } finally {
            cursor?.close()
            db.close()
        }
        return equipeList
    }

    fun adicionarAluno(aluno: Aluno): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_ALUNO_NOME, aluno.nome)
            put(COLUMN_ALUNO_IDADE, aluno.idade)
            put(COLUMN_FK_ESCOLA_ID, aluno.escolaId) // Salva a FK
        }
        val result = db.insert(TABLE_ALUNOS, null, contentValues)
        db.close()
        return result
    }

    fun listarAlunos(escolaId: Int? = null): List<Aluno> {
        val alunoList = mutableListOf<Aluno>()
        var selectQuery = "SELECT * FROM $TABLE_ALUNOS"
        val db = this.readableDatabase
        val whereArgs = mutableListOf<String>()

        if (escolaId != null && escolaId > 0) {
            selectQuery += " WHERE $COLUMN_FK_ESCOLA_ID = ?"
            whereArgs.add(escolaId.toString())
        }

        val cursor: Cursor? = try {
            db.rawQuery(selectQuery, whereArgs.toTypedArray())
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }

        try {
            cursor?.let {
                if (it.moveToFirst()) {
                    val idIndex = it.getColumnIndex(COLUMN_ALUNO_ID)
                    val nomeIndex = it.getColumnIndex(COLUMN_ALUNO_NOME)
                    val idadeIndex = it.getColumnIndex(COLUMN_ALUNO_IDADE)
                    val escolaIdFkIndex = it.getColumnIndex(COLUMN_FK_ESCOLA_ID)

                    do {
                        val id = if (idIndex >= 0) it.getInt(idIndex) else 0
                        val nome = if (nomeIndex >= 0) it.getString(nomeIndex) ?: "" else ""
                        val idade = if (idadeIndex >= 0) it.getInt(idadeIndex) else 0
                        val escolaIdFk = if (escolaIdFkIndex >= 0) it.getInt(escolaIdFkIndex) else 0

                        val aluno = Aluno(id = id, nome = nome, idade = idade, escolaId = escolaIdFk)
                        alunoList.add(aluno)
                    } while (it.moveToNext())
                }
            }
        } finally {
            cursor?.close()
            db.close()
        }
        return alunoList
    }
}