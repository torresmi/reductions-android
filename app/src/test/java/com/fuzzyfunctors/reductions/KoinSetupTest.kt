package com.fuzzyfunctors.reductions

import android.app.Application
import android.content.Context
import com.fuzzyfunctors.reductions.injection.Modules
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.koin.KoinExtension
import io.mockk.mockk
import io.mockk.mockkClass
import org.koin.dsl.binds
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.check.checkModules

class KoinSetupTest : DescribeSpec(), KoinTest {
    private val modules = Modules.get(false)

    override fun extensions() = listOf(
        KoinExtension(modules) {
            mockkClass(it, relaxed = true)
        },
    )

    private val applicationSetupModule = module {
        single { mockk<Application>() } binds arrayOf(
            Context::class,
            Application::class,
        )
    }

    init {
        describe("Dependency resolution") {

            it("should resolve all production dependencies") {
                getKoin().run {
                    loadModules(listOf(applicationSetupModule))
                    checkModules()
                }
            }
        }
    }
}
