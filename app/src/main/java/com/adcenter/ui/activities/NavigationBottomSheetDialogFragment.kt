package com.adcenter.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adcenter.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.layout_bottom_sheet_navigation.*

class NavigationBottomSheetDialogFragment : BottomSheetDialogFragment() {

    var onItemSelectedListener: ((Int) -> Boolean)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_bottom_sheet_navigation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            dismiss()
            onItemSelectedListener?.invoke(menuItem.itemId) ?: false
        }
    }
}