# Example for a Spring Boot backend with MongoDB being used interactively.

Session:

```Groovy
$ gradle -q shell
This is a gradle Application Shell.
You can import your application classes and act on them.
Groovy Shell (2.3.1, JVM: 1.7.0_55)
Type ':help' or ':h' for help.
-----------------------------------------------
groovy:000> import com.example.config.*
===> com.example.config.*
groovy:000> ctx = Application.run()

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v1.0.2.RELEASE)

===> org.springframework.context.annotation.AnnotationConfigApplicationContext@4c898c2d: startup date [Fri May 23 22:32:11 CEST 2014]; root of context hierarchy
groovy:000> repo = ctx.getBean(PersonRepository)
===> org.springframework.data.mongodb.repository.support.SimpleMongoRepository@3957edeb
groovy:000> service = ctx.getBean(PersonService)
===> com.example.config.PersonService@68de760
groovy:000> repo.save(new Person('jane', 'dane'))
===> Person(id=537fb05744aea2eac0931e73, firstName=jane, lastName=dane)
groovy:000> repo.findAll()
===> [Person(id=537fb05744aea2eac0931e73, firstName=jane, lastName=dane)]
groovy:000> service.normalizeNames(repo.findByLastName('dane')[0].id)
===> null
groovy:000> repo.findAll()
===> [Person(id=537fb05744aea2eac0931e73, firstName=Jane, lastName=Dane)]
groovy:000> 
```
