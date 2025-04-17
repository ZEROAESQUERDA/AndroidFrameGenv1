package com.mobfgsr.framegeneration

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SettingsPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val fragments = listOf(
        BasicSettingsFragment.newInstance(),
        AdvancedSettingsFragment.newInstance()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun getBasicSettingsFragment(): BasicSettingsFragment = fragments[0] as BasicSettingsFragment

    fun getAdvancedSettingsFragment(): AdvancedSettingsFragment = fragments[1] as AdvancedSettingsFragment
}
