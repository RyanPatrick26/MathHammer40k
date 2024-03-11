package com.ryanpatrick.mathhammer40k

import android.app.Application

class MathHammer40kApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}