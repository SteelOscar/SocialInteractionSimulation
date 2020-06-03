package data.di

object DataDeserializerComponentHolder {

    private var component: DataDeserializerComponent? = null
    
    fun getComponent(): DataDeserializerComponent {

        if (component == null) {

            component = DaggerDataDeserializerComponent.create()
        }

        return component!!
    }
}