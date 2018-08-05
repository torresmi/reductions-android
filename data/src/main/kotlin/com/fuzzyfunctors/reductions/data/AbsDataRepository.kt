package com.fuzzyfunctors.reductions.data

import arrow.core.Either
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import com.fuzzyfunctors.reductions.domain.Repository
import com.fuzzyfunctors.reductions.domain.toRemoteData
import io.reactivex.Flowable
import io.reactivex.Single
import remotedata.RemoteData

abstract class AbsDataRepository<A : Any> : Repository<A> {

    abstract fun networkCall(): Single<Either<LoadingFailure.Remote, A>>

    override fun fetch(): Flowable<RemoteData<LoadingFailure.Remote, A>> =
            networkCall()
                    .doAfterSuccess {
                        when (it) {
                            is Either.Right -> {
                                save(it.b)
                            }
                        }
                    }
                    .map { it.toRemoteData() }
                    .toFlowable()
                    .startWith(RemoteData.Loading)
}
