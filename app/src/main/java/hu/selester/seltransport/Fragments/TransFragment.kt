package hu.selester.seltransport.Fragments

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.selester.seltransport.Adapters.TransDataAdapter
import hu.selester.seltransport.R
import kotlinx.android.synthetic.main.frg_trans.view.*

class TransFragment:Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.frg_trans, container, false)
        rootView.trans_viewpager.adapter = TransDataAdapter(childFragmentManager)
        rootView.trans_tab.addTab(rootView.trans_tab.newTab().setIcon(R.drawable.tab_doc))
        rootView.trans_tab.addTab(rootView.trans_tab.newTab().setIcon(R.drawable.tab_photo))
        rootView.trans_viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(rootView.trans_tab))
        rootView.trans_tab.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                rootView.trans_viewpager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        return rootView
    }

}