package main

class ApplicationStarter {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {

            System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver")

            SocialInteractionSimulation().startSimulation()
        }
    }
}