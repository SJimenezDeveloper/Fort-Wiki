package es.uji.vj1229.fortuji.searchActivity


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.uji.vj1229.fortuji.common.Cosmetic
import es.uji.vj1229.fortuji.network.FortniteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SearchViewModel : ViewModel() {
    enum class SearchType(val label: String) {
        NAME("Name"), DESCRIPTION("Description"), ID("ID")
    }

    var view: SearchView? = null
        set(value) {
            field = value
        }
    val cosmetics = ArrayList<Cosmetic>()
    var selectedCosmetic: Cosmetic? = null

    fun onSearchRequested(type: SearchType, query: String) {
        // Agregar un toast aquí para saber que la búsqueda se ha solicitado
        view?.showError("Buscando: $query")
        viewModelScope.launch(Dispatchers.Main) {
            FortniteRepository.searchCosmetic(type, query, query, query)
                .onSuccess{
                    cosmetics.clear()
                    cosmetics.addAll(it)
                    view?.showCosmetics(cosmetics)
                }
                .onFailure{
                    view?.showError("Error searching...")
                }
        }
    }


    fun onCosmeticSelected(cosmetic: Cosmetic) {
        selectedCosmetic = cosmetic
        view?.showCurrentCosmeticDetails()
    }

    fun onShowImagesRequested() = selectedCosmetic?.let {
        val images = ArrayList<String>()
        for (entry in it.iconImage.entries) {
            images.add(entry.key)
            images.add(entry.value)
        }
        // Log para asegurar que las imágenes se están agregando correctamente
        Log.d("SearchViewModel", "Adding ${images.size / 2} image pairs for cosmetic ${it.name}")
        images.forEachIndexed { index, item ->
            Log.d("SearchViewModel", "Image[$index]: $item")
        }

        // Se llama al metodo de la vista para iniciar la Images Activity, pasando el nombre y las imágenes
        view?.startImagesActivity(it.name, images)
    }

}