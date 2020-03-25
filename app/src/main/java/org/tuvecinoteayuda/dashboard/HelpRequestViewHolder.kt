package org.tuvecinoteayuda.dashboard

import androidx.lifecycle.Lifecycle
import org.tuvecinoteayuda.ViewModelFactory
import org.tuvecinoteayuda.core.ui.LifecycleViewHolder
import org.tuvecinoteayuda.data.helprequests.models.HelpRequest
import org.tuvecinoteayuda.databinding.CardHelpRequestBinding

class HelpRequestViewHolder(
    private val binding: CardHelpRequestBinding,
    parentLifecycle: Lifecycle
) : LifecycleViewHolder(binding.root, parentLifecycle) {

    private val viewModel: HelpRequestViewModel =
        ViewModelFactory.getInstance().create(HelpRequestViewModel::class.java)

    init {
        binding.apply {
            lifecycleOwner = this@HelpRequestViewHolder
            vm = viewModel
        }
    }

    fun bind(item: HelpRequest) {
        binding.item = item
        viewModel.findCityById(item.user.state, item.user.city)
    }
}
