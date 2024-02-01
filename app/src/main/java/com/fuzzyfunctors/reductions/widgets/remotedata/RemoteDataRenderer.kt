package com.fuzzyfunctors.reductions.widgets.remotedata

import remotedata.RemoteData

interface RemoteDataRenderer {
    fun showNotAskedView()

    fun showLoading()

    fun showSuccess()

    fun showFailure(failureMessage: String? = null)

    fun <E : Any, A : Any> bind(
        data: RemoteData<E, A>,
        onSuccess: (A) -> Unit,
    ) = bind(
        data,
        { null },
        onSuccess,
    )

    fun <E : Any, A : Any> bind(
        data: RemoteData<E, A>,
        onFailure: (E) -> String?,
        onSuccess: (A) -> Unit,
    ) {
        when (data) {
            is RemoteData.NotAsked -> {
                showNotAskedView()
            }
            is RemoteData.Loading -> {
                showLoading()
            }
            is RemoteData.Success -> {
                showSuccess()
                onSuccess(data.data)
            }
            is RemoteData.Failure -> {
                showFailure()
                onFailure(data.error)
            }
        }
    }
}
