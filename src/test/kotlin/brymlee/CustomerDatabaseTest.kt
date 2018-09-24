package brymlee

import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import java.lang.RuntimeException

@ContextConfiguration(classes = arrayOf(CustomerDatabaseTestContext::class))
@RunWith(SpringJUnit4ClassRunner::class)
class CustomerDatabaseTest{

    @Autowired
    val repository:Repository? = null

    @Test
    fun makeSureRepositoryIsNotNull(){
        if(repository == null){
            throw RuntimeException("repository is null in makeSureRepositoryIsNotNull")
        }
    }

    @Test
    fun canRetrieveFirstRow(){
        if(repository != null){
            repository.create()
            repository.insert(0, "Bob", "Barker")
            val person = repository.select()
            assertEquals("Bob", person.firstName)
            assertEquals("Barker", person.lastName)
        }else{
            throw RuntimeException("person is null in canRetrieveFirstRow")
        }
    }
}
