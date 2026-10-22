package com.example.demo

class WasmJsPlatform : Platform {
    override val name: String = "WebAssembly"
}

actual fun getPlatform(): Platform = WasmJsPlatform()
