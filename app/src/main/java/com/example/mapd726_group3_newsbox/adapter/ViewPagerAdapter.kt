package com.example.mapd726_group3_newsbox.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mapd726_group3_newsbox.category.NewsCategory1
import com.example.mapd726_group3_newsbox.category.NewsCategory2
import com.example.mapd726_group3_newsbox.category.NewsCategory3
import com.example.mapd726_group3_newsbox.category.NewsCategory4
import com.example.mapd726_group3_newsbox.category.NewsCategory5

class ViewPagerAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return   when(position){
            0->{
                NewsCategory1()
            }
            1->{
                NewsCategory2()
            }
            2->{
                NewsCategory3()
            }
            3->{
                NewsCategory4()
            }
            4->{
                NewsCategory5()
            }
            else->{
                Fragment()
            }

        }
    }
}