package es.uji.vj1229.fortuji.imagesActivity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import es.uji.vj1229.fortuji.databinding.ImageActivityBinding

class ImagesActivity : AppCompatActivity()  {

    companion object {
        const val NAME = "NAME"
        const val IMAGES = "IMAGES"
    }

    private lateinit var binding: ImageActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ImageActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { i, insets ->
            val sBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            i.setPadding(sBars.left, sBars.top, sBars.bottom, sBars.right)
            insets
        }
        val name = intent.getStringExtra(NAME)
        val imageList = intent.getStringArrayListExtra(IMAGES) ?: arrayListOf()

        title = name
        val imgPairs = ArrayList<Pair<String, String>>()
        imageList.chunked(2).forEach { chunk ->
            if (chunk.size == 2) {
                val (type, url) = chunk
                if (!type.isNullOrEmpty() && !url.isNullOrEmpty()) {
                    imgPairs.add(type to url)
                }
            }
        }
        // Si no se han agregado imágenes válidas, loguea el error.
        if (imgPairs.isEmpty()) {
            Log.d("ImagesActivity", "No valid images to display!")
        }
        // Montamos el RecyclerView
        binding.imagesRecyclerView.layoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        binding.imagesRecyclerView.adapter = ImagesAdapter(imgPairs, this)

    }

}