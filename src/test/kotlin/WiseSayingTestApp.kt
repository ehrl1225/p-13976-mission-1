import com.WiseSayingApp
import com.domain.wiseSaying.wiseSaying.repository.WiseSayingRepository
import com.global.Ut.text.ReadText
import com.global.config.RunMode
import com.global.cotext.ContextManager

class WiseSayingTestApp {

    val wiseSayingRepository by lazy{
        ContextManager.get<WiseSayingRepository>("wiseSayingRepository")!!
    }

    fun setCommands(commands: List<String>){
        val readText = ContextManager.get<ReadText>("readText")!!
        readText.testTexts.clear()
        readText.testIndex = 0
        readText.testTexts.addAll(commands)
        readText.testTexts.add("종료") // 마지막에 종료 명령어 추가
    }

    fun run(reset: Boolean = true){
        val app = WiseSayingApp(RunMode.TEST)
        app.run()
        if (reset)
            wiseSayingRepository.reset()
    }
}