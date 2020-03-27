package org.tuvecinoteayuda.request_detail

import androidx.lifecycle.ViewModel
import org.tuvecinoteayuda.data.helprequests.repository.HelpRequestRepository

class RequestDetailViewModel(private val requestRepository: HelpRequestRepository) : ViewModel() {

    fun start(requestId: Int) {
        val request = requestRepository.findRequestById(requestId)
    }
}