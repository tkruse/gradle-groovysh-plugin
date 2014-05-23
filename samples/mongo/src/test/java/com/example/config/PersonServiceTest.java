package com.example.config;

import junit.framework.TestCase;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;

public class PersonServiceTest extends TestCase {

    public void testNormalizeNames() throws Exception {
        PersonRepository mockRepo = mock(PersonRepository.class);
        Person person = spy(new Person("testfn", "testln"));
        when(person.getId()).thenReturn("1234");
        when(mockRepo.findById("1234")).thenReturn(person);
        PersonService personService = new PersonService(mockRepo);

        personService.normalizeNames("1234");

        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
        verify(mockRepo, times(1)).save(argument.capture());
        assertEquals("Testfn", argument.getValue().getFirstName());
        assertEquals("Testln", argument.getValue().getLastName());
    }
}
