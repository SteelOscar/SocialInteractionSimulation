package di

import dagger.Component
import data.di.DataModule
import main.SocialInteractionSimulation
import main.di.MainModule

@Component(
    modules = [
        DataModule::class,
        MainModule::class
    ]
)
interface AppComponent {

    fun inject(app: SocialInteractionSimulation)
}