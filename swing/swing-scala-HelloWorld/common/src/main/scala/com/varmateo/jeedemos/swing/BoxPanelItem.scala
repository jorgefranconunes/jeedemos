/**************************************************************************
 *
 * Copyright (c) 2019 Jorge Nunes
 *
 **************************************************************************/

package com.varmateo.jeedemos.swing

import java.awt.Component


sealed trait BoxPanelItem


object BoxPanelItem {

    case class Regular(component: Component) extends BoxPanelItem

    case class Filler(component: Component) extends BoxPanelItem

    case object EmptyFiller extends BoxPanelItem
}
