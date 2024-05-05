job("Hello World!") {
    container(displayName = "Say Hello", image = "hello-world")
}

job("Сборка с уведомлением") {
    container(displayName = "Собрать и уведомить об ошибке", image = "gradle:7.1-jre11") {
        kotlinScript { api ->
            try {
                api.gradle("build")
            } catch (ex: Exception) {
                val channel = ChannelIdentifier.Channel(ChatChannel.FromName("CI-channel"))
                val content = ChatMessage.Text("Ошибка сборки проекта")
                api.space().chats.messages.sendMessage(channel = channel, content = content)
            }
        }
    }
}

job("Сборка Gradle") {
  	gradlew("amazoncorretto:17-alpine", "build")
}
