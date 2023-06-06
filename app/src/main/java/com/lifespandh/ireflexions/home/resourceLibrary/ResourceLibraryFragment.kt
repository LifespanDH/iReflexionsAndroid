package com.lifespandh.ireflexions.home.resourceLibrary

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.HomeViewModel
import com.lifespandh.ireflexions.models.ResourceLibraryItem
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import kotlinx.android.synthetic.main.fragment_resource_library.resourceLibraryRV


class ResourceLibraryFragment : BaseFragment(), ResourceLibraryAdapter.OnItemClicked {

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }
    private val resourceLibraryAdapter by lazy { ResourceLibraryAdapter(listOf(), this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resource_library, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        getResourceContent()
        setObservers()
        setViews()
    }

    private fun setViews() {
        resourceLibraryRV.apply {
            adapter = resourceLibraryAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun getResourceContent() {
        homeViewModel.getResourceContent()
    }

    private fun setObservers(){
        homeViewModel.resourceContentLiveData.observeFreshly(this){
            resourceLibraryAdapter.setList(it)
        }

    }


    companion object {
        fun newInstance() = ResourceLibraryFragment()
    }

    override fun onItemClicked(resourceLibraryItem: ResourceLibraryItem) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(resourceLibraryItem.resources.))
        startActivity(browserIntent)
    }
}