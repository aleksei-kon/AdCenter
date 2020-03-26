package com.adcenter.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adcenter.R
import com.adcenter.config.IAppConfig
import com.adcenter.di.dagger.injector.Injector
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.layout_bottom_sheet_navigation.*
import javax.inject.Inject

class NavigationBottomSheetDialogFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var appConfig: IAppConfig

    init {
        Injector.appComponent.inject(this)
    }

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

        closeMenuButton.setOnClickListener { dismiss() }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            dismiss()
            onItemSelectedListener?.invoke(menuItem.itemId) ?: false
        }

        updateMenu()
    }

    private fun updateMenu() {
        val menuRes = when {
            appConfig.isAdmin -> R.menu.bottom_app_bar_admin_menu
            appConfig.isLoggedIn -> R.menu.bottom_app_bar_user_menu
            else -> R.menu.bottom_app_bar_guest_menu
        }

        navigationView.inflateMenu(menuRes)
    }
}