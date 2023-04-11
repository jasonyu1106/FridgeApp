package com.flarefridges.fridgeapp.drawer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flarefridges.fridgeapp.R
import com.flarefridges.fridgeapp.homepage.DrawerCardData
import com.flarefridges.fridgeapp.model.domain.DrawerData
import com.flarefridges.fridgeapp.model.domain.ItemData
import com.flarefridges.fridgeapp.repository.FridgeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawerDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fridgeRepository: FridgeRepository
) : ViewModel() {
    private val _drawerDetailState = MutableStateFlow(DrawerDetailViewState())
    val drawerDetailViewState = _drawerDetailState.asStateFlow()

    private val itemIconMap = mapOf(
        "apple" to R.drawable.apple_icon_24,
        "banana" to R.drawable.banana_icon_24,
        "orange" to R.drawable.orange_icon_24,
        "carrot" to R.drawable.carrot_icon_24,
        "broccoli" to R.drawable.broccoli_icon_24,
    )

    init {
        viewModelScope.launch {
            _drawerDetailState.value = _drawerDetailState.value.copy(isLoading = true)
//            delay(2000)
//            _drawerDetailState.value = _drawerDetailState.value.copy(
//                itemCount = 18,
//                data = listOf(
//                    DrawerDetailCardData(
//                        name = "Apple",
//                        freshQuantity = 2,
//                        rottenQuantity = 1,
//                        iconRes = R.drawable.apple_icon_24
//                    ),
//                    DrawerDetailCardData(
//                        name = "Banana",
//                        freshQuantity = 1,
//                        rottenQuantity = 3,
//                        iconRes = R.drawable.banana_icon_24
//                    ),
//                    DrawerDetailCardData(
//                        name = "Orange",
//                        freshQuantity = 4,
//                        rottenQuantity = 2,
//                        iconRes = R.drawable.orange_icon_24
//                    ),
//                    DrawerDetailCardData(
//                        name = "Carrot",
//                        freshQuantity = 3,
//                        rottenQuantity = 0,
//                        iconRes = R.drawable.carrot_icon_24
//                    ),
//                    DrawerDetailCardData(
//                        name = "Broccoli",
//                        freshQuantity = 2,
//                        rottenQuantity = 0,
//                        iconRes = R.drawable.broccoli_icon_24
//                    )
//                ),
//                isLoading = false
//            )
            fridgeRepository.fetchDrawerList()?.let { drawerData ->
                _drawerDetailState.value = _drawerDetailState.value.copy(
                    itemCount = drawerData.itemCount,
                    data = drawerData.items.map {
                        DrawerDetailCardData(
                            name = it.name.replaceFirstChar { c -> c.uppercase() },
                            freshQuantity = it.freshQuantity,
                            rottenQuantity = it.rottenQuantity,
                            iconRes = itemIconMap[it.name.lowercase()]
                        )
                    },
                    isLoading = false
                )
            }
        }
    }

}

data class DrawerDetailViewState(
    val isLoading: Boolean = false,
    val itemCount: Int = 0,
    val data: List<DrawerDetailCardData> = listOf()
)