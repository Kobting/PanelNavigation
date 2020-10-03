package com.mikep.panelnavigation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

open class PanelNavigator(
    protected val activity: AppCompatActivity,
    startPanelHostClass: Class<out PanelHost>,
    centerPanelHostClass: Class<out PanelHost>,
    endPanelHostClass: Class<out PanelHost>
): LifecycleObserver {

    enum class NavigationTarget {
        START_PANEL,
        CENTER_PANEL,
        END_PANEL
    }

    enum class NavigationType {
        REPLACE,
        REPLACE_NO_BACKSTACK,
        ADD
    }

    private val fragmentStates = mutableMapOf<String, Fragment.SavedState?>()

    var panelNavigationListener: PanelNavigationListener = PanelNavigationListener {}

    protected var startPanelHost: PanelHost = startPanelHostClass.newInstance()
    protected var centerPanelHost: PanelHost = centerPanelHostClass.newInstance()
    protected var endPanelHost: PanelHost = endPanelHostClass.newInstance()

    init {
        activity.lifecycle.addObserver(this)
        initHostPanels()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        //Not sure if this is really needed
        savePanelStates()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        activity.lifecycle.removeObserver(this)
    }

    private fun initHostPanels() {
        val hasStartPanel = activity.supportFragmentManager.findFragmentById(startPanelHost.hostInitialContainerViewId) != null
        val hasCenterPanel = activity.supportFragmentManager.findFragmentById(centerPanelHost.hostInitialContainerViewId) != null
        val hasEndPanel = activity.supportFragmentManager.findFragmentById(endPanelHost.hostInitialContainerViewId) != null

        addInitialHostFragment(startPanelHost, hasStartPanel, NavigationTarget.START_PANEL)
        addInitialHostFragment(centerPanelHost, hasCenterPanel, NavigationTarget.CENTER_PANEL)
        addInitialHostFragment(endPanelHost, hasEndPanel, NavigationTarget.END_PANEL)
    }

    private fun addInitialHostFragment(panelHost: PanelHost, exists: Boolean, navigationTarget: NavigationTarget) {
        if(!exists) {
            activity.supportFragmentManager.beginTransaction().replace(panelHost.hostInitialContainerViewId, panelHost, panelHost::class.java.canonicalName).runOnCommit {
                if(fragmentStates[panelHost::class.java.name] == null) {
                    navigate(panelHost.hostInitialFragmentClass, navigationTarget,
                        NavigationType.REPLACE_NO_BACKSTACK
                    )
                } else {
                    panelHost.setInitialSavedState(fragmentStates[panelHost::class.java.name])
                }
            }.commit()
        }
        //Restoring previous fragments if this navigator was recreated due to activity being recreated
        else {
            when(navigationTarget) {
                NavigationTarget.START_PANEL -> startPanelHost = activity.supportFragmentManager.findFragmentById(startPanelHost.hostInitialContainerViewId) as PanelHost
                NavigationTarget.CENTER_PANEL -> centerPanelHost = activity.supportFragmentManager.findFragmentById(centerPanelHost.hostInitialContainerViewId) as PanelHost
                NavigationTarget.END_PANEL -> endPanelHost = activity.supportFragmentManager.findFragmentById(endPanelHost.hostInitialContainerViewId) as PanelHost
            }
        }
    }

    open fun navigate(fragmentClazz: Class<out Fragment>, navigationTarget: NavigationTarget, navigationType: NavigationType = NavigationType.REPLACE) {
        navigate(fragmentClazz.newInstance(), navigationTarget, navigationType)
    }

    open fun navigate(fragment: Fragment, navigationTarget: NavigationTarget, navigationType: NavigationType = NavigationType.REPLACE) {
        when(navigationTarget) {
            NavigationTarget.START_PANEL -> navigateStartPanel(fragment, navigationType)
            NavigationTarget.CENTER_PANEL -> navigateCenterPanel(fragment, navigationType)
            NavigationTarget.END_PANEL -> navigateEndPanel(fragment, navigationType)
        }
    }

    /**
     * @return if back was handled by the current [PanelHost]
     */
    open fun onBack(navigationTarget: NavigationTarget): Boolean {
        when(navigationTarget) {
            NavigationTarget.START_PANEL -> {
                if(startPanelHost.childFragmentManager.backStackEntryCount > 0) {
                    startPanelHost.childFragmentManager.popBackStack()
                    return true
                } else {
                    return false
                }
            }
            NavigationTarget.CENTER_PANEL -> {
                if(centerPanelHost.childFragmentManager.backStackEntryCount > 0) {
                    centerPanelHost.childFragmentManager.popBackStack()
                    return true
                } else {
                    return false
                }
            }
            NavigationTarget.END_PANEL -> {
                if(endPanelHost.childFragmentManager.backStackEntryCount > 0) {
                    endPanelHost.childFragmentManager.popBackStack()
                    return true
                } else {
                    return false
                }
            }
        }
    }

    private fun navigateStartPanel(fragment: Fragment, navigationType: NavigationType) {
        with(beginTransaction(NavigationTarget.START_PANEL)) {
            if(navigationType == NavigationType.REPLACE) addToBackStack(fragment::class.java.canonicalName)
            replace(startPanelHost.hostInitialContainerViewId, fragment, fragment::class.java.canonicalName)
        }.commit().also {
            panelNavigationListener.onPanelNavigation(NavigationTarget.START_PANEL)
        }
    }

    private fun navigateCenterPanel(fragment: Fragment, navigationType: NavigationType) {
        with(beginTransaction(NavigationTarget.CENTER_PANEL)) {
            if(navigationType == NavigationType.REPLACE) addToBackStack(fragment::class.java.canonicalName)
            replace(centerPanelHost.hostInitialContainerViewId, fragment, fragment::class.java.canonicalName)
        }.commit().also {
            panelNavigationListener.onPanelNavigation(NavigationTarget.CENTER_PANEL)
        }
    }

    private fun navigateEndPanel(fragment: Fragment, navigationType: NavigationType) {
        with(beginTransaction(NavigationTarget.END_PANEL)) {
            if(navigationType == NavigationType.REPLACE) addToBackStack(fragment::class.java.canonicalName)
            replace(endPanelHost.hostInitialContainerViewId, fragment, fragment::class.java.canonicalName)
        }.commit().also {
            panelNavigationListener.onPanelNavigation(NavigationTarget.END_PANEL)
        }
    }

    private fun savePanelStates() {
        savePanelHostState(startPanelHost)
        savePanelHostState(centerPanelHost)
        savePanelHostState(endPanelHost)
    }

    private fun savePanelHostState(panelHost: PanelHost) {
        fragmentStates[panelHost::class.java.name] = activity.supportFragmentManager.saveFragmentInstanceState(activity.supportFragmentManager.findFragmentById(panelHost.hostInitialContainerViewId) ?: return)
    }

    /**
     * Begin fragment transaction for the given [NavigationTarget]. Should be used if the normal [navigate]
     * options are not flexible enough.
     *
     * @param navigationTarget TargetPanel to do the fragment transaction on
     *
     * @return [FragmentTransaction] for the [PanelHost] that matches the given [NavigationTarget]
     */
    fun beginTransaction(navigationTarget: NavigationTarget): FragmentTransaction {
        return when(navigationTarget) {
            NavigationTarget.START_PANEL -> startPanelHost.childFragmentManager.beginTransaction()
            NavigationTarget.CENTER_PANEL -> centerPanelHost.childFragmentManager.beginTransaction()
            NavigationTarget.END_PANEL -> endPanelHost.childFragmentManager.beginTransaction()
        }
    }

    /**
     * Get the containerViewId for the [NavigationTarget] to use in fragment transactions
     *
     * @param navigationTarget TargetPanel for wanted containerViewId
     *
     * @return containerViewId of the [NavigationTarget]
     */
    fun getPanelContainerViewId(navigationTarget: NavigationTarget): Int {
        return when(navigationTarget) {
            NavigationTarget.START_PANEL -> startPanelHost.hostInitialContainerViewId
            NavigationTarget.CENTER_PANEL -> centerPanelHost.hostInitialContainerViewId
            NavigationTarget.END_PANEL -> endPanelHost.hostInitialContainerViewId
        }
    }
}