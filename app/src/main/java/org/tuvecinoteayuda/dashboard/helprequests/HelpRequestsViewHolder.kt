package org.tuvecinoteayuda.dashboard.helprequests

import androidx.lifecycle.Lifecycle
import org.tuvecinoteayuda.ViewModelFactory
import org.tuvecinoteayuda.core.ui.LifecycleViewHolder
import org.tuvecinoteayuda.data.helprequests.models.HelpRequest
import org.tuvecinoteayuda.databinding.CardHelpRequestBinding

class HelpRequestsViewHolder(
    private val binding: CardHelpRequestBinding,
    parentLifecycle: Lifecycle
) : LifecycleViewHolder(binding.root, parentLifecycle) {

    private val viewModel: HelpRequestsViewModel =
        ViewModelFactory.getInstance().create(HelpRequestsViewModel::class.java)

    init {
        binding.apply {
            lifecycleOwner = this@HelpRequestsViewHolder
            vm = viewModel
        }
    }

    fun bind(item: HelpRequest) {
        binding.item = item
        viewModel.findCityById(item.user.state, item.user.city)
    }
}
