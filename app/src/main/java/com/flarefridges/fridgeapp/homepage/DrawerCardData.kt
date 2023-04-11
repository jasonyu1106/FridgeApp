package com.flarefridges.fridgeapp.homepage

import android.graphics.Bitmap

data class DrawerCardData(
    val id: Int,
    val name: String,
    val lockStatus: LockStatus = LockStatus.LOCKED,
    val showLockWarning: Boolean = false,
    val isCameraSupported: Boolean = false,
    val snapshot: Bitmap? = null,
    val isExpanded: Boolean = false,
    val isSnapshotLoading: Boolean = false,
) {
    enum class LockStatus {
        LOCKED,
        UNLOCKED,
        LOCKING,
        UNLOCKING
    }
}
