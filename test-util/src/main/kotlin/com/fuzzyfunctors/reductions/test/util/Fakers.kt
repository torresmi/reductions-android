package com.fuzzyfunctors.reductions.test.util

import io.github.serpro69.kfaker.Faker

val FAKER_DEFAULT = Faker()

inline fun <A> fake(config: Faker.() -> A) = config(FAKER_DEFAULT)
