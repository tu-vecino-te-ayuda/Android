package org.tuvecinoteayuda.dashboard

import androidx.lifecycle.Observer
import org.tuvecinoteayuda.ViewModelFactory
import org.tuvecinoteayuda.core.ui.LifecycleViewHolder
import org.tuvecinoteayuda.core.util.formatDate
import org.tuvecinoteayuda.data.helprequests.models.HelpRequest
import org.tuvecinoteayuda.databinding.CardHelpRequestBinding

class HelpRequestViewHolder(
    private val binding: CardHelpRequestBinding
) : LifecycleViewHolder(binding.root) {

    private val viewModel: HelpRequestViewModel =
        ViewModelFactory.getInstance().create(HelpRequestViewModel::class.java)


    fun bind(item: HelpRequest) {
        binding.title.text = item.helpRequestType.name
        binding.subtitle.text = item.message
        binding.date.text = formatDate(item.createdAt)

        observeCity()
        viewModel.findCityById(item.user.state, item.user.city)
    }

    private fun observeCity() {
        viewModel.city.observe(this, Observer { city ->
            binding.location.text = city.name
        })
    }
}
