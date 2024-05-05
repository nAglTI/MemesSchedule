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
  container(displayName = "Создание задачи при ошибке", image = "gradle") {
        kotlinScript { api ->
            try {
                api.gradle("build")
            } catch (ex: Exception) {
                // получение Id проекта
                val id = api.projectId()
                // получение номера выполняемого скрипта
                val runNumber = api.executionNumber()

                // получение статусов доступных для задач
                val statuses = api.space().projects.planning.issues.statuses.
                getAllIssueStatuses(project = ProjectIdentifier.Id(id))
                // получение Id статуса с названием "Open"
                val openStatusId = statuses.find { it.name == "Open" }?.id
                    ?: throw kotlin.Exception("The 'Open' state doesn't exist in the project")
                // создание задачи со статусом 'Open'
                api.space().projects.planning.issues.createIssue(
                    project = ProjectIdentifier.Id(id),
                    // генерация названия задачи с использованием номера выполняемого скрипта
                    title = "Job 'Build and publish' #$runNumber failed",
                    description = "${ex.message}",
                    status = openStatusId
                )
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
