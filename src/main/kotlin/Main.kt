import java.sql.SQLException
import java.util.concurrent.CountDownLatch


fun main() {
    threadSeries(2)
    threadSeries(4)
    threadSeries(8)
}

fun threadSeries(lvlOfIsolation: Int, repeats: Int = 1000) {
    DBUtils(lvlOfIsolation).execute(
        "DELETE FROM couriers WHERE address LIKE 'улица Пушкина, дом Колотушника'"
    )
    val latch = CountDownLatch(3)
    try {
        DBThread(Query.SELECT, DBUtils(lvlOfIsolation), latch, repeats).run()
        DBThread(Query.INSERT, DBUtils(lvlOfIsolation), latch, repeats).run()
        DBThread(Query.UPDATE, DBUtils(lvlOfIsolation), latch, repeats).run()
    } catch (e: SQLException) {
        throw e
    }
    latch.await()
}