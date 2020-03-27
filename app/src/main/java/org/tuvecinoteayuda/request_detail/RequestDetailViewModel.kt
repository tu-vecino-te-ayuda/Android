package org.tuvecinoteayuda.request_detail

import androidx.lifecycle.ViewModel
import org.tuvecinoteayuda.data.helprequests.models.HelpRequest
import org.tuvecinoteayuda.data.helprequests.repository.HelpRequestRepository

class RequestDetailViewModel(private val requestRepository: HelpRequestRepository): ViewModel() {


    fun start(requestId: Int) {
        requestRepository.findRequestById(requestId)
    }


}