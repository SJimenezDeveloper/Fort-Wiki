package es.uji.vj1229.fortuji.newsActivity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import es.uji.vj1229.fortuji.R
import es.uji.vj1229.fortuji.common.News
import es.uji.vj1229.fortuji.databinding.ActivityNewsBinding

class NewsActivity() : AppCompatActivity(), NewsView {

    private lateinit var binding: ActivityNewsBinding

    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }




    override fun showImage(imageURL: String?) {
        Glide.with(this)
            .load(imageURL)
            .fitCenter()
            .into(binding.newsImageView)
    }

    override fun showNews(news:ArrayList<News>) {
        binding.newsRecyclerView.run {
            adapter = NewsAdapter(news){
                viewModel.onNewsSelected(it)
            }
            layoutManager = LinearLayoutManager(this@NewsActivity)
        }
    }

    override fun showError(s: String) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show()
    }

    override fun showCurrentNewsDetails() {
        NewsDialog().show(supportFragmentManager, "NewsDialog")
    }

    override fun onResume() {
        super.onResume()
        viewModel.view = this
    }
    override fun onPause() {
        super.onPause()
        viewModel.view = null
    }
}