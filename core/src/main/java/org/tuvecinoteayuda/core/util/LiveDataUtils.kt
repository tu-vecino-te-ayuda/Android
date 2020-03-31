package org.tuvecinoteayuda.core.util

import androidx.lifecycle.MutableLiveData

/**
 * Sets value of the given boolean mutable live data to false.
 */
fun resetErrors(vararg errors: MutableLiveData<Boolean>) {
    for (error in errors) {
        error.postValue(false)
    }
}
