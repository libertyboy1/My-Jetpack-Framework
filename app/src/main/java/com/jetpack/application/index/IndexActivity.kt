package com.jetpack.application.index

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jetpack.application.AppBaseActivity
import com.jetpack.application.R
import com.jetpack.application.api.AppRetrofitService
import com.jetpack.application.databinding.ActivityMainBinding
import com.jetpack.application.databinding.AdapterIndexBinding
import com.jetpack.application.index.fragment.IndexFragment
import com.jetpack.support.adapter.BaseRecyclerViewAdapter
import com.jetpack.support.ui.SupportFragment
import com.jetpack.support.util.getFragmentInstance
import io.reactivex.rxjava3.core.Observable

class IndexActivity :
    AppBaseActivity<ActivityMainBinding, IndexActivityPresenter, IndexActivityViewModel>() {

    override val isHaveToolbar: Boolean = true
    override fun getRequestList(): ArrayList<Observable<*>?> = arrayListOf(
        getRetrofit().create(
            AppRetrofitService::class.java
        ).messageSign(Any())
    )

    private val fragments = arrayListOf<Fragment>(
        getFragmentInstance<IndexFragment>(),
        getFragmentInstance<IndexFragment>()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding.indexVM = vm
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        binding.recyclerView.adapter = MyAdapter()

        binding.viewPager.adapter = MyAdapter()

        vm.tempData.value = arrayListOf("5")

        binding.btn1.setOnClickListener {
            vm.tempData.value = arrayListOf("5", "6", "7")
        }

    }

    override fun permissionIsGranted(isGranted: Boolean) {
    }

    override fun getPresenterInstance(): IndexActivityPresenter =
        IndexActivityPresenter(lifecycle, this)

    override val vm: IndexActivityViewModel by viewModels()
    override val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    inner class MyAdapter : FragmentStateAdapter(this) {
        override fun getItemCount(): Int = fragments.size

        override fun createFragment(position: Int): Fragment = fragments[position]

    }


}