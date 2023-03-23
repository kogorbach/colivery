package shpp.myapplication.colivery.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import shpp.myapplication.colivery.data.network.SpaceModel

class SpacesViewModel(
//    private val spaceRepository: SpaceRepository
) : ViewModel() {

    private val _spaces = MutableStateFlow<List<SpaceModel>>(emptyList())
    val spaces: StateFlow<List<SpaceModel>> = _spaces

    init {
//        viewModelScope.launch {
//            loadSpaces()
//        }
    }
}
