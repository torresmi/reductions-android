package com.fuzzyfunctors.reductions.testutil

import com.fuzzyfunctors.reductions.core.alert.Alert
import com.fuzzyfunctors.reductions.core.deal.Deal
import com.fuzzyfunctors.reductions.core.game.Game
import com.fuzzyfunctors.reductions.core.game.GameBestDeal
import com.fuzzyfunctors.reductions.core.store.Store
import io.kotlintest.properties.Gen
import java.util.Date

fun <A> Gen<A>.firstRandom(): A = random().first()

fun randomString(): String = Gen.string().firstRandom()

fun randomNullableString(): String? = NullableGenerator(Gen.string()).firstRandom()

fun randomBool(): Boolean = Gen.bool().firstRandom()

fun randomInt(): Int = Gen.int().firstRandom()

fun randomNullableInt(): Int? = NullableGenerator(Gen.int()).firstRandom()

fun randomLong(): Long = Gen.long().firstRandom()

fun randomNullableLong(): Long? = NullableGenerator(Gen.long()).firstRandom()

fun randomStore(): Store = StoreGenerator().firstRandom()

fun randomAlert(): Alert = AlertGenerator().firstRandom()

fun randomDeal(): Deal = DealGenerator().firstRandom()

fun randomGame(): Game = GameGenerator().firstRandom()

fun randomGameBestDeal(): GameBestDeal = GameBestDealGenerator().firstRandom()

fun randomDate(): Date = Date(randomLong())

fun randomPercent(): String = Gen.choose(0, 100).firstRandom().toString().plus(" %")
