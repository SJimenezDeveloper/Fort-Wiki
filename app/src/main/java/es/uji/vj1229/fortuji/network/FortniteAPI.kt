package es.uji.vj1229.fortuji.network

import es.uji.vj1229.fortuji.newsActivity.NewsResponse
import es.uji.vj1229.fortuji.searchActivity.CosmeticSearchResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface FortniteAPI {

    @Headers("Accept: application/json")
    @GET("news/br")
    suspend fun getNews(): NewsResponse

    @Headers("Accept: application/json")
    @GET("cosmetics/br/search/all")
    suspend fun searchCosmeticsByName(
        @Query("name") name: String,  //Tipo de búsqueda: Name, Description, ID
        @Query("matchMethod") matchMethod: String //Contenido de búsqueda: el nombre o id que estás buscando
    ): CosmeticSearchResponse

    @Headers("Accept: application/json")
    @GET("cosmetics/br/search/all")
    suspend fun searchCosmeticsByDescription(
        @Query("description") description: String,  //Tipo de búsqueda: Name, Description, ID
        @Query("matchMethod") matchMethod: String //Contenido de búsqueda: el nombre o id que estás buscando
    ): CosmeticSearchResponse

    @Headers("Accept: application/json")
    @GET("cosmetics/br/search/all")
    suspend fun searchCosmeticsById(
        @Query("id") id: String,  //Tipo de búsqueda: Name, Description, ID
        @Query("matchMethod") matchMethod: String //Contenido de búsqueda: el nombre o id que estás buscando
    ): CosmeticSearchResponse

}