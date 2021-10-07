import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException


private object DBCredentials {
    const val USER = "postgres"
    const val PASSWORD = "wa31sd12"
    const val URL = "jdbc:postgresql://localhost:5432/fooddelivery"
}

class DBUtils(lvlOfIsolation: Int) {
    private val connection: Connection
    val lvlOfIsolation: Int

    init {
        this.lvlOfIsolation = lvlOfIsolation
        try {
            connection = DriverManager.getConnection(
                DBCredentials.URL,
                DBCredentials.USER,
                DBCredentials.PASSWORD)
            connection.transactionIsolation = lvlOfIsolation;
        } catch (e: SQLException) {
            throw e
        }
    }

    fun execute(str: String) {
        try {
            val statement = connection.createStatement()
            statement?.execute(str)
        } catch (e: SQLException) {
            throw e
        }
    }

    fun closeConnection() {
        try {
            connection.close()
        } catch (e: SQLException) {
            throw e
        }
    }
}