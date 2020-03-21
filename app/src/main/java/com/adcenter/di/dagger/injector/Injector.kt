package com.adcenter.di.dagger.injector

import android.content.Context
import com.adcenter.di.dagger.components.*
import com.adcenter.di.dagger.module.*

object Injector {

    private var appComponentInstance: AppComponent? = null

    private var lastAdsComponent: LastAdsComponent? = null
    private var adRequestsComponent: AdRequestsComponent? = null
    private var searchComponent: SearchComponent? = null
    private var bookmarksComponent: BookmarksComponent? = null
    private var myAdsComponent: MyAdsComponent? = null
    private var detailsComponent: DetailsComponent? = null
    private var loginComponent: LoginComponent? = null
    private var registrationComponent: RegistrationComponent? = null

    val appComponent: AppComponent
        get() = appComponentInstance ?: throw IllegalStateException("AppComponent is null")

    fun init(context: Context) {
        if (appComponentInstance == null) {
            appComponentInstance = DaggerAppComponent.builder()
                .contextModule(ContextModule(context))
                .build()
        }
    }

    fun plusLastAdsComponent(): LastAdsComponent {
        if (lastAdsComponent == null) {
            lastAdsComponent = appComponent.plusLastAdsComponent(LastAdsModule())
        }

        return lastAdsComponent ?: throw IllegalStateException("LastAdsComponent is null")
    }

    fun clearLastAdsComponent() {
        lastAdsComponent = null
    }

    fun plusAdRequestsComponent(): AdRequestsComponent {
        if (adRequestsComponent == null) {
            adRequestsComponent = appComponent.plusAdRequestsComponent(AdRequestsModule())
        }

        return adRequestsComponent ?: throw IllegalStateException("AdRequestsComponent is null")
    }

    fun clearAdRequestsComponent() {
        adRequestsComponent = null
    }

    fun plusSearchComponent(): SearchComponent {
        if (searchComponent == null) {
            searchComponent = appComponent.plusSearchComponent(SearchModule())
        }

        return searchComponent ?: throw IllegalStateException("SearchComponent is null")
    }

    fun clearSearchComponent() {
        searchComponent = null
    }

    fun plusBookmarksComponent(): BookmarksComponent {
        if (bookmarksComponent == null) {
            bookmarksComponent = appComponent.plusBookmarksComponent(BookmarksModule())
        }

        return bookmarksComponent ?: throw IllegalStateException("BookmarksComponent is null")
    }

    fun clearBookmarksComponent() {
        bookmarksComponent = null
    }

    fun plusMyAdsComponent(): MyAdsComponent {
        if (myAdsComponent == null) {
            myAdsComponent = appComponent.plusMyAdsComponent(MyAdsModule())
        }

        return myAdsComponent ?: throw IllegalStateException("MyAdsComponent is null")
    }

    fun clearMyAdsComponent() {
        myAdsComponent = null
    }

    fun plusDetailsComponent(): DetailsComponent {
        if (detailsComponent == null) {
            detailsComponent = appComponent.plusDetailsComponent(DetailsModule())
        }

        return detailsComponent ?: throw IllegalStateException("DetailsComponent is null")
    }

    fun clearDetailsComponent() {
        detailsComponent = null
    }

    fun plusLoginComponent(): LoginComponent {
        if (loginComponent == null) {
            loginComponent = appComponent.plusLoginComponent(LoginModule())
        }

        return loginComponent ?: throw IllegalStateException("LoginComponent is null")
    }

    fun clearLoginComponent() {
        loginComponent = null
    }

    fun plusRegistrationComponent(): RegistrationComponent {
        if (registrationComponent == null) {
            registrationComponent = appComponent.plusRegistrationComponent(RegistrationModule())
        }

        return registrationComponent ?: throw IllegalStateException("RegistrationComponent is null")
    }

    fun clearRegistrationComponent() {
        registrationComponent = null
    }
}