import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication2.R

class AlunosAdapter(private var listaAlunos: List<Aluno>) :
    RecyclerView.Adapter<AlunosAdapter.AlunoViewHolder>() {

    class AlunoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textId: TextView = view.findViewById(R.id.text_id)
        val textNome: TextView = view.findViewById(R.id.text_nome)
        val textIdade: TextView = view.findViewById(R.id.text_idade)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlunoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_aluno, parent, false)
        return AlunoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlunoViewHolder, position: Int) {
        val aluno = listaAlunos[position]

        holder.textId.text = "${position + 1}."
        holder.textNome.text = aluno.nome
        holder.textIdade.text = "Idade: ${aluno.idade} anos"
    }

    override fun getItemCount(): Int = listaAlunos.size
    fun setAlunos(novaLista: List<Aluno>) {
        listaAlunos = novaLista
        notifyDataSetChanged()
    }
}