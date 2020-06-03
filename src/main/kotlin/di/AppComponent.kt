package di

import dagger.Component
import data.di.DataModule
import domain.di.DomainModule
import main.SocialInteractionSimulation
import main.di.MainModule

@Component(
    modules = [
        DataModule::class,
        DomainModule::class,
        MainModule::class
    ]
)
interface AppComponent {

    fun inject(app: SocialInteractionSimulation)
}