package com.jetpack.application.second

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jetpack.application.AppBaseActivity
import com.jetpack.application.R
import com.jetpack.application.api.AppRetrofitService
import com.jetpack.application.databinding.ActivityMainBinding
import com.jetpack.application.index.fragment.IndexFragment
import com.jetpack.application.model.ResponseLiveData
import com.jetpack.support.util.getFragmentInstance

class SecondActivity :
    AppBaseActivity<ActivityMainBinding, SecondActivityPresenter, SecondActivityViewModel>() {

    override val isHaveToolbar: Boolean = true

    private val fragments = arrayListOf<Fragment>(
        getFragmentInstance<IndexFragment>(),
        getFragmentInstance<IndexFragment>()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewPager.adapter = MyAdapter()

        vm.tempData.value = arrayListOf("9")

        binding.btn1.setOnClickListener {
            vm.tempData.value = arrayListOf("11", "12", "13")
        }

//        requests.value = arrayListOf(
//            getRetrofit().create(
//                AppRetrofitService::class.java
//            ).getAppStatus()
//        )


    }

    override fun permissionIsGranted(isGranted: Boolean) {
    }

    override fun getPresenterInstance(): SecondActivityPresenter =
        SecondActivityPresenter(lifecycle, this)

    override val vm: SecondActivityViewModel by viewModels()
    override val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    inner class MyAdapter : FragmentStateAdapter(this) {
        override fun getItemCount(): Int = fragments.size

        override fun createFragment(position: Int): Fragment = fragments[position]

    }

    override val responseClass: String = "com.jetpack.application.model.ResponseLiveData"


}