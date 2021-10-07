import java.io.File
import java.util.concurrent.CountDownLatch

class DBThread(
        private val query: Query,
        private val dbTools: DBUtils,
        private val latch: CountDownLatch,
        private val repeats: Int
    ): Thread() {

    override fun run() {
        try {
            latch.countDown()
            val timeList = mutableListOf<String>()

            for (i in 0 until repeats) {
                val startTime = System.nanoTime()
                dbTools.execute(query.statement)
                timeList.add((System.nanoTime() - startTime).toString())
            }
            writeFile(timeList)
            println(query.name + "_" + dbTools.lvlOfIsolation + "_" + repeats + " thread is done")
        } catch (e: InterruptedException) {
            throw e
        } finally {
            dbTools.closeConnection()
        }
    }

    private fun writeFile(list: List<String>) {
        val file = File(
            "src/main/kotlin/results/" + query.name + "_" + dbTools.lvlOfIsolation + "_" + repeats
        )
        file.createNewFile()
        file.bufferedWriter().use { out ->
            list.forEach {
                out.write(it)
                out.newLine()
            }
        }
    }
}