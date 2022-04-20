package com.jetpack.application.index.fragment

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jetpack.application.R
import com.jetpack.application.api.AppRetrofitService
import com.jetpack.application.databinding.AdapterIndexBinding
import com.jetpack.application.databinding.FragmentIndexBinding
import com.jetpack.application.index.IndexActivity
import com.jetpack.application.index.IndexActivityViewModel
import com.jetpack.support.adapter.BaseRecyclerViewAdapter
import com.jetpack.support.ui.SupportFragment
import io.reactivex.rxjava3.core.Observable

class IndexFragment :
    SupportFragment<IndexActivity, IndexFragmentPresenter, IndexActivityViewModel, FragmentIndexBinding>() {
    override fun getPresenterInstance(): IndexFragmentPresenter =
        IndexFragmentPresenter(lifecycle, this)

    override val vm: IndexActivityViewModel by activityViewModels()
    override fun getLayoutResId(): Int = R.layout.fragment_index
    override fun onViewCreated() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = MyAdapter()
    }

    inner class MyAdapter : BaseRecyclerViewAdapter<AdapterIndexBinding, String>(
        vm.tempData,
        viewLifecycleOwner
    ) {
        override fun getLayoutResId(): Int = R.layout.adapter_index

        override fun bind(binding: AdapterIndexBinding, itemDate: String?) {
            binding.data = itemDate
        }

    }

    override fun getRequestList(): ArrayList<Observable<*>?> = arrayListOf(getRetrofit().create(AppRetrofitService::class.java).messageSign(Any()))

}