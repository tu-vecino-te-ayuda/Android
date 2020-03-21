package org.tuvecinoteayuda.help

class WantToHelpPresenter(private val ui: WantToHelpView) {

    interface WantToHelpView {
        fun onWantToHelp()
    }

    fun onWantToHelp() {
        ui.onWantToHelp()
    }

    companion object {
        fun newIntance(ui: WantToHelpView): WantToHelpPresenter {
            return WantToHelpPresenter(ui)
        }
    }
}