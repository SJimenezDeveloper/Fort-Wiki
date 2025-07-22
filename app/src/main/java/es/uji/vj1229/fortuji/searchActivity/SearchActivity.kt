package es.uji.vj1229.fortuji.searchActivity

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import es.uji.vj1229.fortuji.common.Cosmetic
import es.uji.vj1229.fortuji.databinding.SearchActivityBinding
import es.uji.vj1229.fortuji.imagesActivity.ImagesActivity


class SearchActivity : AppCompatActivity(), SearchView {
    private lateinit var binding: SearchActivityBinding
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.view = this

        binding = SearchActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //ArrayAdapter para el spinner
        val options = SearchViewModel.SearchType.entries.toTypedArray()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.searchTypeSpinner.adapter = adapter

        //Boton de Búsqueda
        binding.searchButton.setOnClickListener {
            viewModel.onSearchRequested(
                binding.searchTypeSpinner.selectedItem as SearchViewModel.SearchType,
                binding.searchContentEditText.text.toString()
            )


        }
        binding.searchCosmeticsRecycler.layoutManager = LinearLayoutManager(this)
    }

    override fun showCosmetics(cosmetics: ArrayList<Cosmetic>) {
        Toast.makeText(this, "Mostrando ${cosmetics.size} cosméticos", Toast.LENGTH_SHORT).show()

        binding.searchCosmeticsRecycler.adapter = CosmeticsAdapter(cosmetics){
            viewModel.onCosmeticSelected(it)
        }
        //binding.searchCosmeticsRecycler.adapter = CosmeticsAdapter(cosmetics){
            //viewModel.onCosmeticSelected(it)
        //}
    }

    override fun showError(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.view = this
    }

    override fun onPause() {
        super.onPause()
        viewModel.view = null
    }

    override fun showCurrentCosmeticDetails() {
        CosmeticsDialog().show(supportFragmentManager, "cosmeticsDialog")
    }

    override fun startImagesActivity(name: String, images: ArrayList<String>){
       val intent = Intent(this, ImagesActivity::class.java).apply {
            putExtra(ImagesActivity.NAME, name)
            putExtra(ImagesActivity.IMAGES, images)
       }
        startActivity(intent)
    }
}