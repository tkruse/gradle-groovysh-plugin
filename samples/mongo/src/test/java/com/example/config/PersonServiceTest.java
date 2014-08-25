package com.example.config;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * example test
 */
public class PersonServiceTest {

    @Test
    public void testNormalizeNames() {
        final PersonRepository mockRepo = mock(PersonRepository.class);
        final Person person = spy(new Person("testfn", "testln"));
        when(person.getPId()).thenReturn("1234");
        when(mockRepo.findByPId("1234")).thenReturn(person);
        final PersonService personService = new PersonService(mockRepo);

        personService.normalizeNames("1234");

        final ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
        verify(mockRepo, times(1)).save(argument.capture());
        assertEquals("Testfn", argument.getValue().getFirstName());
        assertEquals("Testln", argument.getValue().getLastName());
    }
}
