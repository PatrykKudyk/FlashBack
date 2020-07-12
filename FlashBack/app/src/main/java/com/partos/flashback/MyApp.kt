package com.partos.flashback

import android.app.Application
import com.partos.flashback.models.Settings

class MyApp: Application() {
    companion object {
        var userId = (-1).toLong()
        var userSettings = Settings(-1,-1, 15, 5, 10)
    }
}

