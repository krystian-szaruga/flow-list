package com.olr261dn.domain.usecase

import com.olr261dn.domain.model.NavbarPosition
import com.olr261dn.domain.model.QuickActionPosition

data class PositionActions (
    val getNavbar: UseCaseReturnOnly<NavbarPosition>,
    val setNavbar: UseCaseInput<NavbarPosition>,
    val getFab: UseCaseReturnOnly<QuickActionPosition>,
    val setFab: UseCaseInput<QuickActionPosition>,
)