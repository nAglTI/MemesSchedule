job("Easy job") {
    container(displayName = "Say Hello", image = "hello-world")
}

job("Сборка с уведомлением") {
    container(displayName = "Собрать и уведомить об ошибке", image = "gradle:7.1-jre11") {
        kotlinScript { api ->
            try {
                api.gradlew("build") {
        			disableSpaceInitScript = true
    			}
            } catch (ex: Exception) {
                val channel = ChannelIdentifier.Channel(ChatChannel.FromName("CI-channel"))
                val content = ChatMessage.Text("Ошибка сборки проекта")
                api.space().chats.messages.sendMessage(channel = channel, content = content)
            }
        }
    }
}

job("Сборка Gradle") {
  container(displayName = "Amazon JVM Build", image = "amazoncorretto:17-alpine") {
        kotlinScript { api ->
    		api.gradlew("build") {
        		disableSpaceInitScript = true
    		}
		}
    }
}

job("Print total members count") {
    container(image = "amazoncorretto:17-alpine") {
        kotlinScript { api ->
            val totalMembers = api.space().teamDirectory.stats.getAllStats().totalMembers
            println("Total members count: $totalMembers")
        }
    }
}
