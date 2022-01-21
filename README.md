# Privacy Aware Programming 

## Design Goals 

1. Data should remain wrapped as long as possible. Preferably declassified only at the application boundaries. 

2. Wrappers should remain oblivious to the users. The methods of the wrapped type should be use-able from the wrapper.


## Typelevel Implications of Goals #2  

The obliviousness goal results in 3 cases that needed to be supported in a static type system 

1.  A policy type needs to be used interchangably with the type that it is wrapping

```scala 
  val x : Int = new Policy[Int](5)
```

2. The methods on the wrapped type should be directly callable on the wrapper and delegated iff the policy check passes 

```scala 
   new Policy[User](User("userA")).username
   new Policy[Int](5) + 5 
```

3. As it is not possible for all the dependencies of a project to be policy-aware, wrapers needs to be able to be passed into functions that expect the wrapped type as an argument

```scala
    5 + Policy[Int])(5)
```

