package com.partos.flashback

import android.app.Application

class MyApp: Application() {
    companion object {
        var userId = (-1).toLong()
    }
}

