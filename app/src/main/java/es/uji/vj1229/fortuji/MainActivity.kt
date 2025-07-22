package es.uji.vj1229.fortuji

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import es.uji.vj1229.fortuji.databinding.ActivityMainBinding
import es.uji.vj1229.fortuji.newsActivity.NewsActivity
import es.uji.vj1229.fortuji.searchActivity.SearchActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        with(binding){
            buttonNews.setOnClickListener{
                val intentN = Intent(this@MainActivity, NewsActivity::class.java)
                startActivity(intentN)

            }
            buttonCosmetic.setOnClickListener{
                val intentC = Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(intentC)
            }
        }




    }

}