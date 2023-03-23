package shpp.myapplication.colivery.data.network

data class SpaceModel(
    val id: String,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val adminUserId: String,
    val icon: String? = null,
    val usersList: List<String> = emptyList()
)
