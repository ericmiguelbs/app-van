import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import com.example.myapplication2.ui.alunos.Aluno

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, Companion.DATABASE_NAME, null, Companion.DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "alunos.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_ALUNOS = "alunos"
        private const val COL_ID = "id"
        private const val COL_NOME = "nome"
        private const val COL_IDADE = "idade"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_ALUNOS (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_NOME TEXT, " +
                "$COL_IDADE INTEGER)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ALUNOS")
        onCreate(db)
    }
    fun adicionarAluno(nome: String, idade: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NOME, nome)
            put(COL_IDADE, idade)
        }
        db.insert(TABLE_ALUNOS, null, values)
        db.close()
    }

    fun listarAlunos(): List<Aluno> {
        val alunos = mutableListOf<Aluno>()
        val db = readableDatabase

        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_ALUNOS", null)

        if (cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndexOrThrow(COL_ID)
            val nomeIndex = cursor.getColumnIndexOrThrow(COL_NOME)
            val idadeIndex = cursor.getColumnIndexOrThrow(COL_IDADE)

            do {
                val id = cursor.getInt(idIndex)
                val nome = cursor.getString(nomeIndex)
                val idade = cursor.getInt(idadeIndex)

                alunos.add(Aluno(id, nome, idade))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return alunos
    }
}