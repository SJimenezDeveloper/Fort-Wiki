package es.uji.vj1229.fortuji.searchActivity

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import es.uji.vj1229.fortuji.databinding.SearchDialogBinding

class CosmeticsDialog: DialogFragment() {
    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var binding: SearchDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = SearchDialogBinding.inflate(layoutInflater, null, false)

        viewModel.selectedCosmetic?.let { selected ->
            with(binding){
                idTextView.text = selected.id
                nameTextView.text = selected.name
                typeTextView.text = selected.type
                rarityTextView.text = selected.rarity
                addedTextView.text = selected.added
                descriptionTextView.text = selected.description

                // Si no hay iconImage, deshabilitamos el botón de imágenes;
                // de lo contrario, cargamos la imagen usando Glide
                val icon: String? = selected.iconImage["icon"]
                if (selected.iconImage.isEmpty()) {
                    imagesButton.isEnabled = false
                } else {
                    imagesButton.isEnabled = true
                    Glide.with(requireContext())
                        .load(icon)
                        .fitCenter()
                        .into(imageImageView)
                }
                videoButton.visibility = View.GONE
            }
        } ?: run {
            // Si no hay cosmetic seleccionado, se cierra el diálogo.
            dismiss()
        }
        binding.imagesButton.setOnClickListener{
            viewModel.selectedCosmetic?.let {
                viewModel.onShowImagesRequested()
            }
        }
        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setPositiveButton(android.R.string.ok, null)
            .create()
    }

}