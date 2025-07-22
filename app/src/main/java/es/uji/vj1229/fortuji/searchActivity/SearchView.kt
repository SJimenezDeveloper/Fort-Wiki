package es.uji.vj1229.fortuji.searchActivity

import es.uji.vj1229.fortuji.common.Cosmetic

interface SearchView {

    fun showCosmetics(cosmetics: ArrayList<Cosmetic>)
    fun showError(s: String)
    fun showCurrentCosmeticDetails()
    fun startImagesActivity(name: String, images: ArrayList<String>)
}