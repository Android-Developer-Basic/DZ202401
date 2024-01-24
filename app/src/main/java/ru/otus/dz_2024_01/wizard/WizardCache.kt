package ru.otus.dz_2024_01.wizard

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class WizardCache @Inject constructor() {
    var name: String = ""
    var address: String = ""
}