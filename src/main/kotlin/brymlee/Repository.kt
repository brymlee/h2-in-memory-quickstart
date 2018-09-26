package brymlee

import java.sql.Connection
import java.sql.DriverManager
import kotlin.reflect.KClass

const val DB_DRIVER = "org.h2.Driver"
const val DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"
const val DB_USER = ""
const val DB_PASSWORD = ""
const val CREATE_QUERY = "CREATE TABLE CUSTOMER(id int primary key, firstName varchar(255), lastName varchar(255))"
const val INSERT_QUERY = "INSERT INTO CUSTOMER" + "(id, firstName, lastName) values" + "(?,?,?)"
const val SELECT_QUERY = "select * from CUSTOMER"

class Repository {

    fun getDBConnection() : Connection {
        val dbConnection:Connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD)
        try {
            Class.forName(DB_DRIVER);
        } catch (classNotFoundException:ClassNotFoundException) {
            throw RuntimeException(classNotFoundException)
        }
        return dbConnection
    }

    fun select() : Person{
        val dbConnection = getDBConnection()
        val selectPreparedStatement = dbConnection.prepareStatement(SELECT_QUERY)
        val resultSet = selectPreparedStatement.executeQuery()
        resultSet.next()
        val person = Person(resultSet.getInt("id"), resultSet.getString("firstName"), resultSet.getString("lastName"))
        selectPreparedStatement.close()
        return person
    }

    fun insert(id:Int, firstName:String, lastName:String){
        val dbConnection:Connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD)
        try{
            var insertPreparedStatement = dbConnection.prepareStatement(INSERT_QUERY);
            insertPreparedStatement.setInt(1, id)
            insertPreparedStatement.setString(2, firstName)
            insertPreparedStatement.setString(3, lastName)
            insertPreparedStatement.executeUpdate()
            insertPreparedStatement.close()
            dbConnection.commit()
        }catch(exception:Exception){
            throw RuntimeException(exception)
        }finally{
            dbConnection.close()
        }
    }

    fun create(){
        val dbConnection = getDBConnection()
        try{
            dbConnection.setAutoCommit(false)
            var createPreparedStatement = dbConnection.prepareStatement(CREATE_QUERY)
            createPreparedStatement.executeUpdate()
            createPreparedStatement.close()
            System.out.println("H2 In-Memory Database inserted through PreparedStatement")
            dbConnection.commit();
        } catch (exception:Exception) {
            throw RuntimeException(exception)
        } finally {
            dbConnection.close()
        }
    }
}
