package es.uji.vj1229.fortuji.imagesActivity

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import es.uji.vj1229.fortuji.databinding.ItemImageBinding

class ImagesAdapter(val images: List<Pair<String, String>>, val context: Context): RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (type, url) = images[position]
        with(holder.binding) {
            imageNameTextView.text = type
            Glide.with(context)
                .load(url)
                .centerCrop()
                .into(cosmeticsImageView)
        }
    }

    override fun getItemCount(): Int = images.count()
}