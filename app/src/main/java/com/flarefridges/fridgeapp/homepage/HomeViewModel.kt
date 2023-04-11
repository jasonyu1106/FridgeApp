package com.flarefridges.fridgeapp.homepage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.createBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flarefridges.fridgeapp.R
import com.flarefridges.fridgeapp.homepage.DrawerCardData.LockStatus
import com.flarefridges.fridgeapp.model.domain.DrawerData
import com.flarefridges.fridgeapp.repository.FridgeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fridgeRepository: FridgeRepository
) : ViewModel() {
    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    private val _homeNavigationEvent = MutableSharedFlow<HomeNavigationEvent>()
    val homeNavigationEvent = _homeNavigationEvent.asSharedFlow()

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            fridgeRepository.fetchStatus()?.let { fridgeStatus ->
                _homeState.value = _homeState.value.copy(
                    temperature = fridgeStatus.temperature,
                    drawerData = fridgeStatus.drawerData.map {
                        DrawerCardData(
                            id = it.id,
                            name = it.name,
                            lockStatus = if (it.isUnlocked) {
                                LockStatus.UNLOCKED
                            } else {
                                LockStatus.LOCKED
                            },
                            isCameraSupported = it.isCameraSupported
                        )
                    }
                )
            } ?: run {
                _toastEvent.emit("Something went wrong.")
            }
            _homeState.value = _homeState.value.copy(isRefreshing = false)
        }
    }

    fun onUnlock(id: Int) {
        viewModelScope.launch {
            _homeState.value = _homeState.value.copy(
                drawerData = _homeState.value.drawerData.map {
                    if (it.id == id) {
                        it.copy(lockStatus = LockStatus.UNLOCKING)
                    } else it
                }
            )
//            delay(2000)
//            val success = true
            val success = fridgeRepository.unlockDrawer(id)
            if (success) {
                _homeState.value = _homeState.value.copy(
                    drawerData = _homeState.value.drawerData.map {
                        if (it.id == id) {
                            it.copy(lockStatus = LockStatus.UNLOCKED)
                        } else it
                    }
                )
            } else {
                _homeState.value = _homeState.value.copy(
                    drawerData = _homeState.value.drawerData.map {
                        if (it.id == id) {
                            it.copy(lockStatus = LockStatus.LOCKED)
                        } else it
                    }
                )
            }
        }
    }

    fun onLock(id: Int) {
        viewModelScope.launch {
            _homeState.value = _homeState.value.copy(
                drawerData = _homeState.value.drawerData.map {
                    if (it.id == id) {
                        it.copy(lockStatus = LockStatus.LOCKING)
                    } else it
                }
            )
//            delay(2000)
//            val success = false
            val success = fridgeRepository.lockDrawer(id)
            if (success) {
                _homeState.value = _homeState.value.copy(
                    drawerData = _homeState.value.drawerData.map {
                        if (it.id == id) {
                            it.copy(
                                lockStatus = LockStatus.LOCKED,
                                showLockWarning = false
                            )
                        } else it
                    }
                )
            } else {
                Log.e("HomeViewModel", "Failed to lock drawer $id")
                _homeState.value = _homeState.value.copy(
                    drawerData = _homeState.value.drawerData.map {
                        if (it.id == id) {
                            it.copy(
                                lockStatus = LockStatus.UNLOCKED,
                                showLockWarning = true
                            )
                        } else it
                    }
                )
            }
        }
    }

    fun onLockWarningClicked() {
        _homeState.value = _homeState.value.copy(showLockWarningDialog = true)
    }

    fun onLockWarningDialogDismissed() {
        _homeState.value = _homeState.value.copy(showLockWarningDialog = false)
    }

    fun onSnapshot(id: Int, bitmap: Bitmap) {
        viewModelScope.launch {
            _homeState.value = _homeState.value.copy(
                drawerData = _homeState.value.drawerData.map {
                    if (it.id == id) {
                        it.copy(isSnapshotLoading = true)
                    } else it
                }
            )
            fridgeRepository.fetchDrawerImage()?.let { drawerImage ->
                _homeState.value = _homeState.value.copy(
                    drawerData = _homeState.value.drawerData.map {
                        if (it.id == id) {
                            it.copy(
                                isSnapshotLoading = false,
                                snapshot = drawerImage,
                                isExpanded = true
                            )
                        } else it
                    }
                )
            }
//            delay(2000)
//            _homeState.value = _homeState.value.copy(
//                drawerData = _homeState.value.drawerData.map {
//                    if (it.id == id) {
//                        it.copy(
//                            isSnapshotLoading = false,
//                            snapshot = bitmap,
//                            isExpanded = true
//                        )
//                    } else it
//                }
//            )
        }
    }

    fun onViewItems(id: Int) = viewModelScope.launch {
        _homeNavigationEvent.emit(HomeNavigationEvent.ToDrawerItems(id))
    }

    fun onExpandCard(id: Int) {
        _homeState.value = _homeState.value.copy(
            drawerData = _homeState.value.drawerData.map {
                if (it.id == id) {
                    it.copy(isExpanded = true)
                } else it
            }
        )
    }

    fun onCollapseCard(id: Int) {
        _homeState.value = _homeState.value.copy(
            drawerData = _homeState.value.drawerData.map {
                if (it.id == id) {
                    it.copy(isExpanded = false)
                } else it
            }
        )
    }
}

data class HomeState(
    val displayName: String = "Alexa",
//    val temperature: Float = 4f,
//    val drawerData: List<DrawerCardData> = listOf(
//        DrawerCardData(id = 1, name = "Drawer 1", isCameraSupported = true),
//        DrawerCardData(id = 2, name = "Drawer 2", isCameraSupported = false)
//    ),
    val temperature: Float = 0f,
    val drawerData: List<DrawerCardData> = listOf(),
    val drawerImage: Bitmap? = null,
    val showLockWarningDialog: Boolean = false,
    val showErrorToast: Boolean = false,
    val isRefreshing: Boolean = false
)

sealed interface HomeNavigationEvent {
    data class ToDrawerItems(val drawerId: Int) : HomeNavigationEvent
}