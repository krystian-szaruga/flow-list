package com.olr261dn.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olr261dn.domain.viewmodel.BaseItemViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<T>: ViewModel(), BaseItemViewModel<T> {
    protected fun launch(action: suspend () -> Unit) {
        viewModelScope.launch { action() }
    }
}