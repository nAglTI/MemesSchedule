job("Hello World!") {
    container(displayName = "Say Hello", image = "hello-world")
}

job("Build and publish") {
    container(displayName = "Build and notify", image = "gradle:7.1-jre11") {
        kotlinScript { api ->
            try {
                api.gradle("build")
            } catch (ex: Exception) {
                val channel = ChannelIdentifier.Channel(ChatChannel.FromName("CI-channel"))
                val content = ChatMessage.Text("Build failed")
                api.space().chats.messages.sendMessage(channel = channel, content = content)
            }
        }
    }
}
