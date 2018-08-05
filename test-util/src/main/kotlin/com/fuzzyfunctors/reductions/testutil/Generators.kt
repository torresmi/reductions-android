package com.fuzzyfunctors.reductions.`test-util`

import com.fuzzyfunctors.reductions.core.store.Store
import io.kotlintest.properties.Gen

fun <A> Gen<A>.firstRandom(): A = random().first()

fun randomString(): String = Gen.string().firstRandom()

fun randomBool(): Boolean = Gen.bool().firstRandom()

fun randomInt(): Int = Gen.int().firstRandom()

fun randomStore(): Store = StoreGenerator().firstRandom()
