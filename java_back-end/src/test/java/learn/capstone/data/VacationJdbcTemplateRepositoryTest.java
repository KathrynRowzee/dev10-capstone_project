package learn.capstone.data;

import learn.capstone.models.Vacation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class VacationJdbcTemplateRepositoryTest {

    final static int NEXT_ID = 7;

    @Autowired
    VacationJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup(){knownGoodState.set();}

    @Test
    void shouldFindAll(){
        List<Vacation> vacations = repository.findAll();
        assertNotNull(vacations);

        assertTrue(vacations.size() >= 5 && vacations.size() <= 7);
    }

    @Test
    void shouldFindByUserId(){
        List<Vacation> vacations = repository.findByUserId(2);
        assertNotNull(vacations);

        assertTrue(vacations.size() >= 1 && vacations.size() <= 3);
    }

    @Test
    void shouldFindMNTrip(){
        Vacation mn = repository.findById(1);
        assertEquals(1, mn.getVacationId());
        assertEquals("A trip to the capital of MN", mn.getDescription() );
        assertEquals(1, mn.getLocations().size());
    }

    @Test
    void shouldAdd(){
        Vacation vacation = makeVacation();
        Vacation actual = repository.add(vacation);
        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getVacationId());

        vacation = makeVacation();
        actual = repository.add(vacation);
        assertNotNull(actual);
        assertEquals(8, actual.getVacationId());
    }

    @Test
    void shouldUpdate(){
        Vacation vacation = makeVacation();
        vacation.setVacationId(3);
        vacation.setDescription("test2");
        assertTrue(repository.update(vacation));
        vacation.setVacationId(20);
        assertFalse(repository.update(vacation));
    }

    @Test
    void shouldDelete(){
        assertTrue(repository.deleteById(4));
        assertFalse(repository.deleteById(4));
    }



    private Vacation makeVacation(){
        Vacation vacation = new Vacation();
        vacation.setDescription("test");
        vacation.setLeisureLevel(2);

        return vacation;
    }



}