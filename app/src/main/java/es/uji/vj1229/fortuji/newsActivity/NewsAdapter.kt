package es.uji.vj1229.fortuji.newsActivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.uji.vj1229.fortuji.common.News
import es.uji.vj1229.fortuji.databinding.NewsItemBinding
import kotlin.enums.enumEntries

class NewsAdapter(
    private val news: List<News>,
    private val onClickListener: (News) -> Unit) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(val binding: NewsItemBinding ):
        RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NewsItemBinding.inflate(
            inflater, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        news[position].let { entry ->
            with(holder.binding) {
                binding.textView.text = entry.title
                root.setOnClickListener {
                    onClickListener(entry)
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return news.size
    }



}