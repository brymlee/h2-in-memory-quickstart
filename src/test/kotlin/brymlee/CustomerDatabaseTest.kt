package brymlee

import org.junit.Test
import org.junit.Assert.*
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.registerBean

class CustomerDatabaseTest{

    private val genericApplicationContext = GenericApplicationContext {
        registerBean{ Repository() }
        refresh()
    }

    private val repository:Repository = genericApplicationContext.getBean(Repository::class.java)

    @Test
    fun canRetrieveFirstRow(){
        repository.create()
        repository.insert(0, "Bob", "Barker")
        val person = repository.select()
        assertEquals("Bob", person.firstName)
        assertEquals("Barker", person.lastName)
    }
}
