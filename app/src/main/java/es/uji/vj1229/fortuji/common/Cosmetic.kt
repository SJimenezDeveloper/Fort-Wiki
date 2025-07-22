package es.uji.vj1229.fortuji.common

data class Cosmetic (
    val id: String,
    val name: String,
    val description: String,
    val type: String, // Solo una cadena que representa el tipo
    val rarity: String, // Solo una cadena que representa la rareza
    val iconImage: HashMap<String, String>, // URL de la imagen del icono
    val featuredImage: String, // URL de la imagen destacada (puede ser null)
    val added: String
)


