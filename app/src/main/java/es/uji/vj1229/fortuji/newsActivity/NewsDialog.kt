package es.uji.vj1229.fortuji.newsActivity

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import es.uji.vj1229.fortuji.databinding.DialogItemsBinding
import es.uji.vj1229.fortuji.databinding.NewsItemBinding

class NewsDialog : DialogFragment() {

    private val viewModel: NewsViewModel by activityViewModels()
    private lateinit var binding: DialogItemsBinding

    // Se obtiene el mismo NewsViewModel que la Activity


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogItemsBinding.inflate(layoutInflater, null , false)

        // Se carga la noticia seleccionada en el diálogo
        viewModel.selectedNews?.let { selected ->
            with(binding){
                newsTitleTextView.text = selected.title
                newsBodyTextView.text = selected.body
            }

            // Se puede cargar la imagen del ítem; en este ejemplo se utiliza tileImage
            Glide.with(requireContext())
                .load(selected.image)
                .fitCenter()
                .into(binding.newsDialogImageView)
        }

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setPositiveButton(android.R.string.ok, null)
            .create()
    }


}