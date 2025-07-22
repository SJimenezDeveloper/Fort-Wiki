package es.uji.vj1229.fortuji.searchActivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.uji.vj1229.fortuji.common.Cosmetic
import es.uji.vj1229.fortuji.databinding.ItemCosmeticBinding



class CosmeticsAdapter(
    private val cosmetics: ArrayList<Cosmetic>,
    private val onClickListener: (Cosmetic) -> Unit) :
    RecyclerView.Adapter<CosmeticsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCosmeticBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCosmeticBinding.inflate(
            inflater, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        // Verifica que esté entrando al metodo
        //Toast.makeText(holder.itemView.context, "Vinculando cosmético en posición $position", Toast.LENGTH_SHORT).show()
        cosmetics[position].let { entry ->
            with(holder.binding) {
                binding.nameTextView.text = entry.name
                binding.descriptionTextView.text = entry.description
                root.setOnClickListener {
                    onClickListener(cosmetics[position])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return cosmetics.count()
    }
}