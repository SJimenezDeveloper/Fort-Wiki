package es.uji.vj1229.fortuji.network

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import es.uji.vj1229.fortuji.common.News
import es.uji.vj1229.fortuji.common.Cosmetic
import es.uji.vj1229.fortuji.searchActivity.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.Result
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class FortniteRepository {
    companion object {
        // Inicialización de Retrofit con Moshi
        private val api: FortniteAPI

        init {
            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
            api = Retrofit.Builder()
                .baseUrl("https://fortnite-api.com/v2/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(FortniteAPI::class.java)
        }

        // Metodo que obtiene las noticias y las transforma en un Pair (imagen, lista de News)
        suspend fun getNews() = try {
            withContext(Dispatchers.IO) {
                val newsResponse = api.getNews()
                val news = ArrayList<News>()
                for (motd in newsResponse.data.motds) {
                    // Se traduce cada MotdResponse a News
                    news.add(
                        News(
                            title = motd.title,
                            tabTitle = motd.tabTitle,
                            image = motd.tileImage,
                            body = motd.body
                        )
                    )
                }
                // Devuelve un Result con éxito que contiene un Pair (URL de imagen, lista de noticias)
                Result.success(Pair(newsResponse.data.image, news))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

        suspend fun searchCosmetic(
            type: SearchViewModel.SearchType,
            name: String,
            description: String,
            id: String
        )= try {
            withContext(Dispatchers.IO) {
                val searchResponse = when (type) {
                    SearchViewModel.SearchType.NAME -> api.searchCosmeticsByName(name, "contains")
                    SearchViewModel.SearchType.DESCRIPTION -> api.searchCosmeticsByDescription(description, "contains")
                    SearchViewModel.SearchType.ID -> api.searchCosmeticsById(id, "contains")
                }
                //Log.d("FortniteRepository", "Response: ${searchResponse.data}")
                val search = ArrayList<Cosmetic>()
                // Función local para construir HashMap sin valores nulos
                fun <K, V> hashMapNotNull(vararg pairs: Pair<K, V?>): HashMap<K, V> =
                    HashMap(pairs.mapNotNull { pair ->
                        pair.second?.let { value ->
                            pair.first to value
                        }
                    }.toMap())
                for (c in searchResponse.data) {
                    val imagesMap = hashMapNotNull(
                        "Bean large" to c.images.bean?.large?.takeIf { it != "null" },
                        "Bean small" to c.images.bean?.small?.takeIf { it != "null" },
                        "Bean wide" to c.images.bean?.wide?.takeIf { it != "null" },
                        "Featured" to c.images.featured?.takeIf { it != "null" },
                        "Icon" to c.images.icon?.takeIf { it != "null" },
                        "Lego large" to c.images.lego?.large?.takeIf { it != "null" },
                        "Lego small" to c.images.lego?.small?.takeIf { it != "null" },
                        "Lego wide" to c.images.lego?.wide?.takeIf { it != "null" },
                        "Background" to c.images.other?.background?.takeIf { it != "null" },
                        "Coverart" to c.images.other?.coverart?.takeIf { it != "null" },
                        "Decal" to c.images.other?.decal?.takeIf { it != "null" },
                        "Small icon" to c.images.smallIcon?.takeIf { it != "null" }
                    )

                    search.add(
                        Cosmetic(
                            id = c.id,
                            name = c.name,
                            description = c.description,
                            type = if (c.type.displayValue == "null") c.type.value else c.type.displayValue,
                            rarity = if (c.rarity.displayValue == "null") c.rarity.value else c.rarity.displayValue,
                            iconImage = imagesMap,
                            featuredImage = c.images.featured ?: "",
                            added = c.added
                        )
                    )
                }
                Log.d("FortniteRepository", "Encontrados ${search.size} cosméticos")
                Result.success(search)
            }
        } catch (e: Exception) {
            Log.e("FortniteRepository", "Error al buscar cosméticos: ${e.message}")
            e.printStackTrace()
            Result.failure(e)

        }
    }
}