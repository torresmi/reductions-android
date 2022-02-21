package com.fuzzyfunctors.reductions

import com.fuzzyfunctors.reductions.injection.Modules
import io.kotest.core.spec.style.DescribeSpec
import org.koin.test.KoinTest
import org.koin.test.check.checkModules

class KoinSetupTest : DescribeSpec(), KoinTest {

    init {
        describe("Dependency resolution") {

            it("should resolve all production dependencies") {
                checkModules {
                    modules(Modules.get(false))
                }
            }
        }
    }
}
