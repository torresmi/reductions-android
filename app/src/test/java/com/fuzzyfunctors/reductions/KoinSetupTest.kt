package com.fuzzyfunctors.reductions

import com.fuzzyfunctors.reductions.injection.Modules
import io.kotlintest.specs.DescribeSpec
import org.koin.test.KoinTest
import org.koin.test.checkModules

class KoinSetupTest : DescribeSpec(), KoinTest {


    init {
        describe("Dependency resolution") {

            it("should resolve all production dependencies") {
                checkModules(Modules.get(false))
            }
        }
    }
}
