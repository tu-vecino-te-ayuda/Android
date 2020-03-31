package org.tuvecinoteayuda.dashboard.helprequests

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import org.tuvecinoteayuda.ViewModelFactory
import org.tuvecinoteayuda.core.ui.LifecycleViewHolder
import org.tuvecinoteayuda.data.helprequests.models.HelpRequest
import org.tuvecinoteayuda.databinding.CardHelpRequestBinding

class HelpRequestsViewHolder(
    private val parent: ViewGroup,
    private var lifecycleOwner: LifecycleOwner,
    private val binding: CardHelpRequestBinding = CardHelpRequestBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )
) : LifecycleViewHolder(binding.root) {

    private val viewModel: HelpRequestsViewModel =
        ViewModelFactory.getInstance().create(HelpRequestsViewModel::class.java)

    init {
        binding.apply {
//            lifecycleOwner = this@HelpRequestsViewHolder TODO fix viewholder lifecycle (not refreshing)
            lifecycleOwner = this@HelpRequestsViewHolder.lifecycleOwner
            vm = viewModel
        }
    }

    fun bind(item: HelpRequest, onItemClick: (HelpRequest) -> Unit) {
        binding.item = item
        viewModel.findCityById(item.user.state, item.user.city)
        itemView.setOnClickListener { onItemClick.invoke(item) }
    }
}
