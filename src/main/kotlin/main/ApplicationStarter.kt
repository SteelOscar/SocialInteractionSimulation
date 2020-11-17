package main

class ApplicationStarter {
    companion object {

        @JvmStatic
        fun main(args: Array<String>) {

//            if (args.isEmpty()) error("No config file specified")

//            AppConfigParser.parseConfig(args[0])
            AppConfigParser.parseConfig("/home/renat/Desktop/SimulationConfig")

            System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver")

            SocialInteractionSimulation().startSimulation()
        }
    }
}