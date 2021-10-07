enum class Query(val statement: String) {
    SELECT("SELECT * FROM couriers WHERE address LIKE 'улица Пушкина, дом Колотушника'"),
    INSERT("INSERT INTO couriers(address, salary) VALUES ('улица Пушкина, дом Колотушника', 1)"),
    UPDATE("UPDATE couriers SET salary = 2 WHERE address LIKE 'улица Пушкина, дом Колотушника'")
}