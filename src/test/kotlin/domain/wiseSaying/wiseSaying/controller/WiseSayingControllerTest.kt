package domain.wiseSaying.wiseSaying.controller


import WiseSayingTestApp
import com.global.Ut.text.WriteText
import com.global.cotext.ContextManager
import global.expect.Expected
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class WiseSayingControllerTest {
    val writeText by lazy{
        ContextManager.get<WriteText>("writeText")!!
    }

    fun testApp(commands: List<String>, reset: Boolean = true){
        val app = WiseSayingTestApp()
        app.setCommands(commands)
        app.run(reset=reset)
    }

    @Test
    @DisplayName("1단계")
    fun t1(){
        testApp(listOf())
        val expectedOutputs = listOf(
            "== 명언 앱 ==",
            "명령) ",
        )
        val actualOutputs = writeText.wroteTexts
        val expected = Expected(
            expectedOutputs,
            actualOutputs,
        )
        expected.check()
    }

    @Test
    @DisplayName("2,3단계")
    fun t2(){
        testApp(listOf(
            "등록",
            "현재를 사랑하라.",
            "작자미상"
        ))
        val expectedOutputs = listOf(
            "== 명언 앱 ==",
            "명언 : ",
            "작가 : ",
        )
        val actualOutputs = writeText.wroteTexts
        val expected = Expected(
            expectedOutputs,
            actualOutputs,
        )
        expected.check()

    }

    @Test
    @DisplayName("4단계")
    fun t4(){
        testApp(listOf(
            "등록",
            "현재를 사랑하라.",
            "작자미상",
            "등록",
            "현재를 사랑하라.",
            "작자미상"
        ))
        val expectedOutputs = listOf(
            "== 명언 앱 ==",
            "명령) ",
            "명언 : ",
            "작가 : ",
            "1번 명언이 등록되었습니다.",
            "명령) ",
            "명언 : ",
            "작가 : ",
            "2번 명언이 등록되었습니다.",
            "명령) ",
        )
        val actualOutputs = writeText.wroteTexts
        val expected = Expected(
            expectedOutputs,
            actualOutputs,
        )
        expected.check()
    }

    @Test
    @DisplayName("5단계")
    fun t5(){
        testApp(listOf(
            "등록",
            "현재를 사랑하라.",
            "작자미상",
            "등록",
            "과거에 집착하지 마라.",
            "작자미상",
            "목록"
        ))
        val expectedOutputs = listOf(
            "1번 명언이 등록되었습니다.",
            "2번 명언이 등록되었습니다.",
            "번호 / 작가 / 명언",
            "----------------------",
            "2 / 작자미상 / 과거에 집착하지 마라.",
            "1 / 작자미상 / 현재를 사랑하라.",
        )
        val actualOutputs = writeText.wroteTexts
        val expected = Expected(
            expectedOutputs,
            actualOutputs,
        )
        expected.check()
    }

    @Test
    @DisplayName("6단계")
    fun t6(){
        testApp(listOf(
            "등록",
            "현재를 사랑하라.",
            "작자미상",
            "등록",
            "과거에 집착하지 마라.",
            "작자미상",
            "목록",
            "삭제?id=1",
        ))
        val expectedOutputs = listOf(
            "1번 명언이 등록되었습니다.",
            "2번 명언이 등록되었습니다.",
            "번호 / 작가 / 명언",
            "----------------------",
            "2 / 작자미상 / 과거에 집착하지 마라.",
            "1 / 작자미상 / 현재를 사랑하라.",
            "1번 명언이 삭제되었습니다.",
        )
        val actualOutputs = writeText.wroteTexts
        val expected = Expected(
            expectedOutputs,
            actualOutputs,
        )
        expected.check()
    }

    @Test
    @DisplayName("7단계")
    fun t7(){
        testApp(listOf(
            "등록",
            "현재를 사랑하라.",
            "작자미상",
            "등록",
            "과거에 집착하지 마라.",
            "작자미상",
            "목록",
            "삭제?id=1",
            "삭제?id=1",
        ))
        val expectedOutputs = listOf(
            "1번 명언이 등록되었습니다.",
            "2번 명언이 등록되었습니다.",
            "번호 / 작가 / 명언",
            "----------------------",
            "2 / 작자미상 / 과거에 집착하지 마라.",
            "1 / 작자미상 / 현재를 사랑하라.",
            "1번 명언이 삭제되었습니다.",
            "1번 명언은 존재하지 않습니다.",
        )
        val actualOutputs = writeText.wroteTexts
        val expected = Expected(
            expectedOutputs,
            actualOutputs,
        )
        expected.check()
    }

    @Test
    @DisplayName("8단계")
    fun t8(){
        testApp(listOf(
            "등록",
            "현재를 사랑하라.",
            "작자미상",
            "등록",
            "과거에 집착하지 마라.",
            "작자미상",
            "목록",
            "삭제?id=1",
            "삭제?id=1",
            "수정?id=3",
            "수정?id=2",
            "현재와 자신을 사랑하라.",
            "홍길동",
            "목록"
        ))
        val expectedOutputs = listOf(
            "1번 명언이 등록되었습니다.",
            "2번 명언이 등록되었습니다.",
            "번호 / 작가 / 명언",
            "----------------------",
            "2 / 작자미상 / 과거에 집착하지 마라.",
            "1 / 작자미상 / 현재를 사랑하라.",
            "1번 명언이 삭제되었습니다.",
            "1번 명언은 존재하지 않습니다.",
            "3번 명언은 존재하지 않습니다.",
            "명언(기존) : ",
            "명언 : ",
            "작가(기존) : ",
            "작가 : ",
            "2 / 홍길동 / 현재와 자신을 사랑하라."
        )
        val actualOutputs = writeText.wroteTexts
        val expected = Expected(
            expectedOutputs,
            actualOutputs,
        )
        expected.check()
    }

    @Test
    @DisplayName("9단계")
    fun t9(){
        testApp(listOf(
            "등록",
            "현재를 사랑하라.",
            "작자미상",
            "등록",
            "과거에 집착하지 마라.",
            "작자미상",
            "목록",
        ), reset= false)
        testApp(listOf(
            "목록"
        ))

        val expectedOutputs = listOf(
            "db/wiseSaying/1.json",
            "{",
            "\"id\": 1",
            "\"content\": \"현재를 사랑하라.\"",
            "\"author\": \"작자미상\"",
            "}",
            "1번 명언이 등록되었습니다.",
            "db/wiseSaying/2.json",
            "{",
            "\"id\": 2",
            "\"content\": \"과거에 집착하지 마라.\"",
            "\"author\": \"작자미상\"",
            "}",
            "2번 명언이 등록되었습니다.",
            "번호 / 작가 / 명언",
            "----------------------",
            "2 / 작자미상 / 과거에 집착하지 마라.",
            "1 / 작자미상 / 현재를 사랑하라.",
            "db/wiseSaying/lastId.txt",
            "2",
            "2 / 작자미상 / 과거에 집착하지 마라.",
            "1 / 작자미상 / 현재를 사랑하라.",

        )
        val actualOutputs = writeText.wroteTexts
        val expected = Expected(
            expectedOutputs,
            actualOutputs,
        )
        expected.check()

    }

    @Test
    @DisplayName("10단계")
    fun t10(){
        testApp(listOf(
            "등록",
            "현재를 사랑하라.",
            "작자미상",
            "등록",
            "과거에 집착하지 마라.",
            "작자미상",
            "목록",
//            "삭제?id=1",
//            "삭제?id=1",
            "수정?id=2",
            "현재와 자신을 사랑하라.",
            "홍길동",
            "목록",
            "빌드"
        ))
        val expectedOutputs = listOf(
            "1번 명언이 등록되었습니다.",
            "2번 명언이 등록되었습니다.",
            "번호 / 작가 / 명언",
            "----------------------",
            "2 / 작자미상 / 과거에 집착하지 마라.",
            "1 / 작자미상 / 현재를 사랑하라.",
            "2 / 홍길동 / 현재와 자신을 사랑하라.",
            "db/wiseSaying/data.json",
            "[",
            "{",
            "\"id\": 1",
            "\"content\": \"현재를 사랑하라.\"",
            "\"author\": \"작자미상\"",
            "}",
            "{",
            "\"id\": 2",
            "\"content\": \"현재와 자신을 사랑하라.\"",
            "\"author\": \"홍길동\"",
            "}",
            "]",
            "data.json 파일의 내용이 갱신되었습니다.",

        )
        val actualOutputs = writeText.wroteTexts
        val expected = Expected(
            expectedOutputs,
            actualOutputs,
        )
        expected.check()
    }

    @Test
    @DisplayName("13단계")
    fun t13(){
        testApp(listOf(
            "등록",
            "현재를 사랑하라.",
            "작자미상",
            "등록",
            "과거에 집착하지 마라.",
            "작자미상",
            "목록?keywordType=content&keyword=과거",
            "목록?keywordType=author&keyword=작자",
        ))
        val expectedOutputs = listOf(
            "1번 명언이 등록되었습니다.",
            "2번 명언이 등록되었습니다.",
            "----------------------",
            "검색타입 : content",
            "검색어 : 과거",
            "번호 / 작가 / 명언",
            "----------------------",
            "2 / 작자미상 / 과거에 집착하지 마라.",
            "----------------------",
            "검색타입 : author",
            "검색어 : 작자",
            "----------------------",
            "2 / 작자미상 / 과거에 집착하지 마라.",
            "1 / 작자미상 / 현재를 사랑하라."
        )
        val actualOutputs = writeText.wroteTexts
        val expected = Expected(
            expectedOutputs,
            actualOutputs,
        )
        expected.check()
    }

    @Test
    @DisplayName("14단계")
    fun t14(){
        val commands = mutableListOf<String>()
        for (i in 1..10) {
            commands.add("등록")
            commands.add("명언 $i")
            commands.add("작자미상 $i")
        }
        testApp(commands, reset=false)
        testApp(listOf(
            "목록",
            "목록?page=2",
        ))

        val expectedOutputs = listOf(
            "1번 명언이 등록되었습니다.",
            "2번 명언이 등록되었습니다.",
            "3번 명언이 등록되었습니다.",
            "4번 명언이 등록되었습니다.",
            "5번 명언이 등록되었습니다.",
            "6번 명언이 등록되었습니다.",
            "7번 명언이 등록되었습니다.",
            "8번 명언이 등록되었습니다.",
            "9번 명언이 등록되었습니다.",
            "10번 명언이 등록되었습니다.",
            "10 / 작자미상 10 / 명언 10",
            "9 / 작자미상 9 / 명언 9",
            "8 / 작자미상 8 / 명언 8",
            "7 / 작자미상 7 / 명언 7",
            "6 / 작자미상 6 / 명언 6",
            "페이지 : ",
            "[1]",
            "2",
            "5 / 작자미상 5 / 명언 5",
            "4 / 작자미상 4 / 명언 4",
            "3 / 작자미상 3 / 명언 3",
            "2 / 작자미상 2 / 명언 2",
            "1 / 작자미상 1 / 명언 1",
            "페이지 : ",
            "1",
            "[2]"


        )
        val actualOutputs = writeText.wroteTexts
        val expected = Expected(
            expectedOutputs,
            actualOutputs,
        )
        expected.check()
    }
}