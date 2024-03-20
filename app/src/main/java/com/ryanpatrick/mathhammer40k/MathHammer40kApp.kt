package com.ryanpatrick.mathhammer40k

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MathHammer40kApp: Application() {
    override fun onCreate() {
        super.onCreate()
        val applicationScope = CoroutineScope(SupervisorJob())
        Graph.provide(this, applicationScope)
    }
}