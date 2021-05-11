import java.io.InputStreamReader

class MockResponseFileReader(path: String) {
    val content: String = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        .useLines { it.joinToString("") }

}
