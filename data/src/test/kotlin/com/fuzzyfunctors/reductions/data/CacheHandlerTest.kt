package com.fuzzyfunctors.reductions.data

import com.fuzzyfunctors.reductions.domain.LoadingFailure
import io.kotlintest.specs.DescribeSpec
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import remotedata.RemoteData

class CacheHandlerTest : DescribeSpec() {

    override fun isInstancePerTest(): Boolean = true

    val ts = TestSubscriber.create<RemoteData<LoadingFailure, Int>>()

    val cacheResponse: RemoteData<LoadingFailure, Int> = RemoteData.succeed(1)
    val fetchResponse: RemoteData<LoadingFailure.Remote, Int> = RemoteData.succeed(2)
    val fetchCall = Flowable.just(fetchResponse)

    init {

        describe("getting the latest value") {

            context("there is an existing cached value") {

                val sut = CacheHandler(cacheResponse)

                it("should not fetch from network") {

                    sut.getCacheOrFetch { fetchCall }.subscribe(ts)

                    ts.assertValue(cacheResponse)
                }
            }

            context("there is not an existing cached value") {

                val sut = CacheHandler<Int>(RemoteData.NotAsked)

                it("should fetch from network") {

                    sut.getCacheOrFetch { fetchCall }.subscribe(ts)

                    ts.assertValue(fetchResponse)
                }
            }
        }

    }
}
