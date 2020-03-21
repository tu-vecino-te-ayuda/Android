package org.tuvecinoteayuda.needhelp

class NeedHelpPresenter(private val ui: NeedHelpView) {

    interface NeedHelpView {
        fun onNeedHelp()
    }

    fun onWantToHelp() {
        ui.onNeedHelp()
    }

    companion object {
        fun newIntance(ui: NeedHelpView): NeedHelpPresenter {
            return NeedHelpPresenter(ui)
        }
    }
}