package com.lifespandh.ireflexions.home.resourceLibrary

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.HomeViewModel
import com.lifespandh.ireflexions.home.course.CourseFragmentArgs
import com.lifespandh.ireflexions.models.ResourceLibraryItem
import kotlinx.android.synthetic.main.fragment_resource_item.resourceItemRV
import kotlinx.android.synthetic.main.fragment_resource_library.resourceLibraryRV

class ResourceItemFragment : BaseFragment(), ResourceItemAdapter.OnItemClicked {

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }
    private val resourceItemAdapter by lazy { ResourceItemAdapter(listOf(), this) }
    private val args: ResourceItemFragmentArgs by navArgs()
    private var resourceLibraryItem: ResourceLibraryItem? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_resource_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        getBundleValues()
        setViews()
    }

    private fun setViews() {
        resourceItemRV.apply {
            adapter = resourceItemAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        resourceLibraryItem?.let { resourceItemAdapter.setList(it.resources) }
    }

    private fun getBundleValues() {
        resourceLibraryItem = args.item
    }

    override fun onItemClicked(link: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)
    }

}